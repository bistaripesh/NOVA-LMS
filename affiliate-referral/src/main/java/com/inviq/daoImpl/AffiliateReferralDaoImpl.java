package com.inviq.daoImpl;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.entity.PageableUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.AffiliateReferralDao;
import com.inviq.dao.AffiliateReferralRepository;
import com.inviq.dao.converter.AffiliateReferralEntityConverter;
import com.inviq.dao.entity.AffiliateReferralEntity;
import com.inviq.dao.filter.AffiliateReferralFilter;
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
 * @author ripesh created on 13/05/2026
 */
@Repository
public class AffiliateReferralDaoImpl implements AffiliateReferralDao {

  private final AffiliateReferralRepository affiliateReferralRepository;

  @Autowired
  public AffiliateReferralDaoImpl(AffiliateReferralRepository affiliateReferralRepository) {
    this.affiliateReferralRepository = affiliateReferralRepository;
  }

  @Override
  public AffiliateReferralEntity insert(AffiliateReferralEntity entity) {
    return this.affiliateReferralRepository.save(entity);
  }

  @Override
  public AffiliateReferralEntity update(AffiliateReferralEntity entity) {
    return this.affiliateReferralRepository.save(entity);
  }

  @Override
  public int delete(AffiliateReferralEntity entity) {
    try {
      this.affiliateReferralRepository.delete(entity);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<AffiliateReferralEntity> findAll(AffiliateReferralFilter filter) {
    return this.affiliateReferralRepository.findAll(this.getAffiliateReferralSpecification(filter));
  }

  @Override
  public long count(AffiliateReferralFilter filter) {
    return 0;
  }

  @Override
  public Pageable findWithPagination(AffiliateReferralFilter filter) {
    PageRequest pageRequest =
        PageRequest.of(
            PageableUtil.getPageNumberForPagination(filter.getPageNumber()), filter.getPageSize());

    Page<AffiliateReferralEntity> page =
        this.affiliateReferralRepository.findAll(
            this.getAffiliateReferralSpecification(filter), pageRequest);

    List<Response> responses =
        new AffiliateReferralEntityConverter().getResponses(page.getContent());

    return new Pageable(
        filter.getPageNumber(), page.getSize(), (int) page.getTotalElements(), responses);
  }

  @Override
  public Optional<AffiliateReferralEntity> findOne(AffiliateReferralFilter filter) {
    Specification<AffiliateReferralEntity> affiliateReferralFindOneSpec =
        (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (StringUtil.isNotBlankOrNull(filter.getId())) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
          }
          if (StringUtil.isNotBlankOrNull(filter.getAffiliateId())) {
            predicates.add(cb.equal(root.get("affiliate").get("id"), filter.getAffiliateId()));
          }

          if (StringUtil.isNotBlankOrNull(filter.getReferralCode())) {
            predicates.add(cb.equal(root.get("referralCode"), filter.getReferralCode()));
          }

          if (filter.getIsReferralCodeActive() != null) {
            predicates.add(
                cb.equal(root.get("isReferralCodeActive"), filter.getIsReferralCodeActive()));
          }

          return cb.and(predicates.toArray(new Predicate[0]));
        };
    return this.affiliateReferralRepository.findOne(affiliateReferralFindOneSpec);
  }

  @Override
  public Optional<AffiliateReferralEntity> findById(String id) {
    return this.affiliateReferralRepository.findById(id);
  }

  private Specification<AffiliateReferralEntity> getAffiliateReferralSpecification(
      AffiliateReferralFilter filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (StringUtil.isNotBlankOrNull(filter.getAffiliateId())) {
        predicates.add(cb.equal(root.get("affiliate").get("id"), filter.getAffiliateId()));
      }
      if (filter.getIsReferralCodeActive() != null) {
        predicates.add(
            cb.equal(root.get("isReferralCodeActive"), filter.getIsReferralCodeActive()));
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
