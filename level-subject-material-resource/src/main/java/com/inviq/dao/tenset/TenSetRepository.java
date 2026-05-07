package com.inviq.dao.tenset;

import com.inviq.dao.tenset.entity.TenSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 06/05/2026
 */
@Repository
public interface TenSetRepository
    extends JpaRepository<TenSetEntity, String>, JpaSpecificationExecutor<TenSetEntity> {}
