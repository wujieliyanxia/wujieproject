package util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class PwdUtil {
    /**
     * 工具
     *
     * @param user
     * @param pwd
     * @param sid
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String makeParam(String user, String pwd, String sid, Map<String, String> map)
            throws UnsupportedEncodingException {
        Set<Map.Entry<String, String>> set = map.entrySet();
        String param = "";
        for (Map.Entry<String, String> entry : set) {
            param = param + entry.getKey() + "=\"" + entry.getValue() + "\" ";
        }
        return getParam(new String[] { user, pwd, sid, URLEncoder.encode(param, "UTF-8") });
    }

    private static String getParam(String[] param) {
        String str = "[";
        for (String s : param) {
            str += "\"" + s + "\",";
        }
        str = str.substring(0, str.length() - 1);
        str += "]";
        return str;
    }


    public static String getPwd(String accessId) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64 = new BASE64Encoder();
            String valicode =accessId
                    + new SimpleDateFormat("yyyyMMdd").format(new Date());
            String str = base64.encode(md5.digest(valicode.getBytes("utf-8")));
            return str;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
