package com.inviq.dao;

import com.inviq.dao.entity.AffiliateReferralEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 13/05/2026
 */
@Repository
public interface AffiliateReferralRepository
    extends JpaRepository<AffiliateReferralEntity, String>,
        JpaSpecificationExecutor<AffiliateReferralEntity> {}
