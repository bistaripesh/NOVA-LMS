package com.inviq.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.dao.AffiliateReferralDao;
import com.inviq.dao.converter.AffiliateReferralEntityConverter;
import com.inviq.dao.filter.AffiliateReferralFilter;
import com.inviq.dto.request.AffiliateReferralAddRequest;
import com.inviq.dto.request.AffiliateReferralDeleteRequest;
import com.inviq.dto.request.AffiliateReferralUpdateRequest;
import com.inviq.service.AffiliateReferralCrudService;
import com.inviq.serviceImpl.usecase.AffiliateReferralAddUseCase;
import com.inviq.serviceImpl.usecase.AffiliateReferralDeleteUseCase;
import com.inviq.serviceImpl.usecase.AffiliateReferralUpdateUseCase;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 13/05/2026
 */
@Service
public class AffiliateReferralServiceImpl implements AffiliateReferralCrudService {

  private final AffiliateReferralDao affiliateReferralDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  @Autowired
  public AffiliateReferralServiceImpl(
      AffiliateReferralDao affiliateReferralDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.affiliateReferralDao = affiliateReferralDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Override
  public Response create(AffiliateReferralAddRequest request) {
    return new AffiliateReferralAddUseCase(
            this.affiliateReferralDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Response update(AffiliateReferralUpdateRequest request) {
    return new AffiliateReferralUpdateUseCase(
            this.affiliateReferralDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Response delete(AffiliateReferralDeleteRequest request) {
    AffiliateReferralUpdateRequest updateRequest = new AffiliateReferralUpdateRequest();
    updateRequest.setId(request.getId());
    return new AffiliateReferralDeleteUseCase(
            this.affiliateReferralDao, this.auditDao, this.objectMapper)
        .execute(updateRequest);
  }

  @Override
  public Optional<Response> findOne(AffiliateReferralFilter request) {
    return this.affiliateReferralDao
        .findOne(request)
        .map(entity -> new AffiliateReferralEntityConverter().getResponse(entity));
  }

  @Override
  public List<Response> findAll(AffiliateReferralFilter request) {
    return new AffiliateReferralEntityConverter()
        .getResponses(this.affiliateReferralDao.findAll(request));
  }

  @Override
  public Pageable findWithPagination(AffiliateReferralFilter affiliateReferralFilter) {
    return this.affiliateReferralDao.findWithPagination(affiliateReferralFilter);
  }
}
