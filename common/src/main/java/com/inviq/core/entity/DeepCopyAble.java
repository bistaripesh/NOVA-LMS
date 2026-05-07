package com.inviq.core.entity;

/**
 * @author ripesh created on 24/04/2026
 */
public interface DeepCopyAble<T> {
  T deepCopy(T other);
}
