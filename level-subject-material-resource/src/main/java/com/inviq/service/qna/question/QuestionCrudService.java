package com.inviq.service.qna.question;

import com.inviq.core.service.CrudService;
import com.inviq.dao.qna.question.filter.QuestionFilter;
import com.inviq.dto.qna.question.request.QuestionAddRequest;
import com.inviq.dto.qna.question.request.QuestionDeleteRequest;
import com.inviq.dto.qna.question.request.QuestionUpdateRequest;

/**
 * @author ripesh created on 17/05/2026
 */
public interface QuestionCrudService extends CrudService<QuestionAddRequest, QuestionUpdateRequest, QuestionDeleteRequest, QuestionFilter> {}
