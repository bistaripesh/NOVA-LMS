package com.inviq.dto.tenset.request;

import com.inviq.core.dto.request.crud.delete.BaseDeleteRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.MaterialExceptionManager;

/**
 * @author ripesh created on 05/05/2026
 */
public class MaterialDeleteRequest extends BaseDeleteRequest {
  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(MaterialExceptionManager.MaterialError.MATERIAL_ID_EMPTY);
  }
}
