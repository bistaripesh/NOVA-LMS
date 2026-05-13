package com.inviq.dao.affiliateType.entity;

import com.inviq.core.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author ripesh created on 12/05/2026
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "affiliate_types")
@Getter
@Setter
@NoArgsConstructor
public class AffiliateTypeEntity extends BaseEntity<AffiliateTypeEntity> {
  private String affiliateType;
}
