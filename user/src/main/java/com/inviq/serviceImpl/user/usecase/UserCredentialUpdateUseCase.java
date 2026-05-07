package com.inviq.serviceImpl.user.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.context.UserPrincipal;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.exception.AppException;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.dao.entity.UserEntity;
import com.inviq.dao.filter.UserFilter;
import com.inviq.dto.tenset.request.credential.ChangeCredentialRequest;
import com.inviq.dto.tenset.response.user.UserUpdateResponse;
import com.inviq.exception.AuthExceptionManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author ripesh created on 24/04/2026
 */
public class UserCredentialUpdateUseCase
    extends AbstractUpdateUseCase<
        UserEntity, ChangeCredentialRequest, UserUpdateResponse, UserFilter> {

  public UserCredentialUpdateUseCase(
      BaseDao<UserEntity, UserFilter> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(ChangeCredentialRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      ChangeCredentialRequest request, UserEntity entityInRecord) {
    if (!BCrypt.checkpw(request.getOldPassword(), entityInRecord.getPassword()))
      throw new AppException(AuthExceptionManager.AuthError.WRONG_CREDENTIALS);
  }

  @Override
  protected void preProcessRequest(ChangeCredentialRequest request) {
    request.setId(
        ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUserId());
  }

  @Override
  protected Module getModule() {
    return Module.USER;
  }

  @Override
  protected UserFilter prepareFilterToFindDuplicateRecord(ChangeCredentialRequest request) {
    UserFilter userFilter = new UserFilter();
    userFilter.setId(request.getId());
    return userFilter;
  }

  @Override
  protected UserEntity prepareEntityForUpdate(UserEntity entity, ChangeCredentialRequest request) {
    entity.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
    return entity;
  }

  @Override
  protected UserUpdateResponse prepareResponse(UserEntity entityToUpdate) {
    return new UserUpdateResponse();
  }
}
