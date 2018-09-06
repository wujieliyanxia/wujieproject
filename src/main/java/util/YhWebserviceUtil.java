package util;

import com.alibaba.fastjson.JSONObject;
import com.yinhai.common.webservice.security.AddSoapHeader;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;


/**
 * @author JIE WU
 * @create 2018-04-11
 * @desc 银海调用协同webservice
 **/
public class YhWebserviceUtil {

    //https 调用方式
    static {
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {
            public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
                return true;
            }
        });
        String basePath = YhWebserviceUtil.class.getClassLoader().getResource("").getPath();
        System.setProperty("javax.net.ssl.trustStore", basePath + "trustStore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "11111111");
    }


    /**
     * 调用此方法需要将协同上系统对应的密钥文件放在根目录下
     *
     * @param wsdlUrl    协同wsdlUrl地址
     * @param systemKey  协同上注册的systemKey
     * @param serviceKey 协同上注册的服务名
     * @param xmlInput   参数
     * @return
     * @throws Exception
     */
    public static Object invoke(String wsdlUrl, String systemKey, String serviceKey, String xmlInput) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        // 创建client， wsdlUrl地址格式： 业务协同管理平台访问地址 +/services/yinHaiBusiness?wsdl
        Client client = dcf.createClient(wsdlUrl);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        if (wsdlUrl.indexOf("https://") != -1) {
            TLSClientParameters tlsClientParameters = new TLSClientParameters();
            tlsClientParameters.setSecureSocketProtocol("SSL");
            tlsClientParameters.setDisableCNCheck(true);
            tlsClientParameters.setUseHttpsURLConnectionDefaultHostnameVerifier(true);
            tlsClientParameters.setUseHttpsURLConnectionDefaultSslSocketFactory(true);
            http.setTlsClientParameters(tlsClientParameters);
        }
        // 设置连接超时参数
        HTTPClientPolicy hcp = new HTTPClientPolicy();
        hcp.setConnectionTimeout(20000);
        hcp.setReceiveTimeout(200000);
        http.setClient(hcp);
        // 参数xml， 最外层必须是input
        // String xmlInput="<input>test</input>";
        // 设置SOAP头信息
        /**
         * AddSoapHeader 构造函数说明：
         * 1、 协同平台webservice服务命名空间:http://yinhai.com
         * 2、接入系统标识
         * 3、 参数
         * 4、 参数签名
         * 5、 服务标识
         **/
        client.getOutInterceptors().add(new AddSoapHeader("http://yinhai.com", systemKey, xmlInput, RSAUtils.sign(xmlInput), serviceKey));
        // 发起调用
        Object[] obj = client.invoke("callBusiness", xmlInput);
        // 获取返回结果(XML格式)
        Object result = obj[0];
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) throws Exception {
//        String xmlInput = "<input><control>v1.0|22000001480777615|220000014|_67hwrxpd4|203|2018-06-21 10:18:47|1233|0|</control><keyword>220723198508151011|李永鹏|D3657035X</keyword></input>";
        String xmlInput = "<input><task_code>ms00001915531105</task_code></input>";
        invoke("http://10.16.36.163:9003/yhbus/services/yinHaiBusiness?wsdl", "dsfcs", "bp_0001", xmlInput);
    }
}


