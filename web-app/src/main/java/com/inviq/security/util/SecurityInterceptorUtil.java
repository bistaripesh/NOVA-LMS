package com.inviq.security.util;

import com.inviq.security.constant.PublicEndpoints;
import java.util.Arrays;
import java.util.List;

/**
 * @author ripesh created on 23/04/2026
 */
public class SecurityInterceptorUtil {

  public static boolean isPublicEndpoint(String endpoint) {
    List<String> publicEndpoints =
        Arrays.stream(PublicEndpoints.values()).map(PublicEndpoints::getEndpoint).toList();
    return publicEndpoints.contains(endpoint);
  }
}
