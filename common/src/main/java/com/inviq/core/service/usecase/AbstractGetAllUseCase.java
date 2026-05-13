package com.inviq.core.service.usecase;

import com.inviq.core.dto.filter.Filter;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Converter;
import com.inviq.core.entity.Entity;
import java.util.List;

/**
 * @author ripesh created on 12/05/2026
 */
public abstract class AbstractGetAllUseCase<
    E extends Entity<E>, ResponseT extends Response, F extends Filter> {

  protected final BaseDao<E, F> entityDao;
  protected final Converter<E> converter;

  public AbstractGetAllUseCase(BaseDao<E, F> entityDao, Converter<E> converter) {
    this.entityDao = entityDao;
    this.converter = converter;
  }

  public List<Response> execute(F filter) {
    return this.entityDao.findAll(filter).stream()
        .map(entity -> (Response) this.prepareResponse(entity))
        .toList();
  }

  protected abstract ResponseT prepareResponse(Entity<E> entity);
}
