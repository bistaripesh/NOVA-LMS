package com.inviq.daoImpl.affiliate;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.entity.PageableUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.affiliate.AffiliateDao;
import com.inviq.dao.affiliate.AffiliateRepository;
import com.inviq.dao.affiliate.converter.AffiliateEntityConverter;
import com.inviq.dao.affiliate.entity.AffiliateEntity;
import com.inviq.dao.affiliate.filter.AffiliateFilter;
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
public class AffiliateDaoImpl implements AffiliateDao {

  private final AffiliateRepository affiliateRepository;

  @Autowired
  public AffiliateDaoImpl(AffiliateRepository affiliateRepository) {
    this.affiliateRepository = affiliateRepository;
  }

  @Override
  public AffiliateEntity insert(AffiliateEntity entity) {
    return this.affiliateRepository.save(entity);
  }

  @Override
  public AffiliateEntity update(AffiliateEntity entity) {
    return this.affiliateRepository.save(entity);
  }

  @Override
  public int delete(AffiliateEntity entity) {
    try {
      this.affiliateRepository.delete(entity);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<AffiliateEntity> findAll(AffiliateFilter filter) {
    return this.affiliateRepository.findAll(this.getAffiliateSpecification(filter));
  }

  @Override
  public long count(AffiliateFilter filter) {
    return 0;
  }

  @Override
  public Pageable findWithPagination(AffiliateFilter filter) {
    PageRequest pageRequest =
        PageRequest.of(
            PageableUtil.getPageNumberForPagination(filter.getPageNumber()), filter.getPageSize());

    Page<AffiliateEntity> page =
        this.affiliateRepository.findAll(this.getAffiliateSpecification(filter), pageRequest);

    List<Response> responses = new AffiliateEntityConverter().getResponses(page.getContent());

    return new Pageable(
        filter.getPageNumber(), page.getSize(), (int) page.getTotalElements(), responses);
  }

  @Override
  public Optional<AffiliateEntity> findOne(AffiliateFilter filter) {
    Specification<AffiliateEntity> affiliateEntityFindOneSpec =
        (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (StringUtil.isNotBlankOrNull(filter.getId())) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
          }
          if (StringUtil.isNotBlankOrNull(filter.getEmail())) {
            predicates.add(cb.equal(root.get("email"), filter.getEmail()));
          }
          if (StringUtil.isNotBlankOrNull(filter.getPhoneNumber())) {
            predicates.add(cb.equal(root.get("phoneNumber"), filter.getPhoneNumber()));
          }

          return cb.or(predicates.toArray(new Predicate[0]));
        };
    return this.affiliateRepository.findOne(affiliateEntityFindOneSpec);
  }

  @Override
  public Optional<AffiliateEntity> findById(String id) {
    return this.affiliateRepository.findById(id);
  }

  private Specification<AffiliateEntity> getAffiliateSpecification(AffiliateFilter filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (StringUtil.isNotBlankOrNull(filter.getSearchParameter())) {
        predicates.add(
            cb.like(cb.lower(root.get("email")), filter.getSearchParameter().toLowerCase()));
      }
      if (StringUtil.isNotBlankOrNull(filter.getAffiliateTypeId())) {
        predicates.add(cb.equal(root.get("affiliateType").get("id"), filter.getAffiliateTypeId()));
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
