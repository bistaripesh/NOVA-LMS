package com.inviq.dto.fonepay.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ripesh created on 14/05/2026
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FonePayLoginRequest {
  private String username;
  private String password;
}
