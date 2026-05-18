package com.inviq.serviceImpl.affiliate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.dao.affiliate.AffiliateDao;
import com.inviq.dao.affiliate.converter.AffiliateEntityConverter;
import com.inviq.dao.affiliate.filter.AffiliateFilter;
import com.inviq.dto.affiliate.request.AffiliateAddRequest;
import com.inviq.dto.affiliate.request.AffiliateDeleteRequest;
import com.inviq.dto.affiliate.request.AffiliateUpdateRequest;
import com.inviq.service.affiliate.AffiliateCurdService;
import com.inviq.serviceImpl.affiliate.usecase.AffiliateAddUseCase;
import com.inviq.serviceImpl.affiliate.usecase.AffiliateDeleteUseCase;
import com.inviq.serviceImpl.affiliate.usecase.AffiliateUpdateUseCase;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 12/05/2026
 */
@Service
public class AffiliateService implements AffiliateCurdService {

  private final AffiliateDao affiliateDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  @Autowired
  public AffiliateService(AffiliateDao affiliateDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.affiliateDao = affiliateDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Transactional
  @Override
  public Response create(AffiliateAddRequest request) {
    return new AffiliateAddUseCase(this.affiliateDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Transactional
  @Override
  public Response update(AffiliateUpdateRequest request) {
    return new AffiliateUpdateUseCase(this.affiliateDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Transactional
  @Override
  public Response delete(AffiliateDeleteRequest request) {
    return new AffiliateDeleteUseCase(this.affiliateDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Optional<Response> findOne(AffiliateFilter request) {
    return this.affiliateDao
        .findOne(request)
        .map(entity -> new AffiliateEntityConverter().getResponse(entity));
  }

  @Override
  public List<Response> findAll(AffiliateFilter request) {
    return new AffiliateEntityConverter().getResponses(this.affiliateDao.findAll(request));
  }

  @Override
  public Pageable findWithPagination(AffiliateFilter affiliateFilter) {
    return this.affiliateDao.findWithPagination(affiliateFilter);
  }
}
