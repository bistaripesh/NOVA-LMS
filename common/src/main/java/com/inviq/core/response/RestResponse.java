package com.inviq.core.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author ripesh created on 15/04/2026
 */
public class RestResponse {
  public static ResponseEntity<RestResponseBody> ok(Object data) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new RestResponseBody(true, data));
  }

  public static ResponseEntity<RestResponseBody> error(HttpStatus errorStatus, Object data) {
    return ResponseEntity.status(errorStatus)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new RestResponseBody(false, data));
  }
}
