package com.inviq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

/**
 * @author ripesh created on 23/04/2026
 */
@RestController
public class CustomErrorController implements ErrorController {

  private final CustomErrorAttributes customErrorAttributes;

  @Autowired
  public CustomErrorController(CustomErrorAttributes customErrorAttributes) {
    this.customErrorAttributes = customErrorAttributes;
  }

  @RequestMapping("/error")
  public ResponseEntity<?> handleError(WebRequest webRequest, ErrorAttributeOptions options) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(customErrorAttributes.getErrorAttributes(webRequest, options));
  }
}
