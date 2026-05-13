package com.inviq.core.entity;

import com.inviq.core.dto.response.Response;
import java.util.List;

/**
 * @author ripesh created on 12/05/2026
 */
public interface Converter<E extends Entity<E>> {

  Response getResponse(E entity);

  default List<Response> getResponses(List<E> entities) {
    return entities.stream().map(this::getResponse).toList();
  }
}
