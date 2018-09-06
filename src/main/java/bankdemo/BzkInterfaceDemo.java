package bankdemo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import util.GeneralWebserviceUtil;
import util.YhWebserviceUtil;

import javax.xml.namespace.QName;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JIE WU
 * @create 2018-04-13
 * @desc ��ᱣ�Ͽ�����demo
 **/
public class BzkInterfaceDemo {
    // ����
    private final static String AAC003 = "����";
    // ���֤����
    private final static String AAC002 = "362329199412164516";
    // ҵ��ȼ�
    private final static String serviceLevel = "1";
    // �û���
    private final static String user = "1563";
    // ����
    private final static String password = "123";
    // Эͬ��ַ
//    final static String wsdlUrl = "http://172.27.109.200/yhbus/services/yinHaiBusiness?wsdl";
//    final static String wsdlUrl = "http://10.16.28.2:7001/jlCardService/services/CardService?wsdl";
//    final static String wsdlUrl = "http://10.16.36.163:9003/yhbus/services/yinHaiBusiness?wsdl";
    final static String wsdlUrl = "http://192.168.122.179:7005/jlbus/services/yinHaiBusiness?wsdl";
//    final static String wsdlUrl = "http://192.168.50.66:8080/yhbus/services/yinHaiBusiness?wsdl";


