package com.inviq.dto.tenset.request;

import com.inviq.core.dto.request.crud.add.BaseAddRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.LevelSubjectMaterialExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 06/05/2026
 */
@Getter
@Setter
public class LevelSubjectMaterialAddRequest extends BaseAddRequest {
  private String levelSubjectId;
  private String materialId;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(this.levelSubjectId))
      throw new AppException(
          LevelSubjectMaterialExceptionManager.LevelSubjectMaterialError.LEVEL_SUBJECT_ID_EMPTY);
    if (StringUtil.isBlankOrNull(this.materialId))
      throw new AppException(
          LevelSubjectMaterialExceptionManager.LevelSubjectMaterialError.MATERIAL_ID_EMPTY);
  }
}
