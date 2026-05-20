package com.inviq.daoImpl;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.entity.PageableUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.LevelSubjectsDao;
import com.inviq.dao.LevelSubjectsRepository;
import com.inviq.dao.converter.LevelSubjectsEntityConverter;
import com.inviq.dao.entity.LevelSubjectsEntity;
import com.inviq.dao.filter.LevelSubjectsFilter;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 05/05/2026
 */
@Service
public class LevelSubjectsDaoImpl implements LevelSubjectsDao {

  private final LevelSubjectsRepository levelSubjectsRepository;

  @Autowired
  public LevelSubjectsDaoImpl(LevelSubjectsRepository levelSubjectsRepository) {
    this.levelSubjectsRepository = levelSubjectsRepository;
  }

  @Override
  public LevelSubjectsEntity insert(LevelSubjectsEntity entity) {
    return this.levelSubjectsRepository.save(entity);
  }

  @Override
  public LevelSubjectsEntity update(LevelSubjectsEntity entity) {
    return this.levelSubjectsRepository.save(entity);
  }

  @Override
  public int delete(LevelSubjectsEntity entity) {
    try {
      this.levelSubjectsRepository.delete(entity);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<LevelSubjectsEntity> findAll(LevelSubjectsFilter filter) {
    return this.levelSubjectsRepository.findAll(this.getLevelSubjectsSpecification(filter));
  }

  @Override
  public long count(LevelSubjectsFilter filter) {
    return 0;
  }

  @Override
  public Pageable findWithPagination(LevelSubjectsFilter filter) {
    PageRequest pageRequest =
        PageRequest.of(
            PageableUtil.getPageNumberForPagination(filter.getPageNumber()), filter.getPageSize());

    Page<LevelSubjectsEntity> page =
        this.levelSubjectsRepository.findAll(
            this.getLevelSubjectsSpecification(filter), pageRequest);

    List<Response> responses = new LevelSubjectsEntityConverter().getResponses(page.getContent());

    return new Pageable(
        filter.getPageNumber(), page.getSize(), (int) page.getTotalElements(), responses);
  }

  @Override
  public Optional<LevelSubjectsEntity> findOne(LevelSubjectsFilter filter) {
    Specification<LevelSubjectsEntity> levelSubjectsFindOneSpec =
        (root, query, criteriaBuilder) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (StringUtil.isNotBlankOrNull(filter.getId())) {
            predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
          }
          if (StringUtil.isNotBlankOrNull(filter.getLevelId())) {
            predicates.add(criteriaBuilder.equal(root.get("level").get("id"), filter.getLevelId()));
          }

          if(filter.getPrice()!=null){
            predicates.add(criteriaBuilder.equal(root.get("price"), filter.getPrice()));
          }

          if (StringUtil.isNotBlankOrNull(filter.getSubjectId())) {
            predicates.add(
                criteriaBuilder.equal(root.get("subject").get("id"), filter.getSubjectId()));
          }

          if (StringUtil.isNotBlankOrNull(filter.getSubjectName())) {
            predicates.add(
                criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("subject").get("subject")),
                    filter.getSubjectName().toLowerCase()));
          }
          return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    return this.levelSubjectsRepository.findOne(levelSubjectsFindOneSpec);
  }

  @Override
  public Optional<LevelSubjectsEntity> findById(String id) {
    return this.levelSubjectsRepository.findById(id);
  }

  private Specification<LevelSubjectsEntity> getLevelSubjectsSpecification(
      LevelSubjectsFilter filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (StringUtil.isNotBlankOrNull(filter.getLevelId())) {
        predicates.add(cb.equal(root.get("level").get("id"), filter.getLevelId()));
      }
      if (StringUtil.isNotBlankOrNull(filter.getSubjectId())) {
        predicates.add(cb.equal(root.get("subject").get("id"), filter.getSubjectId()));
      }
      if (StringUtil.isNotBlankOrNull(filter.getSearchParameter())) {
        predicates.add(
            cb.like(
                cb.lower(root.get("subject").get("subject")),
                filter.getSearchParameter().toLowerCase()));
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
