package com.inviq.dao.entity;

import com.inviq.core.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author ripesh created on 05/05/2026
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "levels")
@Getter
@Setter
@NoArgsConstructor
public class LevelEntity extends BaseEntity<LevelEntity> {
  private String level;

  public LevelEntity(LevelEntity other) {
    this.level = other.level;
  }

  @Override
  public LevelEntity deepCopy(LevelEntity other) {
    other = super.deepCopy(other);
    return new LevelEntity(other);
  }
}
