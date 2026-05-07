package com.inviq.core.dto.request.crud.delete;

import com.inviq.core.dto.request.crud.Request;
import com.inviq.core.util.validator.crud.BaseDeleteValidator;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 04/04/2025
 */
@Getter
@Setter
public abstract class DeleteRequest extends BaseDeleteValidator<DeleteRequest> implements Request {
  private String id;
}
