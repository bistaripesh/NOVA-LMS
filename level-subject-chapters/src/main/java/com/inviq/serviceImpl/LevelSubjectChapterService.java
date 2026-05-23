package com.inviq.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.dao.LevelSubjectChapterDao;
import com.inviq.dao.affiliate.converter.LevelSubjectChapterEntityConverter;
import com.inviq.dao.affiliate.filter.LevelSubjectChapterFilter;
import com.inviq.dto.request.LevelSubjectChapterAddRequest;
import com.inviq.dto.request.LevelSubjectChapterDeleteRequest;
import com.inviq.dto.request.LevelSubjectChapterUpdateRequest;
import com.inviq.service.LevelSubjectAccessService;
import com.inviq.service.LevelSubjectChapterCrudService;
import com.inviq.serviceImpl.usecase.LevelSubjectChapterAddUseCase;
import com.inviq.serviceImpl.usecase.LevelSubjectChapterDeleteUseCase;
import com.inviq.serviceImpl.usecase.LevelSubjectChapterUpdateUseCase;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 07/05/2026
 */
@Service
public class LevelSubjectChapterService implements LevelSubjectChapterCrudService {

  private final LevelSubjectChapterDao levelSubjectChapterDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;
  private final LevelSubjectAccessService levelSubjectAccessService;

  @Autowired
  public LevelSubjectChapterService(
      LevelSubjectAccessService levelSubjectAccessService,
      LevelSubjectChapterDao levelSubjectChapterDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    this.levelSubjectAccessService = levelSubjectAccessService;
    this.levelSubjectChapterDao = levelSubjectChapterDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Transactional
  @Override
  public Response create(LevelSubjectChapterAddRequest request) {
    return new LevelSubjectChapterAddUseCase(
            this.levelSubjectChapterDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Transactional
  @Override
  public Response update(LevelSubjectChapterUpdateRequest request) {
    return new LevelSubjectChapterUpdateUseCase(
            this.levelSubjectChapterDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Transactional
  @Override
  public Response delete(LevelSubjectChapterDeleteRequest request) {
    return new LevelSubjectChapterDeleteUseCase(
            this.levelSubjectChapterDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Optional<Response> findOne(LevelSubjectChapterFilter request) {
    return this.levelSubjectChapterDao
        .findOne(request)
        .map(entity -> new LevelSubjectChapterEntityConverter().getResponse(entity));
  }

  @Override
  public List<Response> findAll(LevelSubjectChapterFilter request) {
    this.levelSubjectAccessService.ensureAccess(request.getLevelSubjectId());
    return new LevelSubjectChapterEntityConverter()
        .getResponses(this.levelSubjectChapterDao.findAll(request));
  }

  @Override
  public Pageable findWithPagination(LevelSubjectChapterFilter levelSubjectChapterFilter) {
    this.levelSubjectAccessService.ensureAccess(levelSubjectChapterFilter.getLevelSubjectId());
    return this.levelSubjectChapterDao.findWithPagination(levelSubjectChapterFilter);
  }
}
