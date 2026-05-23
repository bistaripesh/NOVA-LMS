package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ripesh created on 15/04/2026
 */
@RestController
@RequestMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
public class PingController {

  @GetMapping
  public ResponseEntity<RestResponseBody> ping() {
    return RestResponse.ok("Ping Successful");
  }
}
