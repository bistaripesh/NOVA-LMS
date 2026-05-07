package com.inviq.dto.tenset.request;

import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.LevelExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 05/05/2026
 */
@Getter
@Setter
public class LevelUpdateRequest extends BaseUpdateRequest {

  private String levelName;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(this.levelName))
      throw new AppException(LevelExceptionManager.LevelError.LEVEL_NAME_EMPTY);
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(LevelExceptionManager.LevelError.LEVEL_ID_EMPTY);
  }
}
