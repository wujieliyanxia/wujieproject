package util;

import com.yinhai.bcpcs.auth.RSAUtils;
import com.yinhai.common.webservice.security.AddSoapHeader;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class WebServiceHttps {
    static {
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

            public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
                return true;
            }
        });
        System.setProperty("javax.net.ssl.trustStore", "D:\\站点证书\\172.27.136.10-test.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "11111111");
    }

    public static Object invoke(String wsdlUrl, String systemKey, String serviceKey, String xmlInput) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        // 创建client， wsdlUrl地址格式： 业务协同管理平台访问地址 +/services/yinHaiBusiness?wsdl
        Client client = dcf.createClient(wsdlUrl);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        TLSClientParameters tlsClientParameters = new TLSClientParameters();
        tlsClientParameters.setSecureSocketProtocol("SSL");
        tlsClientParameters.setDisableCNCheck(true);
        tlsClientParameters.setUseHttpsURLConnectionDefaultHostnameVerifier(true);
        http.setTlsClientParameters(tlsClientParameters);
        tlsClientParameters.setUseHttpsURLConnectionDefaultSslSocketFactory(true);
        // 设置连接超时参数
        HTTPClientPolicy hcp = new HTTPClientPolicy();
        hcp.setReceiveTimeout(200000);
        http.setClient(hcp);

        client.getOutInterceptors().add(new AddSoapHeader("http://yinhai.com", systemKey, xmlInput, RSAUtils.sign(xmlInput), serviceKey));
        // 发起调用
        Object[] obj = client.invoke("callBusiness", xmlInput);
        // 获取返回结果(XML格式)
        Object result = obj[0];
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
//        List aaList = new ArrayList();
//        Map aMap = new HashMap();
//        aMap.put("1", "1");
//        aaList.add(aMap);
//        JSONArray array = JSONArray.fromObject(aaList);
//        System.out.println(array);
//        System.out.println(JSONObject.toJSONString(aaList));
    }
}
