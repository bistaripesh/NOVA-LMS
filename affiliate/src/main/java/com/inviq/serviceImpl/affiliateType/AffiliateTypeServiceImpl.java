package com.inviq.serviceImpl.affiliateType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.dao.affiliateType.AffiliateTypeDao;
import com.inviq.dao.affiliateType.converter.AffiliateTypeEntityConverter;
import com.inviq.dao.affiliateType.filter.AffiliateTypeFilter;
import com.inviq.dto.affiliateType.request.AffiliateTypeAddRequest;
import com.inviq.dto.affiliateType.request.AffiliateTypeDeleteRequest;
import com.inviq.dto.affiliateType.request.AffiliateTypeUpdateRequest;
import com.inviq.service.affiliateType.AffiliateTypeCrudService;
import com.inviq.serviceImpl.affiliateType.usecase.AffiliateTypeAddUseCase;
import com.inviq.serviceImpl.affiliateType.usecase.AffiliateTypeDeleteUseCase;
import com.inviq.serviceImpl.affiliateType.usecase.AffiliateTypeUpdateUseCase;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 12/05/2026
 */
@Service
public class AffiliateTypeServiceImpl implements AffiliateTypeCrudService {

  private final AffiliateTypeDao affiliateTypeDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  @Autowired
  public AffiliateTypeServiceImpl(
      AffiliateTypeDao affiliateTypeDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.affiliateTypeDao = affiliateTypeDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Override
  public Response create(AffiliateTypeAddRequest request) {
    return new AffiliateTypeAddUseCase(this.affiliateTypeDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Response update(AffiliateTypeUpdateRequest request) {
    return new AffiliateTypeUpdateUseCase(this.affiliateTypeDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Response delete(AffiliateTypeDeleteRequest request) {
    return new AffiliateTypeDeleteUseCase(this.affiliateTypeDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Optional<Response> findOne(AffiliateTypeFilter request) {
    return Optional.empty();
  }

  @Override
  public List<Response> findAll(AffiliateTypeFilter request) {
    return new AffiliateTypeEntityConverter().getResponses(this.affiliateTypeDao.findAll(request));
  }

  @Override
  public Pageable findWithPagination(AffiliateTypeFilter affiliateTypeFilter) {
    return this.affiliateTypeDao.findWithPagination(affiliateTypeFilter);
  }
}
