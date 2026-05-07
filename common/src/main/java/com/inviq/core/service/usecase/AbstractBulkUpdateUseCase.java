package com.inviq.core.service.usecase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.AuditEntity;
import com.inviq.AuditType;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.context.UserPrincipal;
import com.inviq.core.dto.filter.Filter;
import com.inviq.core.dto.request.crud.BulkRequest;
import com.inviq.core.dto.request.crud.Request;
import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author ripesh created on 30/04/2026
 */
public abstract class AbstractBulkUpdateUseCase<
    E extends Entity<E>, RequestT extends BaseUpdateRequest, F extends Filter> {

  protected final BaseDao<E, F> entityDao;
  protected final BaseBulkDao<E> bulkDao;
  protected final AuditDao auditDao;
  protected final ObjectMapper objectMapper;

  public AbstractBulkUpdateUseCase(
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

  protected void validateRequestWithEntity(
      BulkRequest<RequestT> requests, List<E> entitiesInRecord) {
    Map<String, E> entityMap =
        entitiesInRecord.stream().collect(Collectors.toMap(Entity::getId, Function.identity()));

    for (RequestT request : requests.getItems()) {
      E entity = entityMap.get(request.getId());

      if (entity != null) {
        this.validateDuplicateWithRequestAndEntity(request, entity);
      }
    }
  }

  protected abstract void validateDuplicateWithRequestAndEntity(RequestT request, E entity);

  /*
  To check if the request contains duplicate records / violates unique constraint.
   */
  protected void checkDuplicationWithinRequest(BulkRequest<RequestT> requests) {
    for (int i = 0; i < requests.getItems().size(); i++) {
      for (int j = i + 1; j < requests.getItems().size(); j++) {
        this.validateDuplicateWithInRequest(requests.getItems().get(i), requests.getItems().get(j));
      }
    }
  }

  protected abstract void validateDuplicateWithInRequest(
      RequestT initialReq, RequestT secondaryReq);

  protected abstract Module getModule();

  public List<Response> execute(BulkRequest<RequestT> requests) {
    this.preProcessRequest(requests);
    this.preValidateRequest(requests);
    requests.getItems().forEach(Request::validateRequest);
    requests.getItems().forEach(request -> request.validateBasicInput(request));
    this.checkDuplicationWithinRequest(requests);
    F constraintFilter = this.prepareFilterToCheckUniqueConstrainViolation(requests);
    List<E> entitiesViolatingUniqueConstrain = this.entityDao.findAll(constraintFilter);
    entitiesViolatingUniqueConstrain.forEach(
        entityViolatingUniqueConstrain -> {
          if (requests.getItems().stream()
              .noneMatch(
                  request -> request.getId().equals(entityViolatingUniqueConstrain.getId()))) {
            throw new AppException(DatabaseExceptionManager.DatabaseError.DUPLICATE_RECORD_FOUND);
          }
        });

    F filter = this.prepareFilterToFindEntities(requests);
    List<E> entitiesInRecord = this.entityDao.findAll(filter);

    if (entitiesInRecord.size() != requests.getItems().size())
      throw new AppException(DatabaseExceptionManager.DatabaseError.RECORD_NOT_FOUND);
    this.validateRequestWithEntity(requests, entitiesInRecord);
    List<E> entityInRecordForAudit =
        entitiesInRecord.stream().map(entity -> entity.deepCopy(entity)).toList();
    List<E> entityToUpdate = this.prepareEntityForUpdate(entitiesInRecord, requests);
    this.prepareBaseEntityForUpdate(entityToUpdate);

    Map<String, E> updateMap =
        entityToUpdate.stream().collect(Collectors.toMap(Entity::getId, Function.identity()));

    List<AuditEntity> auditEntities =
        entityInRecordForAudit.stream()
            .filter(r -> updateMap.containsKey(r.getId()))
            .map(r -> this.prepareAuditEntity(r, updateMap.get(r.getId())))
            .toList();

    if (this.auditDao.bulkInsert(auditEntities).isEmpty())
      throw new AppException(AuditExceptionManager.AuditError.AUDIT_DATA_INSERTION_FAILED);
    this.updateEntities(entityToUpdate);
    return this.prepareResponse(entityToUpdate);
  }

  /*
   To check if the current record / request violates database unique constraint.
  */
  protected abstract F prepareFilterToCheckUniqueConstrainViolation(BulkRequest<RequestT> requests);

  /*
   To find the entities to be updated.
  */

  protected abstract F prepareFilterToFindEntities(BulkRequest<RequestT> requests);

  /*
  Make necessary changes to the entity to be updated.
  */

  protected abstract List<E> prepareEntityForUpdate(
      List<E> entities, BulkRequest<RequestT> request);

  protected void prepareBaseEntityForUpdate(List<E> baseEntities) {
    baseEntities.forEach(
        baseEntity -> {
          baseEntity.setModifiedBy(
              ((UserPrincipal)
                      SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                  .getUserId());
          baseEntity.setVersion(baseEntity.getVersion() + 1);
          baseEntity.setModifiedOn(Instant.now());
        });
  }

  protected AuditEntity prepareAuditEntity(E entityInRecord, E updatedEntity) {
    AuditEntity auditEntity = new AuditEntity();
    auditEntity.setId(StringUtil.getUUID());
    auditEntity.setEntity(this.getModule().getCode());
    auditEntity.setOldData(
        this.objectMapper.convertValue(entityInRecord, new TypeReference<>() {}));
    BeanUtils.copyProperties(updatedEntity, entityInRecord);
    auditEntity.setNewData(
        this.objectMapper.convertValue(entityInRecord, new TypeReference<>() {}));
    auditEntity.setEventType(AuditType.UPDATE);
    auditEntity.setEventDate(Instant.now());
    return auditEntity;
  }

  protected void updateEntities(List<E> entityToUpdate) {
    List<E> entity = this.bulkDao.update(entityToUpdate);
    if (entity == null)
      throw new DatabaseExceptionManager(
          DatabaseExceptionManager.DatabaseError.FAILED_TO_UPDATE_ENTITY);
  }

  protected abstract List<Response> prepareResponse(List<E> addedEntities);
}
