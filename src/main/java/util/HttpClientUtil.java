package util;

import org.apache.commons.fileupload.FileItem;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author JIE WU
 * @create 2018-04-11
 * @desc http调用类
 **/
public class HttpClientUtil {
    private static PoolingHttpClientConnectionManager connectionManager;
    private static RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000)
            .setConnectTimeout(5000).setSocketTimeout(120000).build();

    /**
     * post请求
     *
     * @param url    地址
     * @param entity 将参数存放在entity中
     * @return
     */
    public static String post(String url, HttpEntity entity) {
        String resultStr = null;
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(60000).build();
        connectionManager = new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory()).build());
        connectionManager.setDefaultSocketConfig(socketConfig);
        connectionManager.setMaxTotal(400);
        connectionManager.setDefaultMaxPerRoute(40);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig).build();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse httpResponse = null;
        post.setEntity(entity);
        try {
            httpResponse = httpClient.execute(post, HttpClientContext.create());
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                resultStr = EntityUtils.toString(httpResponse.getEntity());
            } else {
                post.abort();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
                post.releaseConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println(resultStr);
        return resultStr;
    }

    /**
     * get请求
     *
     * @param url 地址
     * @return
     */
    public static CloseableHttpResponse get(String url) {
        CloseableHttpResponse response = null;
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(60000).build();
        connectionManager = new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory()).build());
        connectionManager.setDefaultSocketConfig(socketConfig);
        connectionManager.setMaxTotal(400);
        connectionManager.setDefaultMaxPerRoute(40);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig).build();
        HttpClientContext context = HttpClientContext.create();
        HttpGet httpGet = new HttpGet(url);
        try {
            response = httpClient.execute(httpGet, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 获取拼接参数后的url
     *
     * @param url      需要拼接的url
     * @param paramMap 参数map,上传或下载具体参数请参考文档
     * @return String
     */
    public static String getAppendUrl(String url, Map<String, String> paramMap) {
        boolean firstNum = true;
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        Set<Map.Entry<String, String>> paramMapEntry = paramMap.entrySet();
        for (Map.Entry<String, String> entry : paramMapEntry) {
            if (firstNum) {
                sb.append("?").append(entry.getKey()).append("=").append(entry.getValue());
                firstNum = false;
            } else {
                sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return sb.toString();
    }

    /**
     * post添加参数(浏览器请求)
     *
     * @param paramMap  参数
     * @param fileItems 浏览器传过来的流
     * @return
     */
    public static HttpEntity setEntity(Map<String, String> paramMap, List<FileItem> fileItems) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.setCharset(Charset.forName("UTF-8"));
        // 添加参数，具体参数参考文档
        Set<Map.Entry<String, String>> paramMapEntries = paramMap.entrySet();
        for (Map.Entry<String, String> entry : paramMapEntries) {
            builder.addTextBody(entry.getKey(), entry.getValue());
        }
        // 加上for可以上传多个文件
        for (FileItem fileItem : fileItems) {
            boolean isFormField = fileItem.isFormField();
            if (!isFormField) {
                try {
                    builder.addBinaryBody("file", fileItem.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileItem.getName());// 文件流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.build();
    }

    /**
     * post添加参数(本地文件)
     *
     * @param paramMap 参数
     * @param filePath 文件路径
     * @return
     */
    public static HttpEntity setEntity(Map<String, String> paramMap, String filePath) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.setCharset(Charset.forName("UTF-8"));
        // 添加参数，具体参数参考文档
        Set<Map.Entry<String, String>> paramMapEntries = paramMap.entrySet();
        for (Map.Entry<String, String> entry : paramMapEntries) {
            builder.addTextBody(entry.getKey(), entry.getValue());
        }
        try {
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);
            builder.addBinaryBody("file", inputStream, ContentType.MULTIPART_FORM_DATA, file.getName());// 文件流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    /**
     * 将http get方法返回的response中的流存入文件中（文件下载到本地）
     *
     * @param response post返回流
     * @param filePath 文件存放路径
     */
    public static void dealWithResponse(CloseableHttpResponse response, String filePath) {
        BufferedOutputStream outputStream = null;
        FileOutputStream fileOutputStream = null;
        String filename = null;
        int byteLength = 102400;
        byte[] bytes = new byte[102400];
        HttpEntity entity = response.getEntity();
        Header[] allHeaders = response.getAllHeaders();
        for (Header header : allHeaders) {
            // Content-Disposition: attachment;filename=test%E6%88%91%E4%B9%9F.txt
            if ("Content-Disposition".equals(header.getName())) {
                filename = header.getValue().toString();
                filename = filename.substring(filename.indexOf('=') + 1, filename.length());
                break;
            }
        }
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(filePath + File.separator + filename);
        try {
            fileOutputStream = new FileOutputStream(file);
            outputStream = new BufferedOutputStream(fileOutputStream);
            InputStream content = entity.getContent();
            BufferedInputStream inputStream = new BufferedInputStream(content);
            int len = inputStream.read(bytes, 0, byteLength);
            while (len != -1) {
                outputStream.write(bytes, 0, len);
                len = inputStream.read(bytes, 0, byteLength);

            }
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
