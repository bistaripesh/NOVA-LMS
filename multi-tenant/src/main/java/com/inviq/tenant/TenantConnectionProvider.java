package com.inviq.tenant;

import com.inviq.core.util.SystemUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

/**
 * @author ripesh created on 27/04/2026
 */
@Component
public class TenantConnectionProvider
    implements MultiTenantConnectionProvider<String>, HibernatePropertiesCustomizer {

  private final DataSource dataSource;

  @Autowired
  public TenantConnectionProvider(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Connection getAnyConnection() throws SQLException {
    String PUBLIC_TENANT = SystemUtil.getEnv(TenantEnvConstant.DEFAULT_TENANT);
    return getConnection(PUBLIC_TENANT);
  }

  @Override
  public void releaseAnyConnection(Connection connection) throws SQLException {
    getAnyConnection().close();
  }

  @Override
  public Connection getConnection(String tenantIdentifier) throws SQLException {
    Connection conn = dataSource.getConnection();
    conn.setSchema(tenantIdentifier);
    return conn;
  }

  @Override
  public void releaseConnection(String tenantIdentifier, Connection connection)
      throws SQLException {
    if (connection != null && !connection.isClosed()) {
      connection.close();
    }
  }

  @Override
  public boolean supportsAggressiveRelease() {
    return false;
  }

  @Override
  public boolean isUnwrappableAs(Class<?> unwrapType) {
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> unwrapType) {
    return null;
  }

  @Override
  public void customize(Map<String, Object> hibernateProperties) {
    hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
  }
}
