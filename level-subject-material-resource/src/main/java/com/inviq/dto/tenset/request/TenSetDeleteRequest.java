package com.inviq.dto.tenset.request;

import com.inviq.core.dto.request.crud.delete.BaseDeleteRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.tenset.TenSetExceptionManager;

/**
 * @author ripesh created on 06/05/2026
 */
public class TenSetDeleteRequest extends BaseDeleteRequest {
  @Override
  public void validateRequest() {

    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(TenSetExceptionManager.TenSetError.TEN_SET_ID_EMPTY);
  }
}
