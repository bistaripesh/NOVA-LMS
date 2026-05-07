package com.inviq.core.dto.request.crud.view;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 04/04/2025
 */
@Getter
@Setter
public abstract class BaseViewResponse extends ViewResponse {
  private String createdOn;
  private String createdBy;
  private String modifiedOn;
  private String modifiedBy;
  private int version;
}
