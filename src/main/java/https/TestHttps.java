package https;

import com.alibaba.fastjson.JSONObject;
import util.YhWebserviceUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author JIE WU
 * @create 2018-05-07
 * @desc 测试https
 **/
public class TestHttps {

    public Object yzwhttps() throws Exception {
//        String wsdlUrl = "http://10.16.36.163:9003/yhbus/services/yinHaiBusiness?wsdl";
		String wsdlUrl = "http://172.27.109.200/yhbus/services/yinHaiBusiness?wsdl";
//        String wsdlUrl = "http://localhost:8080/yhbus/services/yinHaiBusiness?wsdl";
//		String wsdlUrl = "http://192.168.17.159:7011/yhbus/services/yinHaiBusiness?wsdl";
//        String serviceKey = "serverService";
        String serviceKey = "getGsCompanyInfo";

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
        Object[] params = new Object[]{xtid, fwid, regionCode, userId, obj.toString()};
        Object result = YhWebserviceUtil.invoke(wsdlUrl,"js_yhxt",serviceKey, JSONObject.toJSONString(params));
        return result;
    }

    public static void main(String[]args){
        TestHttps testHttps = new TestHttps();
        try {
            Object yzwhttps = testHttps.yzwhttps();
            System.out.println(yzwhttps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
