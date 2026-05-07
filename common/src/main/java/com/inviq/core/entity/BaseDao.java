package com.inviq.core.entity;

import com.inviq.core.dto.filter.Filter;
import java.util.List;
import java.util.Optional;

/**
 * @author ripesh created on 09/04/2025
 */
public interface BaseDao<E extends Entity<E>, F extends Filter> {
  E insert(E entity);

  E update(E entity);

  int delete(E entity);

  List<E> findAll(F filter);

  long count(F filter);

  Pageable findWithPagination(F filter);

  Optional<E> findOne(F filter);

  Optional<E> findById(String id);
}
