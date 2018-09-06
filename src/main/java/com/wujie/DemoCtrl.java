package com.wujie;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.EofSensorInputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.HttpClientUtil;
import util.IoStreamProcessingTemplate;
import util.IoStreamProcessor;
import util.YhWebserviceUtil;
import yhucm.FileStore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author JIE WU
 * @create 2018-04-11
 * @desc demo调用测试, 测试统一存储上传下载
 **/
@RequestMapping("demo")
@Controller
public class DemoCtrl {


    @RequestMapping("/testHttps")
    @ResponseBody
    public Object testHttps() throws Exception {
        Object[] param = new Object[]{"220581198110151479", "何建国", "1708011900400143"};
        Object returnObj = YhWebserviceUtil.invoke("https://172.27.109.201/yhbus/services/yinHaiBusiness?wsdl", "js_yhxt", "SBKJBXX", JSONObject.toJSONString(param));
        System.out.println(JSONObject.toJSONString(returnObj));
        return returnObj;
    }

    @RequestMapping("/testHttp")
    @ResponseBody
    public Object testHttp() throws Exception {
        Object[] param = new Object[]{"220581198110151479", "何建国", "1708011900400143"};
        Object returnObj = YhWebserviceUtil.invoke("http://172.27.109.200/yhbus/services/yinHaiBusiness?wsdl", "js_yhxt", "SBKJBXX", JSONObject.toJSONString(param));
        System.out.println(JSONObject.toJSONString(returnObj));
        return returnObj;
    }


    @RequestMapping("/downloadFileForMVC")
    @ResponseBody
    public ResponseEntity<byte[]> downloadFileForMVC() throws Exception {
        FileStore fileStore = new FileStore();
        String downloadUrl = fileStore.getDownloadUrl("0f45c2f5dbc643e1aac116d602459cc7");
        CloseableHttpResponse closeableHttpResponse = HttpClientUtil.get(downloadUrl);
        HttpEntity entity = closeableHttpResponse.getEntity();
        InputStream contentStream = entity.getContent();
        MultiValueMap<String, String> multiValueMap = new HttpHeaders();
        for (Header header : closeableHttpResponse.getAllHeaders()) {
            multiValueMap.add(header.getName(), header.getValue());
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[10240];
        BufferedInputStream bis = new BufferedInputStream(contentStream);
        // 处理流
        process(bis, outputStream);
        closeableHttpResponse.close();
        return new ResponseEntity<byte[]>(outputStream.toByteArray(), multiValueMap,
                HttpStatus.valueOf(closeableHttpResponse.getStatusLine().getStatusCode()));
    }

    @RequestMapping("/downloadFileForAll")
    @ResponseBody
    public void downloadFileForAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        FileStore fileStore = new FileStore();
        String downloadUrl = fileStore.getDownloadUrl("0f45c2f5dbc643e1aac116d602459cc7");
        CloseableHttpResponse closeableHttpResponse = HttpClientUtil.get(downloadUrl);
        HttpEntity entity = closeableHttpResponse.getEntity();
        for (Header header : closeableHttpResponse.getAllHeaders()) {
            response.setHeader(header.getName(), header.getValue());
        }
        InputStream contentStream = entity.getContent();
        BufferedInputStream bis = new BufferedInputStream(contentStream);
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        // 处理流
        process(bis, bos);
        closeableHttpResponse.close();
    }


    /**
     * 文件上传
     *
     * @throws Exception
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Object fileUpload(HttpServletRequest request) throws Exception {
        FileStore fileStore = new FileStore();
        Map<String, String> paramMap = fileStore.getUploadUrl();
        // 获取上传文件
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> fileItems = upload.parseRequest(request);
        HttpEntity httpEntity = HttpClientUtil.setEntity(paramMap, fileItems);
        String returnStrl = HttpClientUtil.post(paramMap.get("uploadUrl") + "/ucm/ucmAction!postFile.do", httpEntity);
        return returnStrl;
    }

    public static void process(final InputStream bis, OutputStream bos) throws IOException {
        // 使用io模板
        IoStreamProcessingTemplate.process(bis, bos, new IoStreamProcessor() {
            @Override
            public void process(InputStream inputStream, OutputStream outputStream) throws IOException {
                byte[] buff = new byte[10240];
                int read = bis.read(buff, 0, buff.length);
                while (read != -1) {
                    outputStream.write(buff, 0, read);
                    read = bis.read(buff, 0, buff.length);
                }
            }
        });
    }
}
