package com.inviq.dao.affiliate.entity;

import com.inviq.core.entity.BaseEntity;
import com.inviq.dao.entity.LevelSubjectsEntity;
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
 * @author ripesh created on 07/05/2026
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "level_subject_chapters")
@Getter
@Setter
@NoArgsConstructor
public class LevelSubjectChapterEntity extends BaseEntity<LevelSubjectChapterEntity> {
  private Integer chapterNumber;
  private String chapter;
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  private LevelSubjectsEntity levelSubject;
}
