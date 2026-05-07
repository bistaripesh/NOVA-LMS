package com.inviq.dto.tenset.request;

import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.tenset.TenSetExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 06/05/2026
 */
@Getter
@Setter
public class TenSetUpdateRequest extends BaseUpdateRequest {
  private String title;
  private Integer year;
  private String pdfLocation;
  private String levelSubjectMaterialId;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(TenSetExceptionManager.TenSetError.TEN_SET_ID_EMPTY);
    if (StringUtil.isBlankOrNull(this.title))
      throw new AppException(TenSetExceptionManager.TenSetError.TITLE_EMPTY);
  }
}
