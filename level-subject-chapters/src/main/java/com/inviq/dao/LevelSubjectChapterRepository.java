package com.inviq.dao;

import com.inviq.dao.affiliate.entity.LevelSubjectChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 07/05/2026
 */
@Repository
public interface LevelSubjectChapterRepository
    extends JpaRepository<LevelSubjectChapterEntity, String>,
        JpaSpecificationExecutor<LevelSubjectChapterEntity> {}
