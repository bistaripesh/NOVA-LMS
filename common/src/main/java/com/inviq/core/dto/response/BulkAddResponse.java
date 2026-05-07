package com.inviq.core.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 30/04/2026
 */
@Getter
@Setter
public class BulkAddResponse<T extends BaseResponse> extends BaseResponse {

  private List<T> items;

  public BulkAddResponse() {
    this.items = new ArrayList<>();
  }
}
