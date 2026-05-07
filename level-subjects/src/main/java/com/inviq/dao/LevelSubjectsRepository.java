package com.inviq.dao;

import com.inviq.dao.entity.LevelSubjectsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 05/05/2026
 */
@Repository
public interface LevelSubjectsRepository
    extends JpaRepository<LevelSubjectsEntity, String>,
        JpaSpecificationExecutor<LevelSubjectsEntity> {}
