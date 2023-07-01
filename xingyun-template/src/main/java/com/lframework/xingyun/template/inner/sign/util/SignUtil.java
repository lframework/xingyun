package com.lframework.xingyun.template.inner.sign.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Api加签、验签工具类
 */
public class SignUtil {

  private static final Logger log = LoggerFactory.getLogger(SignUtil.class);

  /**
   * 加签
   *
   * @param clientId  客户端ID
   * @param apiSecret Api密钥
   * @param timestamp 时间戳
   * @param nonceStr  随机字符串
   * @param json      请求参数
   * @return
   */
  public static String sign(String clientId, String apiSecret, String timestamp, String nonceStr,
      String json) {
    String str = new StringBuilder().append("clientId=").append(clientId).append("&")
        .append("apiSecret=").append(apiSecret)
        .append("&").append("timestamp=").append(timestamp).append("&").append("nonceStr=")
        .append(nonceStr).append("&").append("params=")
        .append(json).toString();
    MessageDigest digest = null;
    try {
      digest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
    byte[] bs = digest.digest(str.getBytes(StandardCharsets.UTF_8));
    StringBuilder builder = new StringBuilder();
    for (byte b : bs) {
      int x = b & 255;
      String s = Integer.toHexString(x);
      if (x > 0 && x < 16) {
        builder.append("0");
        builder.append(s);
      } else {
        builder.append(s);
      }
    }

    return builder.toString();
  }

  /**
   * 验签
   *
   * @param clientId  客户端ID
   * @param apiSecret Api密钥
   * @param timestamp 时间戳
   * @param nonceStr  随机字符串
   * @param json      请求参数
   * @param oriSign   原始签值
   * @return
   */
  public static boolean validate(String clientId, String apiSecret, String timestamp,
      String nonceStr, String json,
      String oriSign) {
    return sign(clientId, apiSecret, timestamp, nonceStr, json).equals(oriSign);
  }

  // 以下为示例
  /*
  public static void main(String[] args) {
    String clientId = "";
    String apiSecret = "";
    String ts = String.valueOf(System.currentTimeMillis());

    //此处为示例的获取字符串算法，此处可以自行替换算法
    String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    Random random = new Random();
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 20; i++) {
      int number = random.nextInt(62);
      builder.append(str.charAt(number));
    }

    String nonceStr = builder.toString();

    String json = "{\"key1\":\"value1\", \"key2\":\"value2\"}";
    String sign = SignUtil.sign(clientId, apiSecret, ts, nonceStr, json); // 这里是加签

    boolean isValidate = SignUtil.validate(clientId, apiSecret, ts, nonceStr, json, sign); // 这里是验签
  }
  */
}
