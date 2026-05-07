package com.inviq.core.entity;

import com.inviq.core.dto.filter.BaseFilterRequest;
import com.inviq.core.dto.filter.Filter;

/**
 * @author ripesh created on 18/04/2025
 */
public class SortingOrderUtil {
  public static SortingOrder getSortingOrder(Filter filter) {
    BaseFilterRequest baseFilter = (BaseFilterRequest) filter;
    return SortingOrder.getByCode(baseFilter.getSortOrder());
  }
}
