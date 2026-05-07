package com.inviq.core.security.token;

import com.inviq.core.constants.NovaLmsAppSecurityEnvConstants;
import com.inviq.core.constants.NovaLmsSecurityConstants;
import com.inviq.core.exception.DynamicAppException;
import com.inviq.core.security.encryption.SecurityUtil;
import com.inviq.core.util.SystemUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import org.springframework.http.HttpStatus;

/**
 * @author ripesh created on 02/12/2023
 */
public class JWTTokenUtils {

  private static final Logger LOGGER = Logger.getLogger(JWTTokenUtils.class.getSimpleName());

  public static String getJWTToken(JWTTokenInfo jwtTokenInfo) {
    LocalDateTime expiryDateTime =
        LocalDateTime.now().plusMinutes(NovaLmsSecurityConstants.JWT_TOKEN_EXPIRATION_MINUTES);
    Instant instant = expiryDateTime.atZone(ZoneId.systemDefault()).toInstant();
    SecretKey jwtSecretKey =
        Keys.hmacShaKeyFor(
            Base64.getDecoder()
                .decode(SystemUtil.getEnv(NovaLmsAppSecurityEnvConstants.JWT_TOKEN_SECRET_KEY)));
    return Jwts.builder()
        .claims(jwtTokenInfo.getClaims())
        .subject(jwtTokenInfo.getSubject())
        .expiration(Date.from(instant))
        .signWith(jwtSecretKey)
        .compact();
  }

  public static String getRefreshToken(
      JWTTokenInfo jwtTokenInfo, boolean isEncrypted, String secretKey) throws Exception {
    LocalDate localDate =
        LocalDate.now().plusDays(NovaLmsSecurityConstants.REFRESH_TOKEN_EXPIRATION_DAYS);
    Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
    SecretKey jwtSecretKey =
        Keys.hmacShaKeyFor(
            Base64.getDecoder()
                .decode(SystemUtil.getEnv(NovaLmsAppSecurityEnvConstants.JWT_TOKEN_SECRET_KEY)));
    String token =
        Jwts.builder()
            .claims(jwtTokenInfo.getClaims())
            .subject(jwtTokenInfo.getSubject())
            .expiration(Date.from(instant))
            .signWith(jwtSecretKey)
            .compact();
    if (isEncrypted) {
      return SecurityUtil.encrypt(token, secretKey);
    }
    return token;
  }

  public static boolean isValidJWTToken(String token, boolean isEncrypted, String secretKey) {
    try {
      if (isEncrypted) {
        token = SecurityUtil.decrypt(token, secretKey);
      }
      SecretKey jwtSecretKey =
          Keys.hmacShaKeyFor(
              Base64.getDecoder()
                  .decode(SystemUtil.getEnv(NovaLmsAppSecurityEnvConstants.JWT_TOKEN_SECRET_KEY)));
      Jwts.parser().verifyWith(jwtSecretKey).build().parse(token);
      return true;
    } catch (Exception ex) {
      LOGGER.severe("ERROR: " + ex.getMessage());
      return false;
    }
  }

  public static Claims getClaims(String token, boolean isEncrypted, String secretKey) {
    try {
      if (isEncrypted) {
        token = SecurityUtil.decrypt(token, secretKey);
      }
      SecretKey jwtSecretKey =
          Keys.hmacShaKeyFor(
              Base64.getDecoder()
                  .decode(SystemUtil.getEnv(NovaLmsAppSecurityEnvConstants.JWT_TOKEN_SECRET_KEY)));
      return Jwts.parser().verifyWith(jwtSecretKey).build().parseSignedClaims(token).getPayload();

    } catch (Exception ex) {
      LOGGER.severe("ERROR: " + ex.getMessage());
      throw new DynamicAppException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());
    }
  }
}
