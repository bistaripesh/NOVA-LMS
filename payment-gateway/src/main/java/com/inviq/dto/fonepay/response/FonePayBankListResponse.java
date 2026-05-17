package com.inviq.dto.fonepay.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 14/05/2026
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FonePayBankListResponse {
  private List<BankDetails> bankDetails;

  public FonePayBankListResponse() {
    this.bankDetails = new ArrayList<>();
  }
}
