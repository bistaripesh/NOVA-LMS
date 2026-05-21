package com.inviq.serviceImpl;

import com.inviq.service.LevelSubjectAccessService;
import com.inviq.core.util.StringUtil;
import com.inviq.core.user.UserType;
import com.inviq.dao.PaymentDao;
import com.inviq.dao.filter.PaymentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 22/05/2026
 */
@Service
public class LevelSubjectAccessServiceImpl implements LevelSubjectAccessService {

  private final PaymentDao paymentDao;

  @Autowired
  public LevelSubjectAccessServiceImpl(PaymentDao paymentDao) {
    this.paymentDao = paymentDao;
  }

  @Override
  public void ensureAccess(String levelSubjectId, String userId, UserType userType) {
    if (userType == UserType.ADMIN || userType == UserType.WRITER) return;
    if (StringUtil.isBlankOrNull(levelSubjectId)) return;

    PaymentFilter filter = new PaymentFilter();
    filter.setLevelSubjectId(levelSubjectId);
    filter.setUserId(userId);
    filter.setIsPaymentSuccess(true);

    this.paymentDao
        .findOne(filter)
        .orElseThrow(
            () -> new AccessDeniedException("Purchase required to access this content"));
  }
}
