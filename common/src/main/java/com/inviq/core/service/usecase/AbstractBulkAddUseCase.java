package com.inviq.core.service.usecase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.AuditEntity;
import com.inviq.AuditType;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.filter.Filter;
import com.inviq.core.dto.request.crud.BulkRequest;
import com.inviq.core.dto.request.crud.add.BaseAddRequest;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.BaseBulkDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Entity;
import com.inviq.core.entity.Module;
import com.inviq.core.entity.exception.DatabaseExceptionManager;
import com.inviq.core.exception.AppException;
import com.inviq.core.exception.manager.AuditExceptionManager;
import com.inviq.core.util.StringUtil;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ripesh created on 30/04/2026
 */
public abstract class AbstractBulkAddUseCase<
    E extends Entity<E>, RequestT extends BaseAddRequest, F extends Filter> {

  protected final BaseDao<E, F> entityDao;
  protected final BaseBulkDao<E> bulkDao;
  protected final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  public AbstractBulkAddUseCase(
      BaseDao<E, F> entityDao,
      BaseBulkDao<E> bulkDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    this.entityDao = entityDao;
    this.bulkDao = bulkDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  protected abstract void preProcessRequest(BulkRequest<RequestT> requests);

  protected abstract void preValidateRequest(BulkRequest<RequestT> requests);

  public List<Response> execute(BulkRequest<RequestT> requests) {
    this.preProcessRequest(requests);
    this.preValidateRequest(requests);
    requests.getItems().forEach(RequestT::validateRequest);
    this.checkDuplicationWithinRequest(requests);
    F filter = this.prepareFilterToFindDuplicateRecord(requests);
    this.checkDuplicateRecord(filter);
    List<E> entitiesToAdd = this.prepareEntities(requests);
    this.prepareBaseEntity(entitiesToAdd);
    List<AuditEntity> auditEntities =
        this.auditDao.bulkInsert(this.prepareAuditEntities(entitiesToAdd));
    if (auditEntities.isEmpty())
      throw new AppException(AuditExceptionManager.AuditError.AUDIT_DATA_INSERTION_FAILED);
    this.insertEntities(entitiesToAdd);
    return this.prepareResponse(entitiesToAdd);
  }

  protected abstract Module getModule();

  protected abstract F prepareFilterToFindDuplicateRecord(BulkRequest<RequestT> requests);

  protected void checkDuplicationWithinRequest(BulkRequest<RequestT> requests) {
    for (int i = 0; i < requests.getItems().size(); i++) {
      for (int j = i + 1; j < requests.getItems().size(); j++) {
        this.validateDuplicateWithInRequest(requests.getItems().get(i), requests.getItems().get(j));
      }
    }
  }

  protected abstract void validateDuplicateWithInRequest(
      RequestT initialReq, RequestT secondaryReq);

  protected void checkDuplicateRecord(F filter) {
    if (!this.entityDao.findAll(filter).isEmpty())
      throw new AppException(DatabaseExceptionManager.DatabaseError.DUPLICATE_RECORD_FOUND);
  }

  protected abstract List<E> prepareEntities(BulkRequest<RequestT> requests);

  protected void prepareBaseEntity(List<E> entitiesToAdd) {
    entitiesToAdd.forEach(
        entityToAdd -> {
          entityToAdd.setId(StringUtil.getUUID());
          entityToAdd.setVersion(1);
          entityToAdd.setCreatedOn(Instant.now());
          entityToAdd.setModifiedOn(Instant.now());
        });
  }

  protected void insertEntities(List<E> entities) {
    List<E> entity = this.bulkDao.insert(entities);
    if (entity == null)
      throw new AppException(DatabaseExceptionManager.DatabaseError.FAILED_TO_INSERT_ENTITY);
  }

  protected List<AuditEntity> prepareAuditEntities(List<E> entitiesToAdd) {
    return entitiesToAdd.stream()
        .map(
            entityToAdd -> {
              AuditEntity auditEntity = new AuditEntity();
              auditEntity.setId(StringUtil.getUUID());
              auditEntity.setEntity(this.getModule().getCode());
              auditEntity.setNewData(
                  this.objectMapper.convertValue(entityToAdd, new TypeReference<>() {}));
              auditEntity.setOldData(null);
              auditEntity.setEventType(AuditType.INSERT);
              auditEntity.setEventDate(Instant.now());
              return auditEntity;
            })
        .collect(Collectors.toList());
  }

  protected abstract List<Response> prepareResponse(List<E> addedEntities);
}
