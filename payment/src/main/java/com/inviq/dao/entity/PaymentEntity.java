package com.inviq.dao.entity;

import com.inviq.core.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

/**
 * @author ripesh created on 21/05/2026
 */
@Entity
@Table(name = "payments")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class PaymentEntity extends BaseEntity<PaymentEntity> {
  @ManyToOne(fetch = FetchType.LAZY)
  private LevelSubjectsEntity levelSubjects;

  @ManyToOne(fetch = FetchType.LAZY)
  private UserEntity user;

  private BigDecimal amount;

  private String referenceLabel;
  private String referralCode;
  private Boolean isPaymentSuccess;
}
