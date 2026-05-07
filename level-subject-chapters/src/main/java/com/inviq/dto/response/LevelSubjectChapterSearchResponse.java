package com.inviq.dto.response;

import com.inviq.core.dto.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 07/05/2026
 */
@Getter
@Setter
public class LevelSubjectChapterSearchResponse extends BaseResponse {
  private String chapterName;
  private String description;
  private String levelSubjectId;
  private String subjectId;
  private String subjectName;
  private String levelId;
  private String levelName;
}
