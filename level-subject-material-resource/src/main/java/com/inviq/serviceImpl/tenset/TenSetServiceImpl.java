package com.inviq.serviceImpl.tenset;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.dao.tenset.converter.TenSetEntityConverter;
import com.inviq.dao.tenset.TenSetDao;
import com.inviq.dao.tenset.filter.TenSetFilter;
import com.inviq.dto.tenset.request.TenSetAddRequest;
import com.inviq.dto.tenset.request.TenSetDeleteRequest;
import com.inviq.dto.tenset.request.TenSetUpdateRequest;
import com.inviq.service.tenset.TenSetCrudService;
import com.inviq.serviceImpl.CacheService;
import com.inviq.serviceImpl.tenset.usecase.TenSetAddUseCase;
import com.inviq.serviceImpl.tenset.usecase.TenSetDeleteUseCase;
import com.inviq.serviceImpl.tenset.usecase.TenSetGetOneUseCase;
import com.inviq.serviceImpl.tenset.usecase.TenSetUpdateUseCase;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 06/05/2026
 */
@Service
public class TenSetServiceImpl implements TenSetCrudService {

  private final TenSetDao tenSetDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;
  private final CacheService cacheService;

  @Autowired
  public TenSetServiceImpl(
      TenSetDao tenSetDao,
      AuditDao auditDao,
      CacheService cacheService,
      ObjectMapper objectMapper) {
    this.tenSetDao = tenSetDao;
    this.auditDao = auditDao;
    this.cacheService = cacheService;
    this.objectMapper = objectMapper;
  }

  @Override
  public Response create(TenSetAddRequest request) {
    return new TenSetAddUseCase(this.tenSetDao, this.auditDao, this.objectMapper).execute(request);
  }

  @Override
  public Response update(TenSetUpdateRequest request) {
    return new TenSetUpdateUseCase(this.tenSetDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Response delete(TenSetDeleteRequest request) {
    return new TenSetDeleteUseCase(this.tenSetDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Optional<Response> findOne(TenSetFilter request) {
    // TODO CHECK PAYMENT ONCE INTEGRATED
    return new TenSetGetOneUseCase(this.tenSetDao, this.cacheService).execute(request);
  }

  @Override
  public List<Response> findAll(TenSetFilter request) {
    return new TenSetEntityConverter().getResponses(this.tenSetDao.findAll(request));
  }

  @Override
  public Pageable findWithPagination(TenSetFilter tenSetFilter) {
    return this.tenSetDao.findWithPagination(tenSetFilter);
  }
}
