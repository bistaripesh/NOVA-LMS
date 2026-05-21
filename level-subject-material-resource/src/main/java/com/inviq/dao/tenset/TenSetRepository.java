package com.inviq.dao.tenset;

import com.inviq.dao.tenset.entity.TenSetEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 06/05/2026
 */
@Repository
public interface TenSetRepository
    extends JpaRepository<TenSetEntity, String>, JpaSpecificationExecutor<TenSetEntity> {

  @Query("SELECT t.levelSubjectMaterial.levelSubject.id FROM TenSetEntity t WHERE t.id = :id")
  Optional<String> findLevelSubjectIdById(@Param("id") String id);
}
