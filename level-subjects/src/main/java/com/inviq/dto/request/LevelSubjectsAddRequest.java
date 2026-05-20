package com.inviq.dto.request;

import com.inviq.core.dto.request.crud.add.BaseAddRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.LevelSubjectsException;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 05/05/2026
 */
@Getter
@Setter
public class LevelSubjectsAddRequest extends BaseAddRequest {

  private String levelId;
  private String subjectId;
  private BigDecimal price;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(this.levelId))
      throw new AppException(LevelSubjectsException.LevelSubjectsError.LEVEL_ID_EMPTY);
    if (StringUtil.isBlankOrNull(this.subjectId))
      throw new AppException(LevelSubjectsException.LevelSubjectsError.SUBJECT_ID_EMPTY);
    if (this.price == null)
      throw new AppException(LevelSubjectsException.LevelSubjectsError.PRICE_EMPTY);
  }
}
