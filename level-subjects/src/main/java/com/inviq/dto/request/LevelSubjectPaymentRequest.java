package com.inviq.dto.request;

import com.inviq.core.dto.request.crud.Request;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.LevelSubjectsException;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 19/05/2026
 */
@Getter
@Setter
public class LevelSubjectPaymentRequest implements Request {
  private String levelSubjectId;
  private String affiliateReferralCode;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(this.levelSubjectId))
      throw new AppException(LevelSubjectsException.LevelSubjectsError.LEVEL_SUBJECT_ID_EMPTY);
  }
}
