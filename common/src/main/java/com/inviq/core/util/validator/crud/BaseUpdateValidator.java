package com.inviq.core.util.validator.crud;

import com.inviq.core.dto.request.crud.Request;
import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.exception.manager.BasicRequestExceptionManager;
import com.inviq.core.util.StringUtil;
import com.inviq.core.util.validator.Validator;

/**
 * @author ripesh created on 09/04/2025
 */
public abstract class BaseUpdateValidator<Req extends Request> implements Validator<Req> {

  @Override
  public void validateBasicInput(Req updateRequest) {
    BaseUpdateRequest request = (BaseUpdateRequest) updateRequest;
    if (StringUtil.isBlankOrNull(request.getId())) {
      throw new AppException(BasicRequestExceptionManager.BasicRequestError.ID_MISSING_FOR_UPDATE);
    }
  }
}
