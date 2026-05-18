package com.inviq.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.dao.MaterialDao;
import com.inviq.dao.converter.MaterialEntityConverter;
import com.inviq.dao.filter.MaterialFilter;
import com.inviq.dto.tenset.request.MaterialAddRequest;
import com.inviq.dto.tenset.request.MaterialDeleteRequest;
import com.inviq.dto.tenset.request.MaterialUpdateRequest;
import com.inviq.service.MaterialCrudService;
import com.inviq.serviceImpl.usecase.MaterialAddUseCase;
import com.inviq.serviceImpl.usecase.MaterialDeleteUseCase;
import com.inviq.serviceImpl.usecase.MaterialUpdateUseCase;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 05/05/2026
 */
@Service
public class MaterialService implements MaterialCrudService {

  private final MaterialDao materialDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  @Autowired
  public MaterialService(MaterialDao materialDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.materialDao = materialDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Transactional
  @Override
  public Response create(MaterialAddRequest request) {
    return new MaterialAddUseCase(this.materialDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Transactional
  @Override
  public Response update(MaterialUpdateRequest request) {
    return new MaterialUpdateUseCase(this.materialDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Transactional
  @Override
  public Response delete(MaterialDeleteRequest request) {
    return new MaterialDeleteUseCase(this.materialDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Optional<Response> findOne(MaterialFilter request) {
    return this.materialDao
        .findOne(request)
        .map(entity -> new MaterialEntityConverter().getResponse(entity));
  }

  @Override
  public List<Response> findAll(MaterialFilter request) {
    return new MaterialEntityConverter().getResponses(this.materialDao.findAll(request));
  }

  @Override
  public Pageable findWithPagination(MaterialFilter materialFilter) {
    return this.materialDao.findWithPagination(materialFilter);
  }
}
