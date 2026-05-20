package com.inviq.service;

import com.inviq.serviceImpl.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.RedisClient;
import redis.clients.jedis.params.SetParams;

/**
 * @author ripesh created on 13/05/2026
 */
@Service
public class CacheServiceImpl implements CacheService {

  private final RedisClient redisClient;

  @Autowired
  public CacheServiceImpl(RedisClient redisClient) {
    this.redisClient = redisClient;
  }

  @Override
  public int addByteData(String key, byte[] data, int expiry) {
    SetParams params = new SetParams().ex(expiry);
    return this.redisClient.set(key.getBytes(), data, params).equals("OK") ? 1 : 0;
  }

  @Override
  public byte[] getByteData(String key) {
    return this.redisClient.get(key.getBytes());
  }

  @Override
  public int delete(String key) {
    return (int) this.redisClient.del(key.getBytes());
  }

  @Override
  public int addStringData(String key, String data, int expiry) {
    SetParams params = new SetParams().ex(expiry);
    return this.redisClient.set(key, data, params).equals("OK") ? 1 : 0;
  }

  @Override
  public String getStringData(String key) {
    return this.redisClient.get(key);
  }

  @Override
  public boolean isCacheMiss(String key) {
    return !this.redisClient.exists(key);
  }
}
