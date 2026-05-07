package com.inviq.dto.request;

import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.LevelSubjectChapterExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 07/05/2026
 */
@Getter
@Setter
public class LevelSubjectChapterUpdateRequest extends BaseUpdateRequest {
  private String levelSubjectId;
  private Integer chapterNumber;
  private String chapterName;
  private String chapterDescription;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(
          LevelSubjectChapterExceptionManager.LevelSubjectChapterError.CHAPTER_ID_EMPTY);
    if (StringUtil.isBlankOrNull(this.levelSubjectId))
      throw new AppException(
          LevelSubjectChapterExceptionManager.LevelSubjectChapterError.LEVEL_SUBJECT_ID_EMPTY);
    if (StringUtil.isBlankOrNull(this.chapterName))
      throw new AppException(
          LevelSubjectChapterExceptionManager.LevelSubjectChapterError.CHAPTER_NAME_EMPTY);
    if (chapterNumber == null)
      throw new AppException(
          LevelSubjectChapterExceptionManager.LevelSubjectChapterError.CHAPTER_NUMBER_EMPTY);
  }
}
