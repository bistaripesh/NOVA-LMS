package com.inviq.serviceImpl;

import com.inviq.core.context.UserPrincipal;
import com.inviq.core.user.UserType;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.LevelSubjectsDao;
import com.inviq.dao.PaymentDao;
import com.inviq.dao.entity.LevelSubjectsEntity;
import com.inviq.dao.filter.LevelSubjectsFilter;
import com.inviq.dao.filter.PaymentFilter;
import com.inviq.service.LevelSubjectAccessService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 22/05/2026
 */
@Service
public class LevelSubjectAccessServiceImpl implements LevelSubjectAccessService {

  private final PaymentDao paymentDao;
  private final LevelSubjectsDao levelSubjectsDao;

  @Autowired
  public LevelSubjectAccessServiceImpl(PaymentDao paymentDao, LevelSubjectsDao levelSubjectsDao) {
    this.paymentDao = paymentDao;
    this.levelSubjectsDao = levelSubjectsDao;
  }

  @Override
  public void ensureAccess(String levelSubjectId) {
    UserPrincipal currentUser =
        (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    UserType userType = currentUser.getUserType();

    if (userType == UserType.ADMIN || userType == UserType.WRITER) return;
    if (StringUtil.isBlankOrNull(levelSubjectId)) return;

    LevelSubjectsFilter subjectFilter = new LevelSubjectsFilter();
    subjectFilter.setId(levelSubjectId);
    LevelSubjectsEntity levelSubject = this.levelSubjectsDao.findOne(subjectFilter).orElse(null);

    if (levelSubject == null) return;

    if (levelSubject.getPrice() == null || levelSubject.getPrice().compareTo(BigDecimal.ZERO) == 0)
      return;

    PaymentFilter paymentFilter = new PaymentFilter();
    paymentFilter.setLevelSubjectId(levelSubjectId);
    paymentFilter.setUserId(currentUser.getUserId());
    paymentFilter.setIsPaymentSuccess(true);

    this.paymentDao
        .findOne(paymentFilter)
        .orElseThrow(() -> new AccessDeniedException("Purchase required to access this content"));
  }
}
