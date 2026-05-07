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
@Table(name = "materials")
@Getter
@Setter
@NoArgsConstructor
public class MaterialEntity extends BaseEntity<MaterialEntity> {
  private String material;
}