    /**
     * ��ʧ�����
     *
     * @return object
     */
    public Object reportLoss() {
        Object returnStr = null;
        StringBuffer inputXml = new StringBuffer();
        appendXml(inputXml);
        inputXml.append("<����*>һ������ʱ��ʧ</����*>");
        //inputXml.append("<����*>һ�������</����*>");
        inputXml.append("<��ᱣ�Ϻ���*>220105198805160613</��ᱣ�Ϻ���*>")
                .append("<Aab301>").append("220100").append("</Aab301>")
                .append("<��Ῠ��*>").append("220100").append("</��Ῠ��*>");
        try {
            returnStr = YhWebserviceUtil.invoke(wsdlUrl, "js_yhxt", "SBKGSJG", inputXml.toString());
            System.out.println(returnStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnStr;
    }

    /**
     * ������Ϣ�뿨״̬��ѯ SBKJBXX
     * �ƿ����Ȳ�ѯ SBKZKJD
     */
    public void getBaseInfo(String serviceKey) {
        try {
            Object returnStr = YhWebserviceUtil.invoke(wsdlUrl, "js_yhxt", serviceKey, "[\"1563\",\"123\",\"362329199412164516\"]");
//            Object returnStr = GeneralWebserviceUtil.invoke(wsdlUrl, serviceKey, "1563","123","362329199412164516");
            System.out.println(returnStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ���Թ������񿪷�ƽ̨
     * @param serviceKey
     */
    public void testGGFW(String serviceKey){
//        QName qName = new QName("http://internal.cfx.web.neusoft.com/", serviceKey);
        String xmlInput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><queryJcBf01><rows><aab301>220201000000</aab301></rows></queryJcBf01>";
        try {
            Object returnStr = YhWebserviceUtil.invoke(wsdlUrl, "ylz_ggfw", serviceKey, xmlInput);
//            Object returnStr = GeneralWebserviceUtil.invoke(wsdlUrl, serviceKey, "1563","123","362329199412164516");
            System.out.println(returnStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * ��ӹ�������
     *
     * @param paramMap ����
     */
    public void putParam(Map<String, String> paramMap) {
        paramMap.put("AAC003", AAC003);
        paramMap.put("AAC002", AAC002);
        paramMap.put("serviceLevel", serviceLevel);
        paramMap.put("�û���*", user);
        paramMap.put("����*", password);
    }

    /**
     * ƴ�ӹ�������
     *
     * @param inputXml
     */
    public void appendXml(StringBuffer inputXml) {
        inputXml.append("<AAC003>").append(AAC003).append("</AAC003>")
                .append("<AAC002>").append(AAC002).append("</AAC002>")
                .append("<serviceLevel>").append(serviceLevel).append("</serviceLevel>")
                .append("<�û���*>").append(user).append("</�û���*>")
                .append("<����*>").append(password).append("</����*>");
    }

    public static void main(String[] args) {
        BzkInterfaceDemo bzkInterfaceDemo = new BzkInterfaceDemo();
//        bzkInterfaceDemo.getBaseInfo("getAC01YZW");
//        bzkInterfaceDemo.getBaseInfo("SBKZKJD");
//        bzkInterfaceDemo.getBaseInfo("SBKJBXX");
//        bzkInterfaceDemo.reportLoss();
        bzkInterfaceDemo.testGGFW("queryJcBf01");
//        InputStream resourceAsStream = BzkInterfaceDemo.class.getResourceAsStream("zkjd.xml");
/*        try {
            FileInputStream inputStream = new FileInputStream(new File("D:\\ideaWorkspace\\wujieproject\\src\\main\\java\\bankdemo\\test.xml"));
            String returnStr = BzkInterfaceDemo.doPost(wsdlUrl, inputStream);
            System.out.println(returnStr);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /*JSONArray arr = JSONObject.parseArray("[\"1\",\"1\",\"1\"]");
        String testStr = "&#x4e2d;&#x56fd;&#x5efa;&#x8bbe;&#x94f6;&#x884c;&#x8d1f;&#x8d23;&#x8054;&#x7cfb;&#x76f8;&#x5173;&#x4f01;&#x4e1a;&#x8d1f;&#x8d23;&#x4eba;&#x7edf;&#x4e00;&#x529e;&#x7406;&#x9886;&#x53d6;&#x3002;&#x5b58;&#x653e;&#x5730;&#x5740;&#xff1a;&#x957f;&#x6625;&#x9ad8;&#x65b0;&#x652f;&#x884c;&#x5357;&#x5173;&#x533a;&#x76db;&#x4e16;&#x5927;&#x8def;2871&#x53f7";
        try {
            String s = BzkInterfaceDemo.stringToUnicode(testStr);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static String doPost(String url, InputStream inputStream) throws Exception {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ʱʱ��30��
            int timeout = 30 * 1000;
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.connect();
            if (inputStream != null) {
                OutputStream outputStream = conn.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                byte[] buf = new byte[10240];
                int len = bis.read(buf, 0, buf.length);
                while (len != -1) {
                    bos.write(buf, 0, len);
                    len = bis.read(buf, 0, buf.length);
                }
                bos.flush();
                bos.close();
            }
            InputStreamReader r = new InputStreamReader(conn.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(r);
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }

    //���ַ���ת����Unicode�ַ�����������ַ�����ֻ����0-9���ֻ�����A--F��ĸ���������κ������ַ�
    //�����ַ���Ҫ��ż�����ַ���
    public static String stringToUnicode(String str) throws Exception {
        byte[] bytes = new byte[str.length() / 2 + 2];    //�����ֽ����飬����Ϊ�ַ�����һ�롣�� 2 �Ǵ��unicode ����ͷ(ff,fe)
        bytes[0] = -2;                    //-2  ��Ӧfe,-1��Ӧff. ����Ҫ���������Ա��� fe,ff.
        bytes[1] = -1;
        byte tempByte = 0;                //��ʱ������
        byte tempHigh = 0;
        byte tempLow = 0;
        for (int i = 0, j = 2; i < str.length(); i += 2, j++)        //ÿѭ������2���ַ�������γ�һ���ֽڡ�
        {
            tempByte = (byte) (((int) str.charAt(i)) & 0xff);    //�����λ��
            if (tempByte >= 48 && tempByte <= 57) {
                tempHigh = (byte) ((tempByte - 48) << 4);    //'0'��Ӧ48��
            } else if (tempByte >= 65 && tempByte <= 70)        //'A'--'F'
            {
                tempHigh = (byte) ((tempByte - 65 + 10) << 4);
            }

            tempByte = (byte) (((int) str.charAt(i + 1)) & 0xff);    //�����λ��
            if (tempByte >= 48 && tempByte <= 57) {
                tempLow = (byte) (tempByte - 48);
            } else if (tempByte >= 65 && tempByte <= 70)        //'A'--'F'
            {
                tempLow = (byte) (tempByte - 65 + 10);        //'A'��Ӧ10.����0xa.��
            }
            bytes[j] = (byte) (tempHigh | tempLow);        //ͨ�����򡯼���һ��
        }

        for (int i = 0; i < bytes.length; i += 2) {
            byte b1 = bytes[i];
            bytes[i] = bytes[i + 1];
            bytes[i + 1] = b1;
        }
        String result = new String(bytes, "Unicode");
        return result;
    }
}
