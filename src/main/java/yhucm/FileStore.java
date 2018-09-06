package yhucm;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import util.GeneralWebserviceUtil;
import util.HttpClientUtil;
import util.YhWebserviceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JIE WU
 * @create 2018-04-11
 * @desc 文件存储和下载
 **/
public class FileStore {
    // 统一存储接口地址
    final static String YHBUS_URL = "http://10.16.36.163:9003/yhbus/services/yinHaiBusiness?wsdl";
    // 获取上传寻址方法
    final static String UPLOAD_FILE_METHOD = "getUploadUrl";
    // 获取下载寻址方法
    final static String DOWNLOAD_FILE_METHOD = "getDownloadUrl";
    // systemKey
    final static String SYSTEM_KEY = "yh_jlldgx";

    /**
     * 上传寻址
     *
     * @return paramMap参数
     */
    public Map<String, String> getUploadUrl() throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("sysid", "jbroker");// 业务系统id
        paramMap.put("addrcode", "220000000000");// 寻址代码
        paramMap.put("loginid", "1");
        paramMap.put("busitype", "FILE");// 业务类别
        Object object = YhWebserviceUtil.invoke(YHBUS_URL,SYSTEM_KEY,UPLOAD_FILE_METHOD, JSONObject.toJSONString(paramMap));
        JSONObject jsonObject = JSONObject.parseObject(object.toString());
        if ("true".equals(jsonObject.getString("bizSuccess"))) {
            String uploadUrl = jsonObject.getString("data");
            paramMap.put("uploadUrl", uploadUrl);
        } else {
            throw new Exception("上传寻址失败：" + jsonObject);
        }

        return paramMap;
    }


    /**
     * 文件上传
     *
     * @param filePath 本地路径名称
     * @return String
     */
    public String fileUpload(String filePath) {
        Map<String, String> paramMap;
        try {
            paramMap = getUploadUrl();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        HttpEntity httpEntity = HttpClientUtil.setEntity(paramMap, filePath);
        String returnStr = HttpClientUtil.post(paramMap.get("uploadUrl") + "/ucm/ucmAction!postFile.do", httpEntity);
        return returnStr;
    }

    /**
     * 下载寻址
     *
     * @param fileId 文件id
     * @return url
     */
    public String getDownloadUrl(String fileId) throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        String downloadUrl;
        paramMap.put("fileid", fileId);// 文件id，上传返回的id
        paramMap.put("sysid", "jbroker");// 业务系统id
        paramMap.put("addrcode", "220000000000");// 寻址代码
        paramMap.put("loginid", "1");
        paramMap.put("busitype", "FILE");// 业务类别
        paramMap.put("expiretime", "0");// accesskey超时时间
        Object object = YhWebserviceUtil.invoke(YHBUS_URL,SYSTEM_KEY, DOWNLOAD_FILE_METHOD, JSONObject.toJSONString(paramMap));
        JSONObject jsonObject = JSONObject.parseObject(object.toString());
        if ("true".equals(jsonObject.getString("bizSuccess"))) {
            Map<String, String> dataMap = (Map<String, String>) jsonObject.get("data");
            downloadUrl = dataMap.get("downloadurl");
            paramMap.put("accesskey", dataMap.get("accesskey"));
            paramMap.put("realid", dataMap.get("realid"));
        } else {
            throw new Exception("下载寻址失败：" + jsonObject);
        }
        String url = HttpClientUtil.getAppendUrl(downloadUrl + "/ucm/ucmAction!getFile.do", paramMap);
        return url;
    }

    /**
     * 文件下载
     */
    public void fileDownLoad(String fileId) {
        String downloadUrl;
        try {
            downloadUrl = getDownloadUrl(fileId);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        CloseableHttpResponse closeableHttpResponse = HttpClientUtil.get(downloadUrl);
        HttpClientUtil.dealWithResponse(closeableHttpResponse, "E:\\统一存储");
    }

    public static void main(String[] args) {
        // 上传
        /*FileStore fileStore = new FileStore();
        String returnStr = fileStore.fileUpload("E:\\test我也.txt");
        System.out.println(returnStr);*/
        /**
         * 下载
         * fileid为上传返回的值
         */
        FileStore fileStore = new FileStore();
        fileStore.fileDownLoad("c6cbfce5b14c4683a78bf684bacc65c0");
    }
}
