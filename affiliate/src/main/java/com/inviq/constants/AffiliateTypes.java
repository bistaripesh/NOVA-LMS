package com.inviq.constants;

import com.inviq.core.exception.AppException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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
        .orElseThrow(
            () ->
                new AppException(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), "Affiliate type not found"));
  }
}
