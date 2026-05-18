package com.inviq.core.service.usecase;

import com.inviq.core.dto.filter.Filter;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.BaseEntity;
import java.util.Optional;

/**
 * @author ripesh created on 12/05/2026
 */
public abstract class AbstractGetOneUseCase<
    E extends BaseEntity<E>, ResponseT extends Response, F extends Filter> {

  protected final BaseDao<E, F> entityDao;

  public AbstractGetOneUseCase(BaseDao<E, F> entityDao) {
    this.entityDao = entityDao;
  }

  public Optional<Response> execute(F filter) {
    return this.entityDao.findOne(filter).map(e -> prepareResponse(e, filter));
  }

  protected abstract ResponseT prepareResponse(E entity, F filter);
}
