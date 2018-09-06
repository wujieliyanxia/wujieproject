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
 * @desc 社会保障卡调用demo
 **/
public class BzkInterfaceDemo {
    // 姓名
    private final static String AAC003 = "邬洁";
    // 身份证号码
    private final static String AAC002 = "362329199412164516";
    // 业务等级
    private final static String serviceLevel = "1";
    // 用户名
    private final static String user = "1563";
    // 密码
    private final static String password = "123";
    // 协同地址
//    final static String wsdlUrl = "http://172.27.109.200/yhbus/services/yinHaiBusiness?wsdl";
//    final static String wsdlUrl = "http://10.16.28.2:7001/jlCardService/services/CardService?wsdl";
//    final static String wsdlUrl = "http://10.16.36.163:9003/yhbus/services/yinHaiBusiness?wsdl";
    final static String wsdlUrl = "http://192.168.122.179:7005/jlbus/services/yinHaiBusiness?wsdl";
//    final static String wsdlUrl = "http://192.168.50.66:8080/yhbus/services/yinHaiBusiness?wsdl";


    /**
     * 挂失、解挂
     *
     * @return object
     */
    public Object reportLoss() {
        Object returnStr = null;
        StringBuffer inputXml = new StringBuffer();
        appendXml(inputXml);
        inputXml.append("<操作*>一张网临时挂失</操作*>");
        //inputXml.append("<操作*>一张网解挂</操作*>");
        inputXml.append("<社会保障号码*>220105198805160613</社会保障号码*>")
                .append("<Aab301>").append("220100").append("</Aab301>")
                .append("<社会卡号*>").append("220100").append("</社会卡号*>");
        try {
            returnStr = YhWebserviceUtil.invoke(wsdlUrl, "js_yhxt", "SBKGSJG", inputXml.toString());
            System.out.println(returnStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnStr;
    }

    /**
     * 基本信息与卡状态查询 SBKJBXX
     * 制卡进度查询 SBKZKJD
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
     * 测试公共服务开放平台
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
     * 添加公共参数
     *
     * @param paramMap 参数
     */
    public void putParam(Map<String, String> paramMap) {
        paramMap.put("AAC003", AAC003);
        paramMap.put("AAC002", AAC002);
        paramMap.put("serviceLevel", serviceLevel);
        paramMap.put("用户名*", user);
        paramMap.put("密码*", password);
    }

    /**
     * 拼接公共参数
     *
     * @param inputXml
     */
    public void appendXml(StringBuffer inputXml) {
        inputXml.append("<AAC003>").append(AAC003).append("</AAC003>")
                .append("<AAC002>").append(AAC002).append("</AAC002>")
                .append("<serviceLevel>").append(serviceLevel).append("</serviceLevel>")
                .append("<用户名*>").append(user).append("</用户名*>")
                .append("<密码*>").append(password).append("</密码*>");
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
            // 超时时间30秒
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

    //把字符串转换成Unicode字符串，输入的字符串中只能是0-9数字或者是A--F字母，不能有任何其他字符
    //输入字符串要是偶数个字符。
    public static String stringToUnicode(String str) throws Exception {
        byte[] bytes = new byte[str.length() / 2 + 2];    //定义字节数组，长度为字符串的一半。加 2 是存放unicode 编码头(ff,fe)
        bytes[0] = -2;                    //-2  对应fe,-1对应ff. 后面要交换，所以保存 fe,ff.
        bytes[1] = -1;
        byte tempByte = 0;                //临时变量。
        byte tempHigh = 0;
        byte tempLow = 0;
        for (int i = 0, j = 2; i < str.length(); i += 2, j++)        //每循环处理2个字符，最后形成一个字节。
        {
            tempByte = (byte) (((int) str.charAt(i)) & 0xff);    //处理高位。
            if (tempByte >= 48 && tempByte <= 57) {
                tempHigh = (byte) ((tempByte - 48) << 4);    //'0'对应48。
            } else if (tempByte >= 65 && tempByte <= 70)        //'A'--'F'
            {
                tempHigh = (byte) ((tempByte - 65 + 10) << 4);
            }

            tempByte = (byte) (((int) str.charAt(i + 1)) & 0xff);    //处理低位。
            if (tempByte >= 48 && tempByte <= 57) {
                tempLow = (byte) (tempByte - 48);
            } else if (tempByte >= 65 && tempByte <= 70)        //'A'--'F'
            {
                tempLow = (byte) (tempByte - 65 + 10);        //'A'对应10.（或0xa.）
            }
            bytes[j] = (byte) (tempHigh | tempLow);        //通过‘或’加在一起。
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
