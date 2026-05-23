package com.inviq.security.util;

import com.inviq.security.constant.PublicEndpoints;
import java.util.Arrays;
import java.util.List;
import org.springframework.util.AntPathMatcher;

/**
 * @author ripesh created on 23/04/2026
 */
public class SecurityInterceptorUtil {

  public static boolean isPublicEndpoint(String endpoint) {
    AntPathMatcher matcher = new AntPathMatcher();

    List<String> patterns =
        Arrays.stream(PublicEndpoints.values()).map(PublicEndpoints::getEndpoint).toList();

    return patterns.stream().anyMatch(pattern -> matcher.match(pattern, endpoint));
  }
}
