package com.inviq.daoImpl;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.entity.PageableUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.LevelSubjectMaterialDao;
import com.inviq.dao.LevelSubjectMaterialRepository;
import com.inviq.dao.entity.LevelSubjectMaterialEntity;
import com.inviq.dao.filter.LevelSubjectMaterialFilter;
import com.inviq.dto.tenset.response.LevelSubjectMaterialSearchResponse;
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
 * @author ripesh created on 06/05/2026
 */
@Repository
public class LevelSubjectMaterialDaoImpl implements LevelSubjectMaterialDao {

  private final LevelSubjectMaterialRepository levelSubjectMaterialRepository;

  @Autowired
  public LevelSubjectMaterialDaoImpl(
      LevelSubjectMaterialRepository levelSubjectMaterialRepository) {
    this.levelSubjectMaterialRepository = levelSubjectMaterialRepository;
  }

  @Override
  public LevelSubjectMaterialEntity insert(LevelSubjectMaterialEntity entity) {
    return this.levelSubjectMaterialRepository.save(entity);
  }

  @Override
  public LevelSubjectMaterialEntity update(LevelSubjectMaterialEntity entity) {
    return this.levelSubjectMaterialRepository.save(entity);
  }

  @Override
  public int delete(LevelSubjectMaterialEntity entity) {
    try {
      this.levelSubjectMaterialRepository.delete(entity);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<LevelSubjectMaterialEntity> findAll(LevelSubjectMaterialFilter filter) {
    return this.levelSubjectMaterialRepository.findAll(
        this.getLevelSubjectMaterialSpecification(filter));
  }

  @Override
  public long count(LevelSubjectMaterialFilter filter) {
    return 0;
  }

  @Override
  public Pageable findWithPagination(LevelSubjectMaterialFilter filter) {
    PageRequest pageRequest =
        PageRequest.of(
            PageableUtil.getPageNumberForPagination(filter.getPageNumber()), filter.getPageSize());

    Page<LevelSubjectMaterialEntity> page =
        this.levelSubjectMaterialRepository.findAll(
            this.getLevelSubjectMaterialSpecification(filter), pageRequest);

    List<Response> responses =
        page.getContent().stream()
            .map(
                entity -> {
                  LevelSubjectMaterialSearchResponse dto = new LevelSubjectMaterialSearchResponse();
                  dto.setId(entity.getId());
                  dto.setMaterialId(entity.getMaterial().getId());
                  dto.setMaterialName(entity.getMaterial().getMaterial());
                  dto.setLevelSubjectId(entity.getLevelSubject().getId());
                  dto.setSubjectName(entity.getLevelSubject().getSubject().getSubject());
                  return dto;
                })
            .collect(Collectors.toList());

    return new Pageable(
        filter.getPageNumber(), page.getSize(), (int) page.getTotalElements(), responses);
  }

  @Override
  public Optional<LevelSubjectMaterialEntity> findOne(LevelSubjectMaterialFilter filter) {
    Specification<LevelSubjectMaterialEntity> levelSubjectMaterialFindOneSpec =
        ((root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();
          if (StringUtil.isNotBlankOrNull(filter.getId())) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
          }
          if (StringUtil.isNotBlankOrNull(filter.getLevelSubjectId())) {
            predicates.add(
                cb.equal(root.get("levelSubject").get("id"), filter.getLevelSubjectId()));
          }
          if (StringUtil.isNotBlankOrNull(filter.getMaterialId())) {
            predicates.add(cb.equal(root.get("material").get("id"), filter.getMaterialId()));
          }
          return cb.and(predicates.toArray(new Predicate[0]));
        });
    return this.levelSubjectMaterialRepository.findOne(levelSubjectMaterialFindOneSpec);
  }

  @Override
  public Optional<LevelSubjectMaterialEntity> findById(String id) {
    return this.levelSubjectMaterialRepository.findById(id);
  }

  private Specification<LevelSubjectMaterialEntity> getLevelSubjectMaterialSpecification(
      LevelSubjectMaterialFilter filter) {
    return ((root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtil.isNotBlankOrNull(filter.getSearchParameter())) {
        predicates.add(
            cb.like(cb.lower(root.get("material")), filter.getSearchParameter().toLowerCase()));
      }

      if (StringUtil.isNotBlankOrNull(filter.getLevelSubjectId())) {
        predicates.add(cb.equal(root.get("levelSubject").get("id"), filter.getLevelSubjectId()));
      }

      if (StringUtil.isNotBlankOrNull(filter.getMaterialId())) {
        predicates.add(cb.equal(root.get("material").get("id"), filter.getMaterialId()));
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    });
  }
}
