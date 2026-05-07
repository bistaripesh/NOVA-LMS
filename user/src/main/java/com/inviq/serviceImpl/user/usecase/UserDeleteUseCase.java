package com.inviq.serviceImpl.user.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.context.UserPrincipal;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractDeleteUseCase;
import com.inviq.dao.entity.UserEntity;
import com.inviq.dao.filter.UserFilter;
import com.inviq.dto.tenset.request.user.UserDeleteRequest;
import com.inviq.dto.tenset.response.user.UserDeleteResponse;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author ripesh created on 17/04/2026
 */
public class UserDeleteUseCase
    extends AbstractDeleteUseCase<UserEntity, UserDeleteRequest, UserDeleteResponse, UserFilter> {

  public UserDeleteUseCase(
      BaseDao<UserEntity, UserFilter> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(UserDeleteRequest request) {}

  @Override
  protected void validateRequestWithEntity(UserDeleteRequest request, UserEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(UserDeleteRequest request) {
    request.setId(
        ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUserId());
  }

  @Override
  protected Module getModule() {
    return Module.USER;
  }

  @Override
  protected UserFilter prepareFilter(UserDeleteRequest request) {
    UserFilter userFilter = new UserFilter();
    userFilter.setId(request.getId());
    return userFilter;
  }

  @Override
  protected UserDeleteResponse prepareResponse(UserEntity entityToUpdate) {
    UserDeleteResponse response = new UserDeleteResponse();
    response.setId(entityToUpdate.getId());
    return response;
  }
}
