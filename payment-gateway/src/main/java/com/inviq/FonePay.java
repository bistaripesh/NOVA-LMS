package com.inviq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.constants.FonePayConstants;
import com.inviq.constants.FonePayEndpoints;
import com.inviq.constants.FonePayEnvConstants;
import com.inviq.core.util.SystemUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;

/**
 * @author ripesh created on 20/05/2026
 */
public class FonePay {

  // ─── Constants ───────────────────────────────────────────────────────────────

  private static final String BASE_URL = SystemUtil.getEnv("FONE_PAY_BASE_URL");
  private static final String LOGIN_URL =
      BASE_URL + FonePayEndpoints.LOGIN;
  private static final String INTENT_QR_URL =
      BASE_URL + FonePayEndpoints.INTENT_QR;

  private static final String USERNAME = SystemUtil.getEnv("FONE_PAY_USER_NAME");
  private static final String PASSWORD = SystemUtil.getEnv("FONE_PAY_PASSWORD");
  private static final String PRIVATE_KEY = SystemUtil.getEnv(FonePayEnvConstants.FONE_PAY_PRIVATE_KEY); // your private key

  // ─── Main ────────────────────────────────────────────────────────────────────

  public static void main(String[] args) throws Exception {
    String token = oAuthLogin();
    System.out.println("Token: " + token);
  }

  // ─── oAuth Login ─────────────────────────────────────────────────────────────

  private static String oAuthLogin() throws Exception {
    String jsonBody = "{\"username\":\"" + USERNAME + "\",\"password\":\"" + PASSWORD + "\"}";

    String basicAuth =
        Base64.getEncoder()
            .encodeToString((USERNAME + ":" + PASSWORD).getBytes(StandardCharsets.UTF_8));

    String signature = sign(jsonBody);

    System.out.println("URL:       " + LOGIN_URL);
    System.out.println("Body:      " + jsonBody);
    System.out.println("BasicAuth: " + basicAuth);
    System.out.println("Signature: " + signature);

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(LOGIN_URL))
            .timeout(Duration.ofSeconds(15))
            .header("Content-Type", "application/json")
            .header("Authorization", "Basic " + basicAuth)
            .header("Signature", signature)
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();

    HttpResponse<String> response =
        HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(15))
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println("Status: " + response.statusCode());
    System.out.println("Body:   " + response.body());

    if (response.statusCode() == 200) {
      // parse accessToken from response
      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = mapper.readTree(response.body());
      return node.get("accessToken").asText();
    }

    throw new RuntimeException("Login failed: " + response.body());
  }

  // ─── Signature ───────────────────────────────────────────────────────────────

  private static String sign(String payload) throws Exception {
    PrivateKey privateKey = getPrivateKey(PRIVATE_KEY);
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
