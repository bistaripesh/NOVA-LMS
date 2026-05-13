package com.inviq.dto.affiliate.response;

import com.inviq.core.dto.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 12/05/2026
 */
@Getter
@Setter
public class AffiliateSearchResponse extends BaseResponse {
  private String profilePicture;
  private String fullName;
  private String email;
  private String phoneNumber;
  private String affiliateType;
  private String localAddress;
  private String city;
  private String state;
}
