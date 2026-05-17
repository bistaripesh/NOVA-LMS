package com.inviq.dao.qna.question;

import com.inviq.dao.qna.question.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 17/05/2026
 */
@Repository
public interface QuestionRepository
    extends JpaRepository<QuestionEntity, String>, JpaSpecificationExecutor<QuestionEntity> {}
