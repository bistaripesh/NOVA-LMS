package com.inviq.daoImpl.qna.question;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.entity.PageableUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.qna.question.QuestionDao;
import com.inviq.dao.qna.question.QuestionRepository;
import com.inviq.dao.qna.question.converter.QuestionEntityConverter;
import com.inviq.dao.qna.question.entity.QuestionEntity;
import com.inviq.dao.qna.question.filter.QuestionFilter;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 17/05/2026
 */
@Repository
public class QuestionDaoImpl implements QuestionDao {

  private final QuestionRepository questionRepository;

  @Autowired
  public QuestionDaoImpl(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  @Override
  public QuestionEntity insert(QuestionEntity entity) {
    return this.questionRepository.save(entity);
  }

  @Override
  public QuestionEntity update(QuestionEntity entity) {
    return this.questionRepository.save(entity);
  }

  @Override
  public int delete(QuestionEntity entity) {
    try {
      this.questionRepository.delete(entity);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<QuestionEntity> findAll(QuestionFilter filter) {

    return this.questionRepository.findAll(this.getQuestionSpecification(filter));
  }

  @Override
  public long count(QuestionFilter filter) {

    return this.questionRepository.count(this.getQuestionSpecification(filter));
  }

  @Override
  public Pageable findWithPagination(QuestionFilter filter) {

    PageRequest pageRequest =
        PageRequest.of(
            PageableUtil.getPageNumberForPagination(filter.getPageNumber()), filter.getPageSize());

    Page<QuestionEntity> page =
        this.questionRepository.findAll(this.getQuestionSpecification(filter), pageRequest);

    List<Response> responses = new QuestionEntityConverter().getResponses(page.getContent());

    return new Pageable(
        filter.getPageNumber(), page.getSize(), (int) page.getTotalElements(), responses);
  }

  @Override
  public Optional<QuestionEntity> findOne(QuestionFilter filter) {

    Specification<QuestionEntity> questionFindOneSpec =
        (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (StringUtil.isNotBlankOrNull(filter.getId()))
            predicates.add(cb.equal(root.get("id"), filter.getId()));

          if (StringUtil.isNotBlankOrNull(filter.getYear()))
            predicates.add(cb.equal(root.get("year"), filter.getYear()));

          if (StringUtil.isNotBlankOrNull(filter.getQuestion())) {
            predicates.add(cb.equal(root.get("question"), filter.getQuestion().toLowerCase()));
          }

          if (StringUtil.isNotBlankOrNull(filter.getLevelSubjectMaterialId()))
            predicates.add(
                cb.equal(
                    root.get("levelSubjectMaterial").get("id"),
                    filter.getLevelSubjectMaterialId()));

          return cb.and(predicates.toArray(new Predicate[0]));
        };

    return this.questionRepository.findOne(questionFindOneSpec);
  }

  @Override
  public Optional<QuestionEntity> findById(String id) {
    return this.questionRepository.findById(id);
  }

  private Specification<QuestionEntity> getQuestionSpecification(QuestionFilter filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtil.isNotBlankOrNull(filter.getSearchParameter()))
        predicates.add(
            cb.like(cb.lower(root.get("question")), filter.getSearchParameter().toLowerCase()));

      if (StringUtil.isNotBlankOrNull(filter.getLevelId())) {
        predicates.add(
            cb.equal(
                root.get("levelSubjectMaterial").get("levelSubject").get("level").get("id"),
                filter.getLevelId()));
      }

      if (StringUtil.isNotBlankOrNull(filter.getSubjectId())) {
        predicates.add(
            cb.equal(
                root.get("levelSubjectMaterial").get("levelSubject").get("subject").get("id"),
                filter.getSubjectId()));
      }

      if (StringUtil.isNotBlankOrNull(filter.getYear()))
        predicates.add(cb.equal(root.get("year"), filter.getYear()));

      if (StringUtil.isNotBlankOrNull(filter.getLevelSubjectMaterialId()))
        predicates.add(
            cb.equal(
                root.get("levelSubjectMaterial").get("id"), filter.getLevelSubjectMaterialId()));

      if (filter.getIsDraft() != null) {
        predicates.add(cb.equal(root.get("isDraft"), filter.getIsDraft()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
