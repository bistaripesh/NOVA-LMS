package com.inviq.core.service.usecase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.AuditEntity;
import com.inviq.AuditType;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.context.UserPrincipal;
import com.inviq.core.dto.filter.Filter;
import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Entity;
import com.inviq.core.entity.Module;
import com.inviq.core.entity.exception.DatabaseExceptionManager;
import com.inviq.core.exception.AppException;
import com.inviq.core.exception.manager.AuditExceptionManager;
import com.inviq.core.util.StringUtil;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author ripesh created on 16/04/2026
 */
public abstract class AbstractUpdateUseCase<
    E extends Entity<E>,
    RequestT extends BaseUpdateRequest,
    ResponseT extends Response,
    F extends Filter> {

  protected final BaseDao<E, F> entityDao;
  protected final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  public AbstractUpdateUseCase(
      BaseDao<E, F> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.entityDao = entityDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  protected abstract void preValidateRequest(RequestT request);

  protected abstract void validateRequestWithEntity(RequestT request, E entityInRecord);

  protected abstract void preProcessRequest(RequestT request);

  public Response execute(RequestT request) {
    this.preProcessRequest(request);
    this.preValidateRequest(request);
    request.validateBasicInput(request);
    request.validateRequest();
    F filter = this.prepareFilterToFindDuplicateRecord(request);
    this.checkDuplicateRecord(filter, request);
    E entityInRecord =
        this.entityDao
            .findById(request.getId())
            .orElseThrow(
                () -> new AppException(DatabaseExceptionManager.DatabaseError.RECORD_NOT_FOUND));
    this.validateRequestWithEntity(request, entityInRecord);
    E entityInRecordForAudit = entityInRecord.deepCopy(entityInRecord);
    E entityToUpdate = this.prepareEntityForUpdate(entityInRecord, request);
    this.prepareBaseEntityForUpdate(entityToUpdate);
    AuditEntity auditEntity =
        this.auditDao.insert(this.prepareAuditEntity(entityInRecordForAudit, entityToUpdate));
    if (auditEntity == null)
      throw new AppException(AuditExceptionManager.AuditError.AUDIT_DATA_INSERTION_FAILED);
    this.updateEntity(entityToUpdate);
    return this.prepareResponse(entityToUpdate);
  }

  protected abstract Module getModule();

  protected abstract F prepareFilterToFindDuplicateRecord(RequestT request);

  protected void checkDuplicateRecord(F filter, RequestT request) {
    Optional<E> entity = this.entityDao.findOne(filter);

    if (entity.isPresent() && !entity.get().getId().equals(request.getId())) {

      throw new AppException(DatabaseExceptionManager.DatabaseError.DUPLICATE_RECORD_FOUND);
    }
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

  protected void prepareBaseEntityForUpdate(E baseEntity) {
    baseEntity.setModifiedBy(
        ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUserId());
    baseEntity.setVersion(baseEntity.getVersion() + 1);
    baseEntity.setModifiedOn(Instant.now());
  }

  protected abstract E prepareEntityForUpdate(E entity, RequestT request);

  protected void updateEntity(E entityToUpdate) {
    E entity = this.entityDao.update(entityToUpdate);
    if (entity == null)
      throw new DatabaseExceptionManager(
          DatabaseExceptionManager.DatabaseError.FAILED_TO_UPDATE_ENTITY);
  }

  protected abstract ResponseT prepareResponse(E entityToUpdate);
}
