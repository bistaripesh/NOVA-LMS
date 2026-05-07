package com.inviq.dto.tenset.request;

import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
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
public class SubjectUpdateRequest extends BaseUpdateRequest {

  private String subjectName;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(SubjectExceptionManager.SubjectError.SUBJECT_ID_EMPTY);

    if (StringUtil.isBlankOrNull(this.subjectName))
      throw new AppException(SubjectExceptionManager.SubjectError.SUBJECT_NAME_EMPTY);
  }
}
