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
@Table(name = "subjects")
@Getter
@Setter
@NoArgsConstructor
public class SubjectEntity extends BaseEntity<SubjectEntity> {
  private String subject;
}
