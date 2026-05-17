package com.inviq.serviceImpl.qna.answer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.util.HtmlImageProcessor;
import com.inviq.dao.qna.answer.AnswerDao;
import com.inviq.dao.qna.answer.converter.AnswerEntityConverter;
import com.inviq.dao.qna.answer.filter.AnswerFilter;
import com.inviq.dto.qna.answer.request.AnswerAddRequest;
import com.inviq.dto.qna.answer.request.AnswerDeleteRequest;
import com.inviq.dto.qna.answer.request.AnswerUpdateRequest;
import com.inviq.dto.qna.answer.response.AnswerDetailedSearchResponse;
import com.inviq.dto.qna.answer.response.AnswerSearchResponse;
import com.inviq.service.qna.answer.AnswerCrudService;
import com.inviq.serviceImpl.qna.answer.usecase.AnswerAddUseCase;
import com.inviq.serviceImpl.qna.answer.usecase.AnswerDeleteUseCase;
import com.inviq.serviceImpl.qna.answer.usecase.AnswerUpdateUseCase;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 17/05/2026
 */
@Service
public class AnswerService implements AnswerCrudService {

  private final AnswerDao answerDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  @Autowired
  public AnswerService(AnswerDao answerDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.answerDao = answerDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Override
  public Response create(AnswerAddRequest request) {
    return new AnswerAddUseCase(this.answerDao, this.auditDao, this.objectMapper).execute(request);
  }

  @Override
  public Response update(AnswerUpdateRequest request) {
    return new AnswerUpdateUseCase(this.answerDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Response delete(AnswerDeleteRequest request) {
    return new AnswerDeleteUseCase(this.answerDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Optional<Response> findOne(AnswerFilter request) {
    return this.answerDao
        .findOne(request)
        .map(entity -> new AnswerEntityConverter().getResponse(entity));
  }

  @Override
  public List<Response> findAll(AnswerFilter request) {
    return this.answerDao.findAll(request).stream()
        .map(entity -> new AnswerEntityConverter().getResponse(entity))
        .collect(Collectors.toList());
  }

  @Override
  public Pageable findWithPagination(AnswerFilter answerFilter) {
    return this.answerDao.findWithPagination(answerFilter);
  }

  @Override
  public List<AnswerDetailedSearchResponse> getAnswersGroupedByQuestion(AnswerFilter filter) {
    Map<String, AnswerDetailedSearchResponse> map = new LinkedHashMap<>();

    this.answerDao
        .findAll(filter)
        .forEach(
            entity -> {
              String questionId = entity.getQuestion().getId();

              AnswerDetailedSearchResponse response =
                  map.computeIfAbsent(
                      questionId,
                      id -> {
                        AnswerDetailedSearchResponse r = new AnswerDetailedSearchResponse();
                        r.setQuestionId(entity.getQuestion().getId());
                        r.setQuestion(HtmlImageProcessor.appendBaseUrlToImages(entity.getQuestion().getQuestion()));
                        return r;
                      });

              AnswerSearchResponse answerResponse = new AnswerSearchResponse();
              answerResponse.setId(entity.getId());
              answerResponse.setStep(entity.getStep());
              answerResponse.setAnswer(HtmlImageProcessor.appendBaseUrlToImages(entity.getAnswer()));

              response.getAnswers().add(answerResponse);
            });

    List<AnswerDetailedSearchResponse> responses = new ArrayList<>(map.values());

    responses.forEach(
        response ->
            response.getAnswers().sort(Comparator.comparing(AnswerSearchResponse::getStep)));

    return responses;
  }
}
