package com.inviq.dao;

import com.inviq.dao.entity.LevelSubjectMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 06/05/2026
 */
@Repository
public interface LevelSubjectMaterialRepository
    extends JpaRepository<LevelSubjectMaterialEntity, String>,
        JpaSpecificationExecutor<LevelSubjectMaterialEntity> {}
