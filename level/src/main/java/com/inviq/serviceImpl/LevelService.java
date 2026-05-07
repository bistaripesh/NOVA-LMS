package com.inviq.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.dao.LevelDao;
import com.inviq.dao.filter.LevelFilter;
import com.inviq.dto.tenset.request.LevelAddRequest;
import com.inviq.dto.tenset.request.LevelDeleteRequest;
import com.inviq.dto.tenset.request.LevelUpdateRequest;
import com.inviq.dto.tenset.response.LevelSearchResponse;
import com.inviq.service.LevelCrudService;
import com.inviq.serviceImpl.usecase.LevelAddUseCase;
import com.inviq.serviceImpl.usecase.LevelDeleteUseCase;
import com.inviq.serviceImpl.usecase.LevelUpdateUseCase;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 05/05/2026
 */
@Service
public class LevelService implements LevelCrudService {

  private final LevelDao levelDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  @Autowired
  public LevelService(LevelDao levelDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.levelDao = levelDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Override
  public Response create(LevelAddRequest request) {
    return new LevelAddUseCase(this.levelDao, this.auditDao, this.objectMapper).execute(request);
  }

  @Override
  public Response update(LevelUpdateRequest request) {
    return new LevelUpdateUseCase(this.levelDao, this.auditDao, this.objectMapper).execute(request);
  }

  @Override
  public Response delete(LevelDeleteRequest request) {
    return new LevelDeleteUseCase(this.levelDao, this.auditDao, this.objectMapper).execute(request);
  }

  @Override
  public Optional<Response> findOne(LevelFilter request) {
    return Optional.empty();
  }

  @Override
  public List<Response> findAll(LevelFilter request) {
    return this.levelDao.findAll(request).stream()
        .map(
            entity -> {
              LevelSearchResponse dto = new LevelSearchResponse();
              dto.setId(entity.getId());
              dto.setLevelName(entity.getLevel());
              return (Response) dto;
            })
        .toList();
  }

  @Override
  public Pageable findWithPagination(LevelFilter levelFilter) {
    return this.levelDao.findWithPagination(levelFilter);
  }
}
