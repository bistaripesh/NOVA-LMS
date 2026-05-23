package com.inviq.daoImpl;

import com.inviq.core.entity.Pageable;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.PaymentDao;
import com.inviq.dao.PaymentRepository;
import com.inviq.dao.entity.PaymentEntity;
import com.inviq.dao.filter.PaymentFilter;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 21/05/2026
 */
@Repository
public class PaymentDaoImpl implements PaymentDao {

  private final PaymentRepository paymentRepository;

  @Autowired
  public PaymentDaoImpl(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @Override
  public PaymentEntity insert(PaymentEntity entity) {
    return this.paymentRepository.save(entity);
  }

  @Override
  public PaymentEntity update(PaymentEntity entity) {
    return this.paymentRepository.save(entity);
  }

  @Override
  public int delete(PaymentEntity entity) {
    try {
      paymentRepository.delete(entity);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<PaymentEntity> findAll(PaymentFilter filter) {
    Specification<PaymentEntity> paymentEntitySpecification =
        (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (StringUtil.isNotBlankOrNull(filter.getLevelSubjectId())) {
            predicates.add(
                cb.equal(root.get("levelSubjects").get("id"), filter.getLevelSubjectId()));
          }
          if (StringUtil.isNotBlankOrNull(filter.getReferralId())) {
            predicates.add(cb.equal(root.get("referral").get("id"), filter.getReferralId()));
          }
          if (StringUtil.isNotBlankOrNull(filter.getUserId())) {
            predicates.add(cb.equal(root.get("user").get("id"), filter.getUserId()));
          }
          if (StringUtil.isNotBlankOrNull(filter.getAffiliateReferralId())) {
            predicates.add(cb.equal(root.get("affiliateReferral").get("id"), filter.getAffiliateReferralId()));
          }

          if(filter.getIsPaymentSuccess()!=null){
              predicates.add(cb.equal(root.get("isPaymentSuccess"), filter.getIsPaymentSuccess()));
          }

          return cb.and(predicates.toArray(new Predicate[0]));
        };
    return this.paymentRepository.findAll(paymentEntitySpecification);
  }

  @Override
  public long count(PaymentFilter filter) {
    return 0;
  }

  @Override
  public Pageable findWithPagination(PaymentFilter filter) {
    return null;
  }

  @Override
  public Optional<PaymentEntity> findOne(PaymentFilter filter) {
    Specification<PaymentEntity> paymentEntitySpecification =
        (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (StringUtil.isNotBlankOrNull(filter.getLevelSubjectId())) {
            predicates.add(
                cb.equal(root.get("levelSubjects").get("id"), filter.getLevelSubjectId()));
          }

          if (StringUtil.isNotBlankOrNull(filter.getUserId())) {
            predicates.add(cb.equal(root.get("user").get("id"), filter.getUserId()));
          }

            if(filter.getIsPaymentSuccess()!=null){
                predicates.add(cb.equal(root.get("isPaymentSuccess"), filter.getIsPaymentSuccess()));
            }

          return cb.and(predicates.toArray(new Predicate[0]));
        };
    return this.paymentRepository.findOne(paymentEntitySpecification);
  }

  @Override
  public Optional<PaymentEntity> findById(String id) {
    return this.paymentRepository.findById(id);
  }
}
