package com.inviq.core.util.validator;

import com.inviq.core.dto.request.crud.Request;

/**
 * @author ripesh created on 09/04/2025
 */
public interface Validator<Req extends Request> {
  void validateBasicInput(Req request);
}
