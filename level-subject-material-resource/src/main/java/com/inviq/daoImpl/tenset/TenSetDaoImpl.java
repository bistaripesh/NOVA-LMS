package com.inviq.daoImpl.tenset;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.entity.PageableUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.tenset.TenSetDao;
import com.inviq.dao.tenset.TenSetRepository;
import com.inviq.dao.tenset.converter.TenSetEntityConverter;
import com.inviq.dao.tenset.entity.TenSetEntity;
import com.inviq.dao.tenset.filter.TenSetFilter;
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
 * @author ripesh created on 06/05/2026
 */
@Repository
public class TenSetDaoImpl implements TenSetDao {

  private final TenSetRepository tenSetRepository;

  @Autowired
  public TenSetDaoImpl(TenSetRepository tenSetRepository) {
    this.tenSetRepository = tenSetRepository;
  }

  @Override
  public TenSetEntity insert(TenSetEntity entity) {
    return this.tenSetRepository.save(entity);
  }

  @Override
  public TenSetEntity update(TenSetEntity entity) {
    return this.tenSetRepository.save(entity);
  }

  @Override
  public int delete(TenSetEntity entity) {
    try {
      this.tenSetRepository.delete(entity);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<TenSetEntity> findAll(TenSetFilter filter) {
    return this.tenSetRepository.findAll(this.getTenSetSpecification(filter));
  }

  @Override
  public long count(TenSetFilter filter) {
    return 0;
  }

  @Override
  public Pageable findWithPagination(TenSetFilter filter) {
    PageRequest pageRequest =
        PageRequest.of(
            PageableUtil.getPageNumberForPagination(filter.getPageNumber()), filter.getPageSize());

    Page<TenSetEntity> page =
        this.tenSetRepository.findAll(this.getTenSetSpecification(filter), pageRequest);

    List<Response> responses = new TenSetEntityConverter().getResponses(page.getContent());

    return new Pageable(
        filter.getPageNumber(), page.getSize(), (int) page.getTotalElements(), responses);
  }

  @Override
  public Optional<TenSetEntity> findOne(TenSetFilter filter) {
    Specification<TenSetEntity> tenSetEntityFindOneSpec =
        (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();
          if (StringUtil.isNotBlankOrNull(filter.getId())) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
          }
          if (filter.getYear() != null) {
            predicates.add(cb.equal(root.get("year"), filter.getYear()));
          }

          if (StringUtil.isNotBlankOrNull(filter.getLevelSubjectMaterialId())) {
            predicates.add(
                cb.equal(
                    root.get("levelSubjectMaterial").get("id"),
                    filter.getLevelSubjectMaterialId()));
          }

          if (StringUtil.isNotBlankOrNull(filter.getTitle())) {
            predicates.add(cb.equal(cb.lower(root.get("title")), filter.getTitle().toLowerCase()));
          }

          return cb.and(predicates.toArray(new Predicate[0]));
        };

    return this.tenSetRepository.findOne(tenSetEntityFindOneSpec);
  }

  @Override
  public Optional<TenSetEntity> findById(String id) {
    return this.tenSetRepository.findById(id);
  }

  @Override
  public Optional<String> findLevelSubjectIdById(String id) {
    return this.tenSetRepository.findLevelSubjectIdById(id);
  }

  private Specification<TenSetEntity> getTenSetSpecification(TenSetFilter filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.getYear() != null) {
        predicates.add(cb.equal(root.get("year"), filter.getYear()));
      }
      if (StringUtil.isNotBlankOrNull(filter.getSearchParameter())) {
        predicates.add(
            cb.like(cb.lower(root.get("year")), filter.getSearchParameter().toLowerCase()));
      }
      if (StringUtil.isNotBlankOrNull(filter.getLevelSubjectMaterialId())) {
        predicates.add(
            cb.equal(
                root.get("levelSubjectMaterial").get("id"), filter.getLevelSubjectMaterialId()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
