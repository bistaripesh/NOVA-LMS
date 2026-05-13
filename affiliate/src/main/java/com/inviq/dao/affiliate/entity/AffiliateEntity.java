package com.inviq.dao.affiliate.entity;

import com.inviq.core.entity.BaseEntity;
import com.inviq.dao.affiliateType.entity.AffiliateTypeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author ripesh created on 11/05/2026
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "affiliates")
@Getter
@Setter
@NoArgsConstructor
public class AffiliateEntity extends BaseEntity<AffiliateEntity> {
  private String displayPicture;
  private String fullName;
  private String email;
  private String phoneNumber;
  private String localAddress;
  private String city;
  private String state;

  @ManyToOne(fetch = FetchType.LAZY)
  private AffiliateTypeEntity affiliateType;
}
