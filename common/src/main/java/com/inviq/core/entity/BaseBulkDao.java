package com.inviq.core.entity;

import java.util.List;

/**
 * @author ripesh created on 30/04/2026
 */
public interface BaseBulkDao<E extends Entity<E>> {

  List<E> insert(List<E> entities);

  List<E> update(List<E> entities);

  int delete(List<E> entities);

  List<E> findByIds(List<String> ids);
}
