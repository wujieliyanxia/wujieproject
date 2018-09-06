package socialsecurity;

import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Encoder;
import util.YhWebserviceUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author JIE WU
 * @create 2018-05-10
 * @desc 社保接口demo
 **/
public class SocialSecurityDemo {
    // 正式协同地址
    static String WSDL_URL = "http://172.27.109.200/yhbus/services/yinHaiBusiness?wsdl";
    // 测试协同地址
    // static String WSDL_URL = "http://10.16.36.163:9003/yhbus/services/yinHaiBusiness?wsdl";
    // 协同上配置的系统id
    static String SYSTEM_KEY = "yzw";

    // 个人基本信息查询（grjbxxcx）4个参数
    static String PERSONAL_INFORMATION_KEY = "grjbxxcx";
    // 个人缴费基数查询接口（grjfjscx)5个参数
    static String PERSONAL_PAYMENT_BASE_KEY = "grjfjscx";
    // 个人缴费明细查询接口（grjfmxcx）5个参数
    static String PERSONAL_PAYMENT_DETAILS_KEY = "grjfmxcx";
    // 个人养老待遇查询接口（gryldycx）4个参数
    static String PERSONAL_PENSION_TREATMENT_KEY = "gryldycx";
    // 待遇发放查询（dyzfjlxxcx）5个参数
    static String TREATMENT_GRANT_KEY = "dyzfjlxxcx";
    // 个人参保证明（grcbzm）4个参数
    static String PERSONAL_GUARANTEE_KEY = "grcbzm";


    /**
     * 4个参数信息查询
     *
     * @param sfzhm      身份证号码
     * @param xm         姓名
     * @param qdm        渠道
     * @param pwd        验证码
     * @param serviceKey 协同上注册的serviceKey
     * @return String
     * @throws Exception 调用接口异常
     */
    public String getInfoByFour(String sfzhm, String xm, String qdm, String pwd, String serviceKey) throws Exception {
        Object[] paramObjs = new Object[]{sfzhm, xm, qdm, pwd};
        System.out.println(JSONObject.toJSONString(paramObjs));
        Object returnObj = YhWebserviceUtil.invoke(WSDL_URL, SYSTEM_KEY, serviceKey, JSONObject.toJSONString(paramObjs));
        return returnObj.toString();

    }

    /**
     * 5个参数查询接口
     *
     * @param sfzhm      身份证号码
     * @param xm         姓名
     * @param xz         险种 养老110失业210
     * @param qdm        渠道
     * @param pwd        验证码
     * @param serviceKey 协同上注册的serviceKey
     * @return String
     * @throws Exception 调用接口异常
     */
    public String getInfoByFive(String sfzhm, String xm, String xz, String qdm, String pwd, String serviceKey) throws Exception {
        Object[] paramObjs = new Object[]{sfzhm, xm, xz, qdm, pwd};
        System.out.println(JSONObject.toJSONString(paramObjs));
        Object returnObj = YhWebserviceUtil.invoke(WSDL_URL, SYSTEM_KEY, serviceKey, JSONObject.toJSONString(paramObjs));
        return returnObj.toString();
    }


    /**
     * 获取验证码
     *
     * @param qdm 渠道
     * @return 验证码
     */
    public String getPwd(String qdm) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64 = new BASE64Encoder();
        String valicode = qdm + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String returnStr = base64.encode(md5.digest(valicode.getBytes("utf-8")));
        return returnStr;
    }

    public static void main(String[] args) {
        SocialSecurityDemo demo = new SocialSecurityDemo();
        try {
//            String returnStr = demo.getInfoByFour("362329199412164516", "邬洁", "szf", demo.getPwd("szf"), PERSONAL_INFORMATION_KEY);
//            String returnStr = demo.getInfoByFour("362329199412164516", "邬洁", "szf", demo.getPwd("szf"), PERSONAL_PENSION_TREATMENT_KEY);
            // 参保证明有问题。。。
// String returnStr = demo.getInfoByFour("362329199412164516", "邬洁", "szf", demo.getPwd("szf"), PERSONAL_GUARANTEE_KEY);
//            String returnStr = demo.getInfoByFive("362329199412164516", "邬洁", "110", "szf", demo.getPwd("szf"),PERSONAL_PAYMENT_BASE_KEY);
//            String returnStr = demo.getInfoByFive("362329199412164516", "邬洁", "110", "szf", demo.getPwd("szf"),PERSONAL_PAYMENT_DETAILS_KEY);
            String returnStr = demo.getInfoByFive("362329199412164516", "邬洁", "110", "szf", demo.getPwd("szf"), TREATMENT_GRANT_KEY);
            System.out.println(returnStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
