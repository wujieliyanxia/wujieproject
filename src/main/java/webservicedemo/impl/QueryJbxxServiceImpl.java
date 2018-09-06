package webservicedemo.impl;

import com.alibaba.fastjson.JSONObject;
import webservicedemo.QueryJbxxService;

import javax.jws.WebService;


/**
 * @author JIE WU
 * @create 2018-04-16
 * @desc test查询接口实现类
 **/
@WebService(endpointInterface = "webservicedemo.QueryJbxxService", serviceName = "queryJbxxService"
            ,targetNamespace="http://webservicedemo/")
public class QueryJbxxServiceImpl implements QueryJbxxService {
    @Override
    public String query(String inputStr) {
        JSONObject jsonObject = JSONObject.parseObject(inputStr);
        return jsonObject.toJSONString();
    }
}
