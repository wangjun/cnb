package me.yugy.cnbeta;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Api config
 */
public class ApiConfig {

    private static final String API_PREFIX = "http://api.cnbeta.com/capi";

    public static String getNewsList(int startSid, int endSid) {
        String param = "app_key=10000";
        if (endSid != -1) {
            param += "&end_sid=" + endSid;
        }
        param += "&format=json&method=Article.Lists";
        if (startSid != -1) {
            param += "&start_sid=" + startSid;
        }
        param += "&timestamp="+ System.currentTimeMillis() +"&topicid=null&v=1.0&mpuffgvbvbttn3Rc";
        String sign = getSign(param);
        param += "&sign=" + sign;
        return API_PREFIX + "?" + param;
    }

    public static String getNewsDetail(int sid) {
        String param = "app_key=10000&format=json&method=Article.NewsContent&sid=" + sid
                + "&timestamp=" + System.currentTimeMillis() + "&v=1.0&mpuffgvbvbttn3Rc";
        String sign = getSign(param);
        param += "&sign=" + sign;
        return API_PREFIX + "?" + param;
    }

    private static final char[] a = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };

    private static String getSign(String paramString) {
        try
        {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            byte[] arrayOfByte = localMessageDigest.digest();
            StringBuilder localStringBuilder = new StringBuilder(2 * arrayOfByte.length);
            for (int i = 0; ; i++)
            {
                if (i < arrayOfByte.length) {
                    localStringBuilder.append(a[((0xF0 & arrayOfByte[i]) >>> 4)]);
                    localStringBuilder.append(a[(0xF & arrayOfByte[i])]);
                } else {
                    return localStringBuilder.toString().toLowerCase();
                }
            }
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
        {
            localNoSuchAlgorithmException.printStackTrace();
        }
        return "";
    }

}
