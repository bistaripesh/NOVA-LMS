package com.inviq.dao.affiliate;

import com.inviq.dao.affiliate.entity.AffiliateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ripesh created on 11/05/2026
 */
public interface AffiliateRepository
    extends JpaRepository<AffiliateEntity, String>, JpaSpecificationExecutor<AffiliateEntity> {}
