package com.inviq.dao.qna.answer;

import com.inviq.dao.qna.answer.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 17/05/2026
 */
@Repository
public interface AnswerRepository
    extends JpaRepository<AnswerEntity, String>, JpaSpecificationExecutor<AnswerEntity> {}
