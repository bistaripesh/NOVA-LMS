package com.inviq.core.dto.filter;

import com.inviq.core.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 04/04/2025
 */
@Getter
@Setter
public abstract class BaseFilterRequest extends Filter {
  private String id;
  private String sortBy;
  private String sortOrder;
  private int pageNumber;
  private int pageSize;
  private String searchParameter;

  public BaseFilterRequest() {
    this.pageNumber = 1;
    this.pageSize = 10;
  }

  @Override
  public String getSearchParameter() {
    if (StringUtil.isNotBlankOrNull(this.searchParameter)) {
      return StringUtil.generateSqlWildCardOnBothSides(this.searchParameter);
    }
    return this.searchParameter;
  }
}
