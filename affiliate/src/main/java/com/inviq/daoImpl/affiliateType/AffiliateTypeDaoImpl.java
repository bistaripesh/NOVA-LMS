package com.inviq.daoImpl.affiliateType;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.entity.PageableUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.affiliateType.AffiliateTypeDao;
import com.inviq.dao.affiliateType.AffiliateTypeRepository;
import com.inviq.dao.affiliateType.converter.AffiliateTypeEntityConverter;
import com.inviq.dao.affiliateType.entity.AffiliateTypeEntity;
import com.inviq.dao.affiliateType.filter.AffiliateTypeFilter;
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
 * @author ripesh created on 12/05/2026
 */
@Repository
public class AffiliateTypeDaoImpl implements AffiliateTypeDao {

  private final AffiliateTypeRepository affiliateTypeRepository;

  @Autowired
  public AffiliateTypeDaoImpl(AffiliateTypeRepository affiliateTypeRepository) {
    this.affiliateTypeRepository = affiliateTypeRepository;
  }

  @Override
  public AffiliateTypeEntity insert(AffiliateTypeEntity entity) {
    return this.affiliateTypeRepository.save(entity);
  }

  @Override
  public AffiliateTypeEntity update(AffiliateTypeEntity entity) {
    return this.affiliateTypeRepository.save(entity);
  }

  @Override
  public int delete(AffiliateTypeEntity entity) {
    try {
      this.affiliateTypeRepository.delete(entity);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<AffiliateTypeEntity> findAll(AffiliateTypeFilter filter) {
    return this.affiliateTypeRepository.findAll(this.getAffiliateTypeSpecification(filter));
  }

  @Override
  public long count(AffiliateTypeFilter filter) {
    return 0;
  }

  @Override
  public Pageable findWithPagination(AffiliateTypeFilter filter) {
    PageRequest pageRequest =
        PageRequest.of(
            PageableUtil.getPageNumberForPagination(filter.getPageNumber()), filter.getPageSize());

    Page<AffiliateTypeEntity> page =
        this.affiliateTypeRepository.findAll(
            this.getAffiliateTypeSpecification(filter), pageRequest);

    List<Response> responses = new AffiliateTypeEntityConverter().getResponses(page.getContent());

    return new Pageable(
        filter.getPageNumber(), page.getSize(), (int) page.getTotalElements(), responses);
  }

  @Override
  public Optional<AffiliateTypeEntity> findOne(AffiliateTypeFilter filter) {
    Specification<AffiliateTypeEntity> affiliateTypeFindOneSpec =
        (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (StringUtil.isNotBlankOrNull(filter.getId())) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
          }

          if (StringUtil.isNotBlankOrNull(filter.getAffiliateType())) {
            predicates.add(
                cb.equal(
                    cb.lower(root.get("affiliateType")), filter.getAffiliateType().toLowerCase()));
          }

          return cb.and(predicates.toArray(new Predicate[0]));
        };
    return this.affiliateTypeRepository.findOne(affiliateTypeFindOneSpec);
  }

  @Override
  public Optional<AffiliateTypeEntity> findById(String id) {
    return this.affiliateTypeRepository.findById(id);
  }

  private Specification<AffiliateTypeEntity> getAffiliateTypeSpecification(
      AffiliateTypeFilter filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtil.isNotBlankOrNull(filter.getSearchParameter())) {
        predicates.add(
            cb.like(
                cb.lower(root.get("affiliateType")), filter.getSearchParameter().toLowerCase()));
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
