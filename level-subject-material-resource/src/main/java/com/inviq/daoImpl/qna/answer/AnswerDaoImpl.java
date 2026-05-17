package com.inviq.daoImpl.qna.answer;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.entity.PageableUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.qna.answer.AnswerDao;
import com.inviq.dao.qna.answer.AnswerRepository;
import com.inviq.dao.qna.answer.converter.AnswerEntityConverter;
import com.inviq.dao.qna.answer.entity.AnswerEntity;
import com.inviq.dao.qna.answer.filter.AnswerFilter;
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
public class AnswerDaoImpl implements AnswerDao {

  private final AnswerRepository answerRepository;

  @Autowired
  public AnswerDaoImpl(AnswerRepository answerRepository) {
    this.answerRepository = answerRepository;
  }

  @Override
  public AnswerEntity insert(AnswerEntity entity) {
    return this.answerRepository.save(entity);
  }

  @Override
  public AnswerEntity update(AnswerEntity entity) {
    return this.answerRepository.save(entity);
  }

  @Override
  public int delete(AnswerEntity entity) {
    try {
      this.answerRepository.delete(entity);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<AnswerEntity> findAll(AnswerFilter filter) {
    return this.answerRepository.findAll(this.getAnswerSpecification(filter));
  }

  @Override
  public long count(AnswerFilter filter) {
    return 0;
  }

  @Override
  public Pageable findWithPagination(AnswerFilter filter) {
    PageRequest pageRequest =
        PageRequest.of(
            PageableUtil.getPageNumberForPagination(filter.getPageNumber()), filter.getPageSize());

    Page<AnswerEntity> page =
        this.answerRepository.findAll(this.getAnswerSpecification(filter), pageRequest);

    List<Response> responses = new AnswerEntityConverter().getResponses(page.getContent());

    return new Pageable(
        filter.getPageNumber(), page.getSize(), (int) page.getTotalElements(), responses);
  }

  @Override
  public Optional<AnswerEntity> findOne(AnswerFilter filter) {
    Specification<AnswerEntity> answerFindOneSpec =
        (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();
          if (StringUtil.isNotBlankOrNull(filter.getId())) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
          }
          if(filter.getStep()!=null){
            predicates.add(cb.equal(root.get("step"), filter.getStep()));
          }
          return cb.and(predicates.toArray(new Predicate[0]));
        };
    return this.answerRepository.findOne(answerFindOneSpec);
  }

  @Override
  public Optional<AnswerEntity> findById(String id) {
    return this.answerRepository.findById(id);
  }

  private Specification<AnswerEntity> getAnswerSpecification(AnswerFilter filter) {
    return ((root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtil.isNotBlankOrNull(filter.getQuestionId())) {
        predicates.add(cb.equal(root.get("question").get("id"), filter.getQuestionId()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    });
  }
}
