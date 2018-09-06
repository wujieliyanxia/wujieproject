package https;

import com.alibaba.fastjson.JSONObject;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.URLConnectionHTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.http.conn.ssl.NoopHostnameVerifier;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author JIE WU
 * @create 2018-05-07
 * @desc 调用https的util
 **/
public class httpsWebserviceUtil {
    static String URL = "https://139.215.206.92:9222/services/ESBDockGovHallServer?wsdl";
    static String SERVICE_KEY = "serverService";

    static {
        ByteBuddyAgent.install();
        new ByteBuddy()
                .redefine(URLConnectionHTTPConduit.class)
                .visit(Advice.to(URLConnectionHTTPConduitAdviceInterceptor.class).on(ElementMatchers.named("setupConnection")))
                .make()
                .load(httpsWebserviceUtil.class.getClassLoader(),
                        ClassReloadingStrategy.fromInstalledAgent());
    }

    public static Client create(String url) throws Exception {
        Bus bus = BusFactory.getThreadDefaultBus();
        TLSClientParameters tlsClientParameters = null;
        if (url.startsWith("https")) {
            String certPath = HttpsCertGenetator.generateCert(url);
            char[] certPass = ("changeit").toCharArray();
            FileInputStream is = new FileInputStream(certPath);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(is, certPass);
            is.close();
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, certPass);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            tlsClientParameters = new TLSClientParameters();
            tlsClientParameters.setSSLSocketFactory(sslSocketFactory);
            tlsClientParameters.setHostnameVerifier(NoopHostnameVerifier.INSTANCE);
            tlsClientParameters.setDisableCNCheck(true);
            tlsClientParameters.setUseHttpsURLConnectionDefaultHostnameVerifier(false);
            tlsClientParameters.setUseHttpsURLConnectionDefaultSslSocketFactory(false);
            bus.setProperty("clientParameters", tlsClientParameters);
        }
        JaxWsDynamicClientFactory dcf = new JaxWsDynamicClientFactory(bus) {
            @Override
            protected boolean compileJavaSrc(String classPath, List<File> srcList, String dest) {
                org.apache.cxf.common.util.Compiler javaCompiler = new org.apache.cxf.common.util.Compiler();
                javaCompiler.setClassPath(classPath);
                javaCompiler.setOutputDir(dest);
                javaCompiler.setEncoding("UTF-8");
                javaCompiler.setTarget("1.6");
                return javaCompiler.compileFiles(srcList);
            }
        };

        Client client = null;
        client = dcf.createClient(url);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        policy.setConnectionTimeout(6000000);// 连接超时(毫秒)
        policy.setAllowChunking(false);// 取消块编码
        policy.setReceiveTimeout(6000000);// 响应超时(毫秒)
        conduit.setClient(policy);
        http.setClient(policy);
        if (url.startsWith("https") && tlsClientParameters != null) {
            http.setTlsClientParameters(tlsClientParameters);
        }
        return client;
    }

    public static Object invoke(String serviceKey, String url, String... param) throws Exception {
        Client client = create(url);
        Object[] objects = client.invoke(serviceKey, param);
        return objects[0];
    }

    public static void main(String[] args) {
        String xtid = "2c9080954db31c31014dd76f18040002";//系统ID
        String fwid = "8a81a7dc60e845fc01628f84d31e0005";//服务ID
        String regionCode = "2200001";//这个写死就可以
        String userId = "rst_01";//这个写死就可以
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -100);//+1今天的时间加一天
        String startdate = dateFormat.format(calendar.getTime());
        String enddate = dateFormat.format(date);
        JSONObject obj = new JSONObject();//具体参数请参照接口文档
        obj.put("startdate", startdate);
        obj.put("enddate", enddate);
        obj.put("pageNo", 1);
        obj.put("rows", 50);
        Object returnObject = null;
        try {
            returnObject = invoke(SERVICE_KEY, URL, xtid, fwid, regionCode, userId, obj.toString());
            System.out.println(returnObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
