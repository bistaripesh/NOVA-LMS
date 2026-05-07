package com.inviq.daoImpl;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.entity.PageableUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.SubjectDao;
import com.inviq.dao.SubjectRepository;
import com.inviq.dao.entity.SubjectEntity;
import com.inviq.dao.filter.SubjectFilter;
import com.inviq.dto.tenset.response.SubjectSearchResponse;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 05/05/2026
 */
@Repository
public class SubjectDaoImpl implements SubjectDao {

  private final SubjectRepository subjectRepository;

  @Autowired
  public SubjectDaoImpl(SubjectRepository subjectRepository) {
    this.subjectRepository = subjectRepository;
  }

  @Override
  public SubjectEntity insert(SubjectEntity entity) {
    return this.subjectRepository.save(entity);
  }

  @Override
  public SubjectEntity update(SubjectEntity entity) {
    return this.subjectRepository.save(entity);
  }

  @Override
  public int delete(SubjectEntity entity) {
    try {
      this.subjectRepository.delete(entity);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<SubjectEntity> findAll(SubjectFilter filter) {
    return this.subjectRepository.findAll(this.getSubjectSpecification(filter));
  }

  @Override
  public long count(SubjectFilter filter) {
    return 0;
  }

  @Override
  public Pageable findWithPagination(SubjectFilter filter) {
    PageRequest pageRequest =
        PageRequest.of(
            PageableUtil.getPageNumberForPagination(filter.getPageNumber()), filter.getPageSize());

    Page<SubjectEntity> page =
        this.subjectRepository.findAll(this.getSubjectSpecification(filter), pageRequest);

    List<Response> responses =
        page.getContent().stream()
            .map(
                entity -> {
                  SubjectSearchResponse dto = new SubjectSearchResponse();
                  dto.setId(entity.getId());
                  dto.setSubjectName(entity.getSubject());
                  return dto;
                })
            .collect(Collectors.toList());

    return new Pageable(
        filter.getPageNumber(), page.getSize(), (int) page.getTotalElements(), responses);
  }

  @Override
  public Optional<SubjectEntity> findOne(SubjectFilter filter) {
    Specification<SubjectEntity> subjectEntityFindOneSpec =
        (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (StringUtil.isNotBlankOrNull(filter.getId())) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
          }

          if (StringUtil.isNotBlankOrNull(filter.getSubjectName())) {
            predicates.add(cb.equal(root.get("subject"), filter.getSubjectName()));
          }

          return cb.and(predicates.toArray(new Predicate[0]));
        };
    return this.subjectRepository.findOne(subjectEntityFindOneSpec);
  }

  @Override
  public Optional<SubjectEntity> findById(String id) {
    return this.subjectRepository.findById(id);
  }

  private Specification<SubjectEntity> getSubjectSpecification(SubjectFilter filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new java.util.ArrayList<>();

      if (StringUtil.isNotBlankOrNull(filter.getSubjectName())) {
        predicates.add(
            cb.equal(cb.lower(root.get("subject")), filter.getSubjectName().toLowerCase()));
      }

      if (StringUtil.isNotBlankOrNull(filter.getSearchParameter())) {
        predicates.add(
            cb.like(cb.lower(root.get("subject")), filter.getSearchParameter().toLowerCase()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
