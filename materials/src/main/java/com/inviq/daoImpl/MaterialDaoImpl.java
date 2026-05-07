package com.inviq.daoImpl;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.entity.PageableUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.MaterialDao;
import com.inviq.dao.MaterialRepository;
import com.inviq.dao.entity.MaterialEntity;
import com.inviq.dao.filter.MaterialFilter;
import com.inviq.dto.tenset.response.MaterialSearchResponse;
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
public class MaterialDaoImpl implements MaterialDao {

  private final MaterialRepository materialRepository;

  @Autowired
  public MaterialDaoImpl(MaterialRepository materialRepository) {
    this.materialRepository = materialRepository;
  }

  @Override
  public MaterialEntity insert(MaterialEntity entity) {
    return this.materialRepository.save(entity);
  }

  @Override
  public MaterialEntity update(MaterialEntity entity) {
    return this.materialRepository.save(entity);
  }

  @Override
  public int delete(MaterialEntity entity) {
    try {
      this.materialRepository.delete(entity);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<MaterialEntity> findAll(MaterialFilter filter) {
    return this.materialRepository.findAll(this.getMaterialSpecification(filter));
  }

  @Override
  public long count(MaterialFilter filter) {
    return 0;
  }

  @Override
  public Pageable findWithPagination(MaterialFilter filter) {
    PageRequest pageRequest =
        PageRequest.of(
            PageableUtil.getPageNumberForPagination(filter.getPageNumber()), filter.getPageSize());

    Page<MaterialEntity> page =
        this.materialRepository.findAll(this.getMaterialSpecification(filter), pageRequest);

    List<Response> responses =
        page.getContent().stream()
            .map(
                entity -> {
                  MaterialSearchResponse dto = new MaterialSearchResponse();
                  dto.setId(entity.getId());
                  dto.setMaterial(entity.getMaterial());
                  return dto;
                })
            .collect(Collectors.toList());

    return new Pageable(
        filter.getPageNumber(), page.getSize(), (int) page.getTotalElements(), responses);
  }

  @Override
  public Optional<MaterialEntity> findOne(MaterialFilter filter) {
    Specification<MaterialEntity> materialFindOneSpec =
        (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();
          if (StringUtil.isNotBlankOrNull(filter.getId())) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
          }
          if (StringUtil.isNotBlankOrNull(filter.getMaterialName())) {
            predicates.add(
                cb.equal(cb.lower(root.get("material")), filter.getMaterialName().toLowerCase()));
          }
          return cb.and(predicates.toArray(new Predicate[0]));
        };
    return this.materialRepository.findOne(materialFindOneSpec);
  }

  @Override
  public Optional<MaterialEntity> findById(String id) {
    return this.materialRepository.findById(id);
  }

  private Specification<MaterialEntity> getMaterialSpecification(MaterialFilter filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (StringUtil.isNotBlankOrNull(filter.getSearchParameter())) {
        predicates.add(
            cb.like(cb.lower(root.get("material")), filter.getSearchParameter().toLowerCase()));
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
