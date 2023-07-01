package com.lframework.xingyun.template.gen.components.magic;

import com.lframework.starter.web.components.redis.RedisHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssssssss.magicapi.modules.db.cache.SqlCache;

@Component
public class MagicCustomSqlCache implements SqlCache {

  @Autowired
  private RedisHandler redisHandler;

  @Override
  public void put(String name, String key, Object value) {
    redisHandler.hset(name, key, value);
  }

  @Override
  public void put(String name, String key, Object value, long ttl) {
    redisHandler.hset(name, key, value, ttl);
  }

  @Override
  public <T> T get(String name, String key) {
    return (T) redisHandler.hget(name, key);
  }

  @Override
  public void delete(String name) {
    redisHandler.hdel(name);
  }
}
