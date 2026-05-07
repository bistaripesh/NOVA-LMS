package com.inviq.dao;

import com.inviq.dao.entity.LevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 05/05/2026
 */
@Repository
public interface LevelRepository
    extends JpaRepository<LevelEntity, String>, JpaSpecificationExecutor<LevelEntity> {}
