package com.inviq.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.dao.LevelSubjectMaterialDao;
import com.inviq.dao.filter.LevelSubjectMaterialFilter;
import com.inviq.dto.tenset.request.LevelSubjectMaterialAddRequest;
import com.inviq.dto.tenset.request.LevelSubjectMaterialDeleteRequest;
import com.inviq.dto.tenset.request.LevelSubjectMaterialUpdateRequest;
import com.inviq.dto.tenset.response.LevelSubjectMaterialSearchResponse;
import com.inviq.service.LevelSubjectMaterialCrudService;
import com.inviq.serviceImpl.usecase.LevelSubjectMaterialAddUseCase;
import com.inviq.serviceImpl.usecase.LevelSubjectMaterialDeleteUseCase;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 06/05/2026
 */
@Service
public class LevelSubjectMaterialService implements LevelSubjectMaterialCrudService {

  private final LevelSubjectMaterialDao levelSubjectMaterialDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  @Autowired
  public LevelSubjectMaterialService(
      LevelSubjectMaterialDao levelSubjectMaterialDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    this.levelSubjectMaterialDao = levelSubjectMaterialDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Override
  public Response create(LevelSubjectMaterialAddRequest request) {
    return new LevelSubjectMaterialAddUseCase(
            this.levelSubjectMaterialDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Response update(LevelSubjectMaterialUpdateRequest request) {
    return null;
  }

  @Override
  public Response delete(LevelSubjectMaterialDeleteRequest request) {
    return new LevelSubjectMaterialDeleteUseCase(
            this.levelSubjectMaterialDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Optional<Response> findOne(LevelSubjectMaterialFilter request) {
    return this.levelSubjectMaterialDao
        .findOne(request)
        .map(
            entity -> {
              LevelSubjectMaterialSearchResponse dto = new LevelSubjectMaterialSearchResponse();
              dto.setId(entity.getId());
              dto.setMaterialId(entity.getMaterial().getId());
              dto.setMaterialName(entity.getMaterial().getMaterial());
              dto.setLevelSubjectId(entity.getLevelSubject().getId());
              dto.setSubjectName(entity.getLevelSubject().getSubject().getSubject());
              return dto;
            });
  }

  @Override
  public List<Response> findAll(LevelSubjectMaterialFilter request) {
    return this.levelSubjectMaterialDao.findAll(request).stream()
        .map(
            entity -> {
              LevelSubjectMaterialSearchResponse dto = new LevelSubjectMaterialSearchResponse();
              dto.setId(entity.getId());
              dto.setMaterialId(entity.getMaterial().getId());
              dto.setMaterialName(entity.getMaterial().getMaterial());
              dto.setLevelSubjectId(entity.getLevelSubject().getId());
              dto.setSubjectName(entity.getLevelSubject().getSubject().getSubject());
              return (Response) dto;
            })
        .toList();
  }

  @Override
  public Pageable findWithPagination(LevelSubjectMaterialFilter levelSubjectMaterialFilter) {
    return this.levelSubjectMaterialDao.findWithPagination(levelSubjectMaterialFilter);
  }
}
