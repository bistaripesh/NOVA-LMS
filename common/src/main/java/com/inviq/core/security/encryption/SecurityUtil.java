package com.inviq.core.security.encryption;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author ripesh created on 20/04/2025
 */
public class SecurityUtil {
  public static String encrypt(String plainText, String secretKey) throws Exception {
    byte[] keyData = secretKey.getBytes();
    SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
    Cipher cipher = Cipher.getInstance("Blowfish");
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
    byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  public static String decrypt(String encryptedText, String secretKey) throws Exception {
    byte[] keyData = secretKey.getBytes();
    Cipher cipher = Cipher.getInstance("Blowfish");
    SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
    byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
    return new String(decryptedBytes);
  }
}
