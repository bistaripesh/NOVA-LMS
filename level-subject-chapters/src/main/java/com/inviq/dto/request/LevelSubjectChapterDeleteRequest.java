package com.inviq.dto.request;

import com.inviq.core.dto.request.crud.delete.BaseDeleteRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.LevelSubjectChapterExceptionManager;

/**
 * @author ripesh created on 07/05/2026
 */
public class LevelSubjectChapterDeleteRequest extends BaseDeleteRequest {
  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(
          LevelSubjectChapterExceptionManager.LevelSubjectChapterError.CHAPTER_ID_EMPTY);
  }
}
