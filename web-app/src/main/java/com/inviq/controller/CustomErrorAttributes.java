package com.inviq.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

/**
 * @author ripesh created on 28/04/2026
 */
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {
  @Override
  public Map<String, Object> getErrorAttributes(
      WebRequest webRequest, ErrorAttributeOptions options) {
    Map<String, Object> errorAttributes = new HashMap<>();
    Throwable error = super.getError(webRequest);
    errorAttributes.put("success", false);
    errorAttributes.put("message", error.getMessage());
    return errorAttributes;
  }
}
