package com.inviq.dao.affiliateType;

import com.inviq.dao.affiliateType.entity.AffiliateTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 12/05/2026
 */
@Repository
public interface AffiliateTypeRepository
    extends JpaRepository<AffiliateTypeEntity, String>,
        JpaSpecificationExecutor<AffiliateTypeEntity> {}
