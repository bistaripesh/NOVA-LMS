package com.inviq.core.access;

import com.inviq.core.user.UserType;

/**
 * Enforces purchase-based access to level subject content.
 * ADMIN and WRITER bypass the check; USER must have a successful payment.
 */
public interface LevelSubjectAccessService {
  void ensureAccess(String levelSubjectId, String userId, UserType userType);
}
