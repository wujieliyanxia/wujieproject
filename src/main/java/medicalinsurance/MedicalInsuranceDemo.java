package medicalinsurance;

import com.alibaba.fastjson.JSONObject;
import util.YhWebserviceUtil;

/**
 * @author JIE WU
 * @create 2018-05-17
 * @desc 医保接口测试用例
 **/
public class MedicalInsuranceDemo {
    // 正式协同地址
//    static String WSDL_URL = "http://172.27.109.200/yhbus/services/yinHaiBusiness?wsdl";
    // 测试协同地址
//    static String WSDL_URL = "http://10.16.36.163:9003/yhbus/services/yinHaiBusiness?wsdl";
    static String WSDL_URL = "https://172.27.109.201/yhbus/services/yinHaiBusiness?wsdl";
    // 协同上配置的系统id
    static String SYSTEM_KEY = "yzw";
    //


    /**
     * 个人基本信息查询
     *
     * @param aac003 姓名
     * @param aae135 身份证
     * @param aaa027 统筹区
     * @return 参考接口文档
     */
    public Object getPjbxx(String aac003, String aae135, String aaa027) throws Exception {
        Object[] paramObj = new Object[]{
                aac003,
                aae135,
                aaa027
        };
        Object returnObj = YhWebserviceUtil.invoke(WSDL_URL, SYSTEM_KEY, "getPjbxx", JSONObject.toJSONString(paramObj));
        return returnObj;
    }

    public static void main(String[] args) {
        MedicalInsuranceDemo demo = new MedicalInsuranceDemo();
        try {
            Object returnObj = demo.getPjbxx("李XX", "3623291994121XXXX" , "220100");
            System.out.println(returnObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
