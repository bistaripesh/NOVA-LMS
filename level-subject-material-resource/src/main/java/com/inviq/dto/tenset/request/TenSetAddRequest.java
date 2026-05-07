package com.inviq.dto.tenset.request;

import com.inviq.core.dto.request.crud.add.BaseAddRequest;
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
public class TenSetAddRequest extends BaseAddRequest {

  private String title;
  private Integer year;
  private String levelSubjectMaterialId;
  // TODO REMOVE ON FUTURE DEVELOPMENT
  private String pdfLocation;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(this.title))
      throw new AppException(TenSetExceptionManager.TenSetError.TITLE_EMPTY);
    if (StringUtil.isBlankOrNull(this.levelSubjectMaterialId))
      throw new AppException(TenSetExceptionManager.TenSetError.LEVEL_SUBJECT_MATERIAL_ID_EMPTY);
    if (StringUtil.isBlankOrNull(this.pdfLocation))
      throw new AppException(TenSetExceptionManager.TenSetError.PDF_UPLOAD_EMPTY);
  }
}
