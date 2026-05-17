package com.inviq.dao.qna.answer.entity;

import com.inviq.core.entity.BaseEntity;
import com.inviq.dao.qna.question.entity.QuestionEntity;
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
 * @author ripesh created on 17/05/2026
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
public class AnswerEntity extends BaseEntity<AnswerEntity> {

  private String answer;

  @ManyToOne(fetch = FetchType.LAZY)
  private QuestionEntity question;
}
