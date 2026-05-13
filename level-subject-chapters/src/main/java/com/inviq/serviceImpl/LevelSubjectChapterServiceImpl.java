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
import com.inviq.service.LevelSubjectChapterCrudService;
import com.inviq.serviceImpl.affiliate.usecase.LevelSubjectChapterAddUseCase;
import com.inviq.serviceImpl.affiliate.usecase.LevelSubjectChapterDeleteUseCase;
import com.inviq.serviceImpl.affiliate.usecase.LevelSubjectChapterUpdateUseCase;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 07/05/2026
 */
@Service
public class LevelSubjectChapterServiceImpl implements LevelSubjectChapterCrudService {

  private final LevelSubjectChapterDao levelSubjectChapterDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  @Autowired
  public LevelSubjectChapterServiceImpl(
      LevelSubjectChapterDao levelSubjectChapterDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.levelSubjectChapterDao = levelSubjectChapterDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Override
  public Response create(LevelSubjectChapterAddRequest request) {
    return new LevelSubjectChapterAddUseCase(
            this.levelSubjectChapterDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Response update(LevelSubjectChapterUpdateRequest request) {
    return new LevelSubjectChapterUpdateUseCase(
            this.levelSubjectChapterDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

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
    return new LevelSubjectChapterEntityConverter()
        .getResponses(this.levelSubjectChapterDao.findAll(request));
  }

  @Override
  public Pageable findWithPagination(LevelSubjectChapterFilter levelSubjectChapterFilter) {
    return this.levelSubjectChapterDao.findWithPagination(levelSubjectChapterFilter);
  }
}
