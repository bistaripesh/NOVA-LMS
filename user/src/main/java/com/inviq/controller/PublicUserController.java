package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dto.tenset.request.user.UserAddRequest;
import com.inviq.service.UserCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ripesh created on 23/04/2026
 */
@RestController
@RequestMapping("/public/user")
public class PublicUserController {

  private final UserCrudService userCrudService;

  public PublicUserController(UserCrudService userCrudService) {
    this.userCrudService = userCrudService;
  }

  @PostMapping
  public ResponseEntity<RestResponseBody> createUser(@RequestBody UserAddRequest userAddRequest) {
    return RestResponse.ok(this.userCrudService.create(userAddRequest));
  }
}
