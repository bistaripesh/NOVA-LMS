package com.inviq.dto.fonepay.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 14/05/2026
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FonePayIntentQRResponse {
  private String qrString;
  private String qrDisplayName;
  private String status;
  private String terminalId;
  private String prn;
  private String qrMessage;
  private String terminalName;
  private String websocketId;
  private String location;
  private String fonePayPanNumber;
}
