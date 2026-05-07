package com.inviq.audit.dao.impl;

import com.inviq.AuditEntity;
import com.inviq.audit.dao.AuditDao;
import com.inviq.audit.dao.AuditRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 17/04/2026
 */
@Repository
public class AuditDaoImpl implements AuditDao {

  private final AuditRepository auditRepository;

  @Autowired
  public AuditDaoImpl(AuditRepository auditRepository) {
    this.auditRepository = auditRepository;
  }

  @Override
  public AuditEntity insert(AuditEntity auditEntity) {
    return this.auditRepository.save(auditEntity);
  }

  @Override
  public List<AuditEntity> bulkInsert(List<AuditEntity> auditEntities) {
    return this.auditRepository.saveAll(auditEntities);
  }
}
