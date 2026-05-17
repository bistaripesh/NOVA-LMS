package com.inviq.serviceImpl;

/**
 * @author ripesh created on 13/05/2026
 */
public interface CacheService {
  int addByteData(String key, byte[] data, int expiry);

  byte[] getByteData(String key);

  int delete(String key);

  int addStringData(String key, String data, int expiry);

  String getStringData(String key);

  boolean isCacheMiss(String key);
}
