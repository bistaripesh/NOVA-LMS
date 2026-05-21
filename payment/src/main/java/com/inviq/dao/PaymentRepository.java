package com.inviq.dao;

import com.inviq.dao.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 21/05/2026
 */
@Repository
public interface PaymentRepository
    extends JpaRepository<PaymentEntity, String>, JpaSpecificationExecutor<PaymentEntity> {}
