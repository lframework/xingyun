package com.dstz.agilebpm.security.autoconfiguration;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.dstz.base.core.cache.ICache;
import com.dstz.base.core.jwt.JWTService;
import com.dstz.base.core.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class CustomJWTService extends JWTService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private ICache<String> icache;

  /**
   * 密钥
   */
  @Value("${ab.jwt.secret:asd%WE^@&fas156dfa}")
  private String secret;

  /**
   * jwt签发者名称
   */
  @Value("${ab.jwt.issuer:agileBPM}")
  private String issuer;

  @Value("${ab.jwt.valid:true}")
  private Boolean valid;

  @Override
  public String getValidSubjectFromRedisToken(String authToken) {
    if (StringUtil.isEmpty(authToken)) {
      return null;
    }
    try {
      Claims claims = getClaimsFromToken(authToken);

      if (claims != null) {
        String token = icache.getByKey(JWT_CACHE_REGION,
            String.format("jwt:%s:%s", claims.getAudience(), claims.getSubject()));
        if (StringUtil.isEmpty(token)) {
          logger.debug(
              "JWT token 校验失败，token 已过期,签发时间 " + DateUtil.formatDateTime(claims.getIssuedAt()));
          return null;
        }

        // 有效则放行，并更新剩余时间
        if (valid && authToken.equals(token)) {
          icache.add2Region(JWT_CACHE_REGION,
              String.format("jwt:%s:%s", claims.getAudience(), claims.getSubject()), token);
          return claims.getSubject();
        } else if (!valid) {
          return claims.getSubject();
        } else {
          logger.info("JWT token 校验失败，服务器 token 与 被校验 token 不一致！ 同一签发对象的 token 不支持多地登录 {}",
              claims);
        }
      }

    } catch (Exception e) {
      logger.warn("解析令牌失败", e);
      return null;
    }
    return null;
  }

  @Override
  public void logoutRedisToken(String authToken) {
    if (StringUtil.isEmpty(authToken)) {
      return;
    }
    try {
      Claims claims = getClaimsFromToken(authToken);

      if (claims != null) {
        icache.delByKey(JWT_CACHE_REGION,
            String.format("jwt:%s:%s", claims.getAudience(), claims.getSubject()));
      }
    } catch (Exception e) {
      logger.warn("解析令牌失败", e);
    }
  }

  @Override
  public String generateToken(String username, String audience) {
    Assert.notBlank(audience, "生成token 签发对象 不能为空");

    String token = Jwts.builder()
        // jwt签发者
        .setIssuer(issuer)
        // jwt所面向的用户
        .setSubject(username)
        // 接收jwt的一方
        .setAudience(audience)
        //	.setExpiration(new Date(System.currentTimeMillis() + expirationMinute * 60 * 1000))
        //	.setNotBefore(new Date(System.currentTimeMillis() - notBeforeMinute * 60 * 1000))
        .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS512, secret).compact();

    // 由缓存负责续期，以及超时判断。且服务端可以注销令牌使用
    if (icache != null) {
      icache.add2Region(JWT_CACHE_REGION, String.format("jwt:%s:%s", audience, username), token);
    }

    return token;
  }

  /**
   * 从令牌中获取数据声明
   *
   * @param token 令牌
   * @return 数据声明
   */
  private Claims getClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }
}
