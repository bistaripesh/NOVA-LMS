package com.inviq.dao.affiliate.filter;

import com.inviq.core.dto.filter.BaseFilterRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 07/05/2026
 */
@Getter
@Setter
public class LevelSubjectChapterFilter extends BaseFilterRequest {
  private String chapterName;
  private String levelSubjectId;
}
