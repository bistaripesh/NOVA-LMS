package com.inviq.serviceImpl.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.exception.DynamicAppException;
import com.inviq.dao.UserDao;
import com.inviq.dao.filter.UserFilter;
import com.inviq.dto.tenset.request.credential.ChangeCredentialRequest;
import com.inviq.dto.tenset.request.user.UserAddRequest;
import com.inviq.dto.tenset.request.user.UserDeleteRequest;
import com.inviq.dto.tenset.request.user.UserUpdateRequest;
import com.inviq.dto.tenset.response.user.UserAddResponse;
import com.inviq.dto.tenset.response.user.UserSearchResponse;
import com.inviq.service.UserCrudService;
import com.inviq.serviceImpl.user.usecase.UserAddUseCase;
import com.inviq.serviceImpl.user.usecase.UserCredentialUpdateUseCase;
import com.inviq.serviceImpl.user.usecase.UserDeleteUseCase;
import com.inviq.serviceImpl.user.usecase.UserUpdateUseCase;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 15/04/2026
 */
@Service
public class UserServiceImpl implements UserCrudService {

  private final UserDao userDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  @Autowired
  public UserServiceImpl(UserDao userDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.userDao = userDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Transactional
  @Override
  public Response create(UserAddRequest request) {
    return new UserAddUseCase(this.userDao, this.auditDao, this.objectMapper).execute(request);
  }

  @Transactional
  @Override
  public Response update(UserUpdateRequest request) {
    return new UserUpdateUseCase(this.userDao, this.auditDao, this.objectMapper).execute(request);
  }

  @Transactional
  @Override
  public Response delete(UserDeleteRequest request) {
    return new UserDeleteUseCase(this.userDao, this.auditDao, this.objectMapper).execute(request);
  }

  @Override
  public Optional<Response> findOne(UserFilter request) {
    return this.userDao
        .findOne(request)
        .map(
            entity -> {
              UserSearchResponse dto = new UserSearchResponse();
              dto.setId(entity.getId());
              dto.setFirstName(entity.getFirstName());
              dto.setMiddleName(entity.getMiddleName());
              dto.setLastName(entity.getLastName());
              dto.setEmail(entity.getEmail());
              return dto;
            });
  }

  @Override
  public List<Response> findAll(UserFilter request) {
    return List.of();
  }

  @Override
  public Pageable findWithPagination(UserFilter filter) {
    return null;
  }

  @Transactional
  @Override
  public void changePassword(ChangeCredentialRequest request) {
    new UserCredentialUpdateUseCase(this.userDao, this.auditDao, this.objectMapper)
        .execute(request);
  }
}
