package com.inviq.dto.request;

import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.core.exception.AppException;
import com.inviq.exception.LevelSubjectsException;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 05/05/2026
 */
@Getter
@Setter
public class LevelSubjectsUpdateRequest extends BaseUpdateRequest {
  private String subjectId;
  private String levelId;
  private BigDecimal price;

  @Override
  public void validateRequest() {
    if (this.price == null)
      throw new AppException(LevelSubjectsException.LevelSubjectsError.PRICE_EMPTY);
  }
}
