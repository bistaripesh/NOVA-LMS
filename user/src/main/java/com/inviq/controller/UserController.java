package com.inviq.controller;

import com.inviq.core.context.UserPrincipal;
import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.filter.UserFilter;
import com.inviq.dto.tenset.request.credential.ChangeCredentialRequest;
import com.inviq.dto.tenset.request.user.UserDeleteRequest;
import com.inviq.dto.tenset.request.user.UserUpdateRequest;
import com.inviq.service.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 15/04/2026
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

  private final UserCrudService userCrudService;

  @Autowired
  public UserController(UserCrudService userCrudService) {
    this.userCrudService = userCrudService;
  }

  @PutMapping
  public ResponseEntity<RestResponseBody> updateUser(
      @RequestBody UserUpdateRequest userUpdateRequest) {

    return RestResponse.ok(this.userCrudService.update(userUpdateRequest));
  }

  @PutMapping("/change-password")
  public ResponseEntity<?> changePassword(
      @RequestBody ChangeCredentialRequest changeCredentialRequest) {
    this.userCrudService.changePassword(changeCredentialRequest);
    return ResponseEntity.status(200).build();
  }

  @DeleteMapping
  public ResponseEntity<RestResponseBody> deleteUser() {
    return RestResponse.ok(this.userCrudService.delete(new UserDeleteRequest()));
  }

  @GetMapping
  public ResponseEntity<RestResponseBody> getUserInfo() {
    return RestResponse.ok(
        this.userCrudService.findOne(
            new UserFilter() {
              {
                setId(
                    (((UserPrincipal)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                        .getUserId()));
              }
            }));
  }
}
