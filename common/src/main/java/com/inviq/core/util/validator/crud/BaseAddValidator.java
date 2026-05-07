package com.inviq.core.util.validator.crud;

import com.inviq.core.dto.request.crud.Request;
import com.inviq.core.util.validator.Validator;

/**
 * @author ripesh created on 24/04/2026
 */
public class BaseAddValidator<Req extends Request> implements Validator<Req> {
  @Override
  public void validateBasicInput(Req request) {}
}
