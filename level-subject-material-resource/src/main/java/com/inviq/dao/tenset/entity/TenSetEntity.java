package com.inviq.dao.tenset.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.inviq.core.entity.BaseEntity;
import com.inviq.dao.entity.LevelSubjectMaterialEntity;
import jakarta.persistence.Entity;
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
@Table(name = "ten_sets")
@Getter
@Setter
@NoArgsConstructor
public class TenSetEntity extends BaseEntity<TenSetEntity> {
  @ManyToOne(fetch = LAZY)
  private LevelSubjectMaterialEntity levelSubjectMaterial;

  private String title;
  private String pdfUrl;
  private Integer year;
}
