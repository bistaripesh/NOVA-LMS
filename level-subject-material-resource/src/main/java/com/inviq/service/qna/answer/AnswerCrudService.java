package com.inviq.service.qna.answer;

import com.inviq.core.service.CrudService;
import com.inviq.dao.qna.answer.filter.AnswerFilter;
import com.inviq.dto.qna.answer.request.AnswerAddRequest;
import com.inviq.dto.qna.answer.request.AnswerDeleteRequest;
import com.inviq.dto.qna.answer.request.AnswerUpdateRequest;
import com.inviq.dto.qna.answer.response.AnswerDetailedSearchResponse;
import java.util.List;

/**
 * @author ripesh created on 17/05/2026
 */
public interface AnswerCrudService
    extends CrudService<AnswerAddRequest, AnswerUpdateRequest, AnswerDeleteRequest, AnswerFilter> {

  List<AnswerDetailedSearchResponse> getAnswersGroupedByQuestion(AnswerFilter filter);
}
