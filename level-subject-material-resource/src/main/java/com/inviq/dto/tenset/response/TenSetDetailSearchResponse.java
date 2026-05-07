package com.inviq.dto.tenset.response;

import com.inviq.core.dto.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 06/05/2026
 */
@Getter
@Setter
public class TenSetDetailSearchResponse extends BaseResponse {
  private String title;
  private byte[] pdfByte;
  private Integer year;
  private int bookTotalPageNumber;
}
