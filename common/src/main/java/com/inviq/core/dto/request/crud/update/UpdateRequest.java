package com.inviq.core.dto.request.crud.update;

import com.inviq.core.dto.request.crud.Request;
import com.inviq.core.util.validator.crud.BaseUpdateValidator;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 04/04/2025
 */
@Getter
@Setter
public abstract class UpdateRequest extends BaseUpdateValidator<UpdateRequest> implements Request {
  protected String id;
}
