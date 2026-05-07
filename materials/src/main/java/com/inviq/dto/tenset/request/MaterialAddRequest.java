package com.inviq.dto.tenset.request;

import com.inviq.core.dto.request.crud.add.BaseAddRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.MaterialExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 05/05/2026
 */
@Getter
@Setter
public class MaterialAddRequest extends BaseAddRequest {

  private String materialName;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(this.materialName))
      throw new AppException(MaterialExceptionManager.MaterialError.MATERIAL_NAME_EMPTY);
  }
}
