package com.inviq.util;

import com.inviq.constants.FonePayEnvConstants;
import com.inviq.core.util.SystemUtil;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * @author ripesh created on 14/05/2026
 */
public class FonePaySignatureUtil {

  public static String sign(String payload) throws Exception {
    PrivateKey privateKey =
        getPrivateKey(SystemUtil.getEnv(FonePayEnvConstants.FONE_PAY_PRIVATE_KEY));
    Signature signature = Signature.getInstance("SHA256withRSA");
    signature.initSign(privateKey);
    signature.update(payload.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(signature.sign());
  }

  private static PrivateKey getPrivateKey(String key) throws Exception {
    String cleaned =
        key.replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("-----BEGIN RSA PRIVATE KEY-----", "")
            .replace("-----END RSA PRIVATE KEY-----", "")
            .replaceAll("[^A-Za-z0-9+/=]", "");

    byte[] decoded = Base64.getDecoder().decode(cleaned);
    return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
  }
}
