package com.inviq.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.dao.SubjectDao;
import com.inviq.dao.converter.SubjectEntityConverter;
import com.inviq.dao.filter.SubjectFilter;
import com.inviq.dto.tenset.request.SubjectAddRequest;
import com.inviq.dto.tenset.request.SubjectDeleteRequest;
import com.inviq.dto.tenset.request.SubjectUpdateRequest;
import com.inviq.service.SubjectCrudService;
import com.inviq.serviceImpl.usecase.SubjectAddUseCase;
import com.inviq.serviceImpl.usecase.SubjectDeleteUseCase;
import com.inviq.serviceImpl.usecase.SubjectUpdateUseCase;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 05/05/2026
 */
@Service
public class SubjectService implements SubjectCrudService {

  private final SubjectDao subjectDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  public SubjectService(SubjectDao subjectDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.subjectDao = subjectDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Transactional
  @Override
  public Response create(SubjectAddRequest request) {
    return new SubjectAddUseCase(this.subjectDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Transactional
  @Override
  public Response update(SubjectUpdateRequest request) {
    return new SubjectUpdateUseCase(this.subjectDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Transactional
  @Override
  public Response delete(SubjectDeleteRequest request) {
    return new SubjectDeleteUseCase(this.subjectDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Optional<Response> findOne(SubjectFilter request) {
    return Optional.empty();
  }

  @Override
  public List<Response> findAll(SubjectFilter request) {
    return new SubjectEntityConverter().getResponses(this.subjectDao.findAll(request));
  }

  @Override
  public Pageable findWithPagination(SubjectFilter subjectFilter) {
    return this.subjectDao.findWithPagination(subjectFilter);
  }
}
