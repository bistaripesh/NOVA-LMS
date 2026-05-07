package com.inviq.serviceImpl.user.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.dao.UserDao;
import com.inviq.dao.entity.UserEntity;
import com.inviq.dao.filter.UserFilter;
import com.inviq.dto.tenset.request.user.UserAddRequest;
import com.inviq.dto.tenset.response.user.UserAddResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author ripesh created on 15/04/2026
 */
public class UserAddUseCase
    extends AbstractAddUseCase<UserEntity, UserAddRequest, UserAddResponse, UserFilter> {

  public UserAddUseCase(UserDao userDao, AuditDao auditDao, ObjectMapper objectMapper) {
    super(userDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(UserAddRequest request) {}

  @Override
  protected void preValidateRequest(UserAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.USER;
  }

  @Override
  protected UserFilter prepareFilterToFindDuplicateRecord(UserAddRequest request) {
    UserFilter userFilter = new UserFilter();
    userFilter.setEmail(request.getEmail());
    return userFilter;
  }

  @Override
  protected UserEntity prepareEntity(UserAddRequest request) {
    UserEntity user = new UserEntity();
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());
    user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
    return user;
  }

  @Override
  public UserAddResponse prepareResponse(UserEntity addedEntity) {
    UserAddResponse response = new UserAddResponse();
    response.setId(addedEntity.getId());
    return response;
  }
}
