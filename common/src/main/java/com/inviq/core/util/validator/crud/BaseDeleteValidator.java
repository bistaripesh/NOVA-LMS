package com.inviq.core.util.validator.crud;

import com.inviq.core.dto.request.crud.Request;
import com.inviq.core.dto.request.crud.delete.BaseDeleteRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.exception.manager.BasicRequestExceptionManager;
import com.inviq.core.util.StringUtil;
import com.inviq.core.util.validator.Validator;

/**
 * @author ripesh created on 17/04/2026
 */
public abstract class BaseDeleteValidator<Req extends Request> implements Validator<Req> {

  @Override
  public void validateBasicInput(Req deleteRequest) {
    BaseDeleteRequest request = (BaseDeleteRequest) deleteRequest;
    if (StringUtil.isBlankOrNull(request.getId())) {
      throw new AppException(BasicRequestExceptionManager.BasicRequestError.ID_MISSING_FOR_DELETE);
    }
  }
}
