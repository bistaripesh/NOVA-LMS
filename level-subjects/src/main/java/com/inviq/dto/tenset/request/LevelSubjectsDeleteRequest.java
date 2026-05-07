package com.inviq.dto.tenset.request;

import com.inviq.core.dto.request.crud.delete.BaseDeleteRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.LevelSubjectsException;

/**
 * @author ripesh created on 05/05/2026
 */
public class LevelSubjectsDeleteRequest extends BaseDeleteRequest {
  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(LevelSubjectsException.LevelSubjectsError.LEVEL_SUBJECT_ID_EMPTY);
  }
}
