package com.inviq.core.service.usecase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.AuditEntity;
import com.inviq.AuditType;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.context.UserPrincipal;
import com.inviq.core.dto.filter.Filter;
import com.inviq.core.dto.request.crud.add.BaseAddRequest;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Entity;
import com.inviq.core.entity.Module;
import com.inviq.core.entity.exception.DatabaseExceptionManager;
import com.inviq.core.exception.AppException;
import com.inviq.core.exception.manager.AuditExceptionManager;
import com.inviq.core.util.StringUtil;
import java.time.Instant;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author ripesh created on 09/04/2025
 */
public abstract class AbstractAddUseCase<
    E extends Entity<E>,
    RequestT extends BaseAddRequest,
    ResponseT extends Response,
    F extends Filter> {

  protected final BaseDao<E, F> entityDao;
  protected final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  public AbstractAddUseCase(BaseDao<E, F> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.entityDao = entityDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  protected abstract void preProcessRequest(RequestT request);

  protected abstract void preValidateRequest(RequestT request);

  public ResponseT execute(RequestT request) {
    this.preProcessRequest(request);
    this.preValidateRequest(request);
    request.validateRequest();
    F filter = this.prepareFilterToFindDuplicateRecord(request);
    this.checkDuplicateRecord(filter);
    E entityToAdd = this.prepareEntity(request);
    this.prepareBaseEntity(entityToAdd);
    AuditEntity auditEntity = this.auditDao.insert(this.prepareAuditEntity(entityToAdd));
    if (auditEntity == null)
      throw new AppException(AuditExceptionManager.AuditError.AUDIT_DATA_INSERTION_FAILED);

    this.insertEntity(entityToAdd);
    return this.prepareResponse(entityToAdd);
  }

  protected abstract Module getModule();

  protected abstract F prepareFilterToFindDuplicateRecord(RequestT request);

  protected void checkDuplicateRecord(F filter) {
    if (this.entityDao.findOne(filter).isPresent())
      throw new AppException(DatabaseExceptionManager.DatabaseError.DUPLICATE_RECORD_FOUND);
  }

  protected abstract E prepareEntity(RequestT request);

  protected void prepareBaseEntity(E entityToAdd) {
    entityToAdd.setId(StringUtil.getUUID());
    entityToAdd.setVersion(1);
    entityToAdd.setCreatedOn(Instant.now());
    entityToAdd.setModifiedOn(Instant.now());
    entityToAdd.setCreatedBy(
        ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUserId());
    entityToAdd.setModifiedBy(
        ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUserId());
  }

  protected void insertEntity(E entityToAdd) {
    Entity<E> entity = this.entityDao.insert(entityToAdd);
    if (entity == null)
      throw new AppException(DatabaseExceptionManager.DatabaseError.FAILED_TO_INSERT_ENTITY);
  }

  protected AuditEntity prepareAuditEntity(E entityToAdd) {
    AuditEntity auditEntity = new AuditEntity();
    auditEntity.setId(StringUtil.getUUID());
    auditEntity.setEntity(this.getModule().getCode());
    auditEntity.setNewData(this.objectMapper.convertValue(entityToAdd, new TypeReference<>() {}));
    auditEntity.setOldData(null);
    auditEntity.setEventType(AuditType.INSERT);
    auditEntity.setEventDate(Instant.now());
    return auditEntity;
  }

  protected abstract ResponseT prepareResponse(E addedEntity);
}
