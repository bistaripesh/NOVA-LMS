package com.inviq.dao.entity;

import com.inviq.core.entity.BaseEntity;
import com.inviq.dao.affiliate.entity.AffiliateEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author ripesh created on 13/05/2026
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "affiliate_referrals")
@Getter
@Setter
@NoArgsConstructor
public class AffiliateReferralEntity extends BaseEntity<AffiliateReferralEntity> {

  @ManyToOne(fetch = FetchType.LAZY)
  private AffiliateEntity affiliate;

  private Integer commissionPercent;

  private String referralCode;

  private String description;

  private Boolean isReferralCodeActive;

  private Boolean isTransactionSettled;
}
