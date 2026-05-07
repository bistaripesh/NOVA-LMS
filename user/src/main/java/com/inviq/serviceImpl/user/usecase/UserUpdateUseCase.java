package com.inviq.serviceImpl.user.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.dao.entity.UserEntity;
import com.inviq.dao.filter.UserFilter;
import com.inviq.dto.tenset.request.user.UserUpdateRequest;
import com.inviq.dto.tenset.response.user.UserUpdateResponse;

/**
 * @author ripesh created on 16/04/2026
 */
public class UserUpdateUseCase
    extends AbstractUpdateUseCase<UserEntity, UserUpdateRequest, UserUpdateResponse, UserFilter> {

  public UserUpdateUseCase(
      BaseDao<UserEntity, UserFilter> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(UserUpdateRequest request) {}

  @Override
  protected void validateRequestWithEntity(UserUpdateRequest request, UserEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(UserUpdateRequest request) {}

  @Override
  protected Module getModule() {
    return Module.USER;
  }

  @Override
  protected UserFilter prepareFilterToFindDuplicateRecord(UserUpdateRequest request) {
    UserFilter userFilter = new UserFilter();
    userFilter.setEmail(request.getEmail());
    return userFilter;
  }

  @Override
  protected UserEntity prepareEntityForUpdate(UserEntity userEntity, UserUpdateRequest request) {
    userEntity.setFirstName(request.getFirstName());
    userEntity.setMiddleName(request.getMiddleName());
    userEntity.setLastName(request.getLastName());
    return userEntity;
  }

  @Override
  public UserUpdateResponse prepareResponse(UserEntity entityToUpdate) {
    UserUpdateResponse response = new UserUpdateResponse();
    response.setId(entityToUpdate.getId());
    return response;
  }
}
