package com.inviq.service;

import com.inviq.core.service.CrudService;
import com.inviq.dao.filter.UserFilter;
import com.inviq.dto.tenset.request.credential.ChangeCredentialRequest;
import com.inviq.dto.tenset.request.user.UserAddRequest;
import com.inviq.dto.tenset.request.user.UserDeleteRequest;
import com.inviq.dto.tenset.request.user.UserUpdateRequest;

/**
 * @author ripesh created on 15/04/2026
 */
public interface UserCrudService
    extends CrudService<UserAddRequest, UserUpdateRequest, UserDeleteRequest, UserFilter> {
  void changePassword(ChangeCredentialRequest request);
}
