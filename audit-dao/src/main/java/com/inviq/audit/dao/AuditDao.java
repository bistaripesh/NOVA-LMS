package com.inviq.audit.dao;

import com.inviq.AuditEntity;
import java.util.List;

/**
 * @author ripesh created on 17/04/2026
 */
public interface AuditDao {
  AuditEntity insert(AuditEntity auditEntity);

  List<AuditEntity> bulkInsert(List<AuditEntity> auditEntities);
}
