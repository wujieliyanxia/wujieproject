package webservicedemo;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Map;

/**
 * @author JIE WU
 * @create 2018-04-16
 * @desc test查询接口
 **/
@WebService
public interface QueryJbxxService {
    /**
     * 查询
     *
     * @return map
     */
    @WebMethod(operationName = "query")
    @WebResult
    String query(@WebParam(name = "inputStr") String inputStr);
}
