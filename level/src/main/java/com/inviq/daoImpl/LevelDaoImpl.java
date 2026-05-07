package com.inviq.daoImpl;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.entity.PageableUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.LevelDao;
import com.inviq.dao.LevelRepository;
import com.inviq.dao.entity.LevelEntity;
import com.inviq.dao.filter.LevelFilter;
import com.inviq.dto.tenset.response.LevelSearchResponse;
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
public class LevelDaoImpl implements LevelDao {

  private final LevelRepository levelRepository;

  @Autowired
  public LevelDaoImpl(LevelRepository levelRepository) {
    this.levelRepository = levelRepository;
  }

  @Override
  public LevelEntity insert(LevelEntity entity) {
    return this.levelRepository.save(entity);
  }

  @Override
  public LevelEntity update(LevelEntity entity) {
    return this.levelRepository.save(entity);
  }

  @Override
  public int delete(LevelEntity entity) {
    try {
      this.levelRepository.delete(entity);
      return 1;
    } catch (Exception e) {

      return 0;
    }
  }

  @Override
  public List<LevelEntity> findAll(LevelFilter filter) {

    return this.levelRepository.findAll(this.getLevelSpecification(filter));
  }

  @Override
  public long count(LevelFilter filter) {
    return 0;
  }

  @Override
  public Pageable findWithPagination(LevelFilter filter) {
    PageRequest pageRequest =
        PageRequest.of(
            PageableUtil.getPageNumberForPagination(filter.getPageNumber()), filter.getPageSize());

    Page<LevelEntity> page =
        this.levelRepository.findAll(this.getLevelSpecification(filter), pageRequest);

    List<Response> responses =
        page.getContent().stream()
            .map(
                entity -> {
                  LevelSearchResponse dto = new LevelSearchResponse();
                  dto.setId(entity.getId());
                  dto.setLevelName(entity.getLevel());
                  return dto;
                })
            .collect(Collectors.toList());

    return new Pageable(
        filter.getPageNumber(), page.getSize(), (int) page.getTotalElements(), responses);
  }

  @Override
  public Optional<LevelEntity> findOne(LevelFilter filter) {
    Specification<LevelEntity> levelFindOneSpec =
        (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();
          if (StringUtil.isNotBlankOrNull(filter.getId())) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
          }
          if (StringUtil.isNotBlankOrNull(filter.getLevelName())) {
            predicates.add(cb.equal(root.get("level"), filter.getLevelName()));
          }
          return cb.and(predicates.toArray(new Predicate[0]));
        };
    return this.levelRepository.findOne(levelFindOneSpec);
  }

  @Override
  public Optional<LevelEntity> findById(String id) {
    return this.levelRepository.findById(id);
  }

  private Specification<LevelEntity> getLevelSpecification(LevelFilter filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtil.isNotBlankOrNull(filter.getSearchParameter())) {
        predicates.add(
            cb.like(cb.lower(root.get("level")), filter.getSearchParameter().toLowerCase()));
      }

      if (StringUtil.isNotBlankOrNull(filter.getLevelName())) {
        predicates.add(cb.equal(cb.lower(root.get("level")), filter.getLevelName().toLowerCase()));
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
