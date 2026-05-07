package com.inviq.dto.tenset.response.user;

import com.inviq.core.dto.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 07/05/2026
 */
@Getter
@Setter
public class UserSearchResponse extends BaseResponse {
  private String firstName;
  private String middleName;
  private String lastName;
  private String email;
}
