package com.inviq.tenant;

import com.inviq.core.context.UserPrincipal;
import java.util.Map;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author ripesh created on 27/04/2026
 */
@Component
class TenantIdentifierResolver
    implements CurrentTenantIdentifierResolver<String>, HibernatePropertiesCustomizer {

  private String currentTenant = "public";

  @Override
  public String resolveCurrentTenantIdentifier() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      currentTenant = ((UserPrincipal) authentication.getPrincipal()).getSchema();
    }
    return currentTenant;
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return false;
  }

  @Override
  public void customize(Map<String, Object> hibernateProperties) {
    hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
  }
}
