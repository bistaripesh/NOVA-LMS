package com.inviq.audit.dao;

import com.inviq.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 17/04/2026
 */
@Repository
public interface AuditRepository extends JpaRepository<AuditEntity, String> {}
