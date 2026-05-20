package com.inviq.dao.entity;

import com.inviq.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

/**
 * @author ripesh created on 05/05/2026
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "level_subjects")
@Getter
@Setter
@NoArgsConstructor
public class LevelSubjectsEntity extends BaseEntity<LevelSubjectsEntity> {
  @ManyToOne(fetch = FetchType.LAZY)
  private LevelEntity level;

  @ManyToOne(fetch = FetchType.LAZY)
  private SubjectEntity subject;

  private BigDecimal price;
}
