package com.inviq.dao.qna.question.entity;

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
 * @author ripesh created on 17/05/2026
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
public class QuestionEntity extends BaseEntity<QuestionEntity> {
  private String question;
  private Integer year;
  private Boolean isDraft;

  @ManyToOne(fetch = LAZY)
  private LevelSubjectMaterialEntity levelSubjectMaterial;

}
