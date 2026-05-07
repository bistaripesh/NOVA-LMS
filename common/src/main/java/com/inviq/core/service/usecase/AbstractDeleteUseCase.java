package com.inviq.core.service.usecase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.AuditEntity;
import com.inviq.AuditType;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.filter.Filter;
import com.inviq.core.dto.request.crud.delete.BaseDeleteRequest;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Entity;
import com.inviq.core.entity.Module;
import com.inviq.core.entity.exception.DatabaseExceptionManager;
import com.inviq.core.exception.AppException;
import com.inviq.core.exception.manager.AuditExceptionManager;
import com.inviq.core.util.StringUtil;
import java.time.Instant;

/**
 * @author ripesh created on 17/04/2026
 */
public abstract class AbstractDeleteUseCase<
    E extends Entity<E>,
    RequestT extends BaseDeleteRequest,
    ResponseT extends Response,
    F extends Filter> {

  private final BaseDao<E, F> entityDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  public AbstractDeleteUseCase(
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
    request.validateRequest();
    request.validateBasicInput(request);
    F filter = this.prepareFilter(request);
    E entityInRecord =
        this.entityDao
            .findOne(filter)
            .orElseThrow(
                () -> new AppException(DatabaseExceptionManager.DatabaseError.RECORD_NOT_FOUND));
    this.validateRequestWithEntity(request, entityInRecord);
    int res = this.entityDao.delete(entityInRecord);

    AuditEntity auditEntity = this.auditDao.insert(this.prepareAuditEntity(entityInRecord));
    if (auditEntity == null)
      throw new AppException(AuditExceptionManager.AuditError.AUDIT_DATA_INSERTION_FAILED);

    if (res == 0)
      throw new AppException(DatabaseExceptionManager.DatabaseError.FAILED_TO_DELETE_ENTITY);
    return this.prepareResponse(entityInRecord);
  }

  protected abstract Module getModule();

  protected abstract F prepareFilter(RequestT requestT);

  protected abstract ResponseT prepareResponse(E entityToUpdate);

  protected AuditEntity prepareAuditEntity(E entityInRecord) {
    AuditEntity auditEntity = new AuditEntity();
    auditEntity.setId(StringUtil.getUUID());

    auditEntity.setNewData(
        this.objectMapper.convertValue(entityInRecord, new TypeReference<>() {}));

    auditEntity.setEventType(AuditType.DELETE);
    auditEntity.setEventDate(Instant.now());
    auditEntity.setEntity(this.getModule().getCode());
    return auditEntity;
  }
}
