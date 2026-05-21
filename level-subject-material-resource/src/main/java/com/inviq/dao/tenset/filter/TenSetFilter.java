package com.inviq.dao.tenset.filter;

import com.inviq.core.dto.filter.BaseFilterRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 06/05/2026
 */
@Getter
@Setter
public class TenSetFilter extends BaseFilterRequest {
  private Integer year;
  private String levelSubjectMaterialId;
  private String levelSubjectId;
  private String title;
  private int bookPageNumber;
}
