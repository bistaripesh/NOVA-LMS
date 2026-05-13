package com.inviq.constants;

import com.inviq.core.exception.DynamicAppException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 12/05/2026
 */
@Getter
@AllArgsConstructor
public enum AffiliateTypes {
  COMPANY("COMPANY", "Company"),
  MARKETER("MARKETER", "Marketer"),
  DISTRIBUTOR("DISTRIBUTOR", "Distributor"),
  TEACHER("TEACHER", "Teacher"),
  ;

  private final String code;
  private final String displayName;

  public static AffiliateTypes getByCode(String code) {
    return Arrays.stream(AffiliateTypes.values())
        .filter(affiliateType -> affiliateType.code.equals(code))
        .findFirst()
        .orElseThrow(() -> new DynamicAppException("Affiliate type not found"));
  }
}
