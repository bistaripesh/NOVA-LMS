package com.inviq.dao.entity;

import com.inviq.core.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author ripesh created on 06/05/2026
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "level_subject_materials")
@Getter
@Setter
@NoArgsConstructor
public class LevelSubjectMaterialEntity extends BaseEntity<LevelSubjectMaterialEntity> {

  @ManyToOne(fetch = FetchType.LAZY)
  private MaterialEntity material;

  @ManyToOne(fetch = FetchType.LAZY)
  private LevelSubjectsEntity levelSubject;
}
