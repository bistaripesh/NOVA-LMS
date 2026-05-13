package com.inviq.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.dao.LevelSubjectsDao;
import com.inviq.dao.converter.LevelSubjectsEntityConverter;
import com.inviq.dao.filter.LevelSubjectsFilter;
import com.inviq.dto.tenset.request.LevelSubjectsAddRequest;
import com.inviq.dto.tenset.request.LevelSubjectsDeleteRequest;
import com.inviq.dto.tenset.request.LevelSubjectsUpdateRequest;
import com.inviq.service.LevelSubjectsCrudService;
import com.inviq.serviceImpl.affiliate.usecase.LevelSubjectsAddUseCase;
import com.inviq.serviceImpl.affiliate.usecase.LevelSubjectsDeleteUseCase;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 05/05/2026
 */
@Service
public class LevelSubjectsServiceImpl implements LevelSubjectsCrudService {

  private final LevelSubjectsDao levelSubjectsDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  @Autowired
  public LevelSubjectsServiceImpl(
      LevelSubjectsDao levelSubjectsDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.levelSubjectsDao = levelSubjectsDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Override
  public Response create(LevelSubjectsAddRequest request) {
    return new LevelSubjectsAddUseCase(this.levelSubjectsDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Response update(LevelSubjectsUpdateRequest request) {
    return null;
  }

  @Override
  public Response delete(LevelSubjectsDeleteRequest request) {
    return new LevelSubjectsDeleteUseCase(this.levelSubjectsDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Optional<Response> findOne(LevelSubjectsFilter request) {
    return this.levelSubjectsDao
        .findOne(request)
        .map(entity -> new LevelSubjectsEntityConverter().getResponse(entity));
  }

  @Override
  public List<Response> findAll(LevelSubjectsFilter request) {
    return new LevelSubjectsEntityConverter().getResponses(this.levelSubjectsDao.findAll(request));
  }

  @Override
  public Pageable findWithPagination(LevelSubjectsFilter levelSubjectsFilter) {
    return this.levelSubjectsDao.findWithPagination(levelSubjectsFilter);
  }
}
