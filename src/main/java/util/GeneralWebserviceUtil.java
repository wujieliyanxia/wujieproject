package util;

import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.print.DocFlavor;
import javax.xml.namespace.QName;
import java.io.File;
import java.util.*;

/**
 * @author JIE WU
 * @create 2018-04-11
 * @desc 普通的webservice调用
 **/
public class GeneralWebserviceUtil {
    /**
     * @param wsdlUrl    接口发布的wsdl地址
     * @param serviceKey 调用的方法名
     * @param xmlInput   参数
     * @return
     * @throws Exception
     */
    public static Object invoke(String wsdlUrl, String serviceKey, String... xmlInput) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        // 创建client
        Client client = dcf.createClient(wsdlUrl);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        // 设置连接超时参数
        HTTPClientPolicy hcp = new HTTPClientPolicy();
        hcp.setConnectionTimeout(2000);
        hcp.setReceiveTimeout(20000);
        http.setClient(hcp);
        // 发起调用
        Object[] obj = client.invoke(serviceKey, xmlInput);
        // 获取返回结果(XML格式)
        Object result = obj[0];
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) throws Exception {
        String wsdlUrl = "http://10.16.19.60:8001/services/get_dataSoap?wsdl";
        invoke(wsdlUrl,"Grjbxxcx","220721194210133012","沈荣林","szf","");
    }

    public int reverse1(int x) {
        if (x < 10 && x > -10) return x;
        String var1 = String.valueOf(x);
        String var2;
        int var4;
        if (x < 0) {
            var1 = var1.substring(1, var1.length());
        }
        char[] var3 = new char[var1.length()];
        for (int i = var1.length() - 1; i >= 0; i--) {
            var3[var1.length() - 1 - i] = (var1.charAt(i));
        }
        var2 = new String(var3);
        if (x < 0) {
            var2 = "-" + var2;
        }
        try {
            return var4 = Integer.parseInt(var2);
        } catch (NumberFormatException e) {
            return 0;
        }

    }


    public int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }


    public static int romanToInt(String s) {
        Map<Character, Integer> var0 = new HashMap<>();
        var0.put('I', 1);
        var0.put('V', 5);
        var0.put('X', 10);
        var0.put('L', 50);
        var0.put('C', 100);
        var0.put('D', 500);
        var0.put('M', 1000);
        Set<String> var3 = new HashSet<>();
        var3.add("IV");
        var3.add("IX");
        var3.add("XL");
        var3.add("XC");
        var3.add("CD");
        var3.add("CM");
        int var1 = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            Integer var2 = var0.get(s.charAt(i));
            if (i < s.length() - 1 && var3.contains(s.substring(i, i + 2))) {
                var2 = -var2;
            }
            var1 += var2;
        }
        return var1;
    }
}
