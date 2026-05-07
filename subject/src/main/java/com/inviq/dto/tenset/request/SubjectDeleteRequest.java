package com.inviq.dto.tenset.request;

import com.inviq.core.dto.request.crud.delete.BaseDeleteRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.SubjectExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 05/05/2026
 */
@Getter
@Setter
public class SubjectDeleteRequest extends BaseDeleteRequest {
  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(SubjectExceptionManager.SubjectError.SUBJECT_ID_EMPTY);
  }
}
