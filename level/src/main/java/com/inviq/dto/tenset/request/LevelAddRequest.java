package com.inviq.dto.tenset.request;

import com.inviq.core.dto.request.crud.add.BaseAddRequest;
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
public class LevelAddRequest extends BaseAddRequest {
  private String levelName;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(this.levelName))
      throw new AppException(LevelExceptionManager.LevelError.LEVEL_NAME_EMPTY);
  }
}
