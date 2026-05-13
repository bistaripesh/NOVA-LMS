package com.inviq.util;

import java.security.SecureRandom;

/**
 * @author ripesh created on 13/05/2026
 */
public class ReferralCodeUtil {

  public static String generateReferralCode() {
    String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    int CODE_LENGTH = 10;
    SecureRandom random = new SecureRandom();
    StringBuilder promoCode = new StringBuilder(CODE_LENGTH);
    for (int i = 0; i < CODE_LENGTH; i++) {
      int index = random.nextInt(CHARACTERS.length());
      promoCode.append(CHARACTERS.charAt(index));
    }
    return promoCode.toString();
  }
}
