package com.inviq.serviceImpl.fonepay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.constants.FonePayEndpoints;
import com.inviq.constants.FonePayEnvConstants;
import com.inviq.core.exception.DynamicAppException;
import com.inviq.core.util.StringUtil;
import com.inviq.core.util.SystemUtil;
import com.inviq.dto.fonepay.request.FonePayBankListRequest;
import com.inviq.dto.fonepay.request.FonePayIntentQRRequest;
import com.inviq.dto.fonepay.request.FonePayLoginRequest;
import com.inviq.dto.fonepay.response.FonePayBankListResponse;
import com.inviq.dto.fonepay.response.FonePayIntentQRResponse;
import com.inviq.dto.fonepay.response.FonePayLoginResponse;
import com.inviq.service.fonepay.FonePayService;
import com.inviq.util.FonePaySignatureUtil;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;

/**
 * @author ripesh created on 14/05/2026
 */
public class FonePayServiceImpl implements FonePayService {

  @Override
  public FonePayLoginResponse oAuthLogin(FonePayLoginRequest request) throws Exception {
    String jsonBody = new ObjectMapper().writeValueAsString(request).replace(" ", "");
    String basicAuth =
        Base64.getEncoder()
            .encodeToString(
                (request.getUsername() + ":" + request.getPassword())
                    .getBytes(StandardCharsets.UTF_8));

    String signature = FonePaySignatureUtil.sign(jsonBody);

    String LOGIN_URL =
        SystemUtil.getEnv(FonePayEnvConstants.FONE_PAY_BASE_URL) + FonePayEndpoints.LOGIN;
    HttpRequest httpRequest =
        HttpRequest.newBuilder()
            .uri(URI.create(LOGIN_URL))
            .timeout(Duration.ofSeconds(15))
            .header("Content-Type", "application/json")
            .header("Authorization", "Basic " + basicAuth)
            .header("Signature", signature)
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();

    HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15)).build();
    HttpResponse<String> response =
        httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    return switch (response.statusCode()) {
      case 200 -> new ObjectMapper().readValue(response.body(), FonePayLoginResponse.class);
      case 401 -> throw new DynamicAppException("Invalid Credentials");
      case 403 -> throw new DynamicAppException("Access Denied");
      case 404 -> throw new DynamicAppException("Not Found");
      case 500 -> throw new DynamicAppException("Internal Server Error");
      default -> throw new DynamicAppException(response.body());
    };
  }

  @Override
  public FonePayBankListResponse getBankList(FonePayBankListRequest request) throws Exception {
    String signature = FonePaySignatureUtil.sign("");
    String bankListUrl =
        SystemUtil.getEnv(FonePayEnvConstants.FONE_PAY_BASE_URL) + FonePayEndpoints.GET_BANK_LIST;

    HttpRequest.Builder httpRequestBuilder =
        HttpRequest.newBuilder()
            .uri(
                URI.create(
                    bankListUrl
                        + "?paymentMode="
                        + request.getPaymentMode().getCode()
                        + "&mobileNo="
                        + request.getMobileNumber()))
            .timeout(Duration.ofSeconds(15))
            .header(
                "Authorization",
                request.getBearerToken().startsWith("Bearer ")
                    ? request.getBearerToken()
                    : "Bearer " + request.getBearerToken())
            .header("signature", signature)
            .header("paymentMode", request.getPaymentMode().getCode())
            .GET();

    if (StringUtil.isNotBlankOrNull(request.getMobileNumber())) {
      httpRequestBuilder.header("mobileNo", request.getMobileNumber());
    }

    HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15)).build();
    HttpResponse<String> response =
        httpClient.send(httpRequestBuilder.build(), HttpResponse.BodyHandlers.ofString());

    return switch (response.statusCode()) {
      case 200 -> new ObjectMapper().readValue(response.body(), FonePayBankListResponse.class);
      case 401 -> throw new DynamicAppException("Invalid Credentials");
      case 403 -> throw new DynamicAppException("Access Denied");
      case 404 -> throw new DynamicAppException("Not Found");
      case 500 -> throw new DynamicAppException("Internal Server Error");
      default -> throw new DynamicAppException(response.body());
    };
  }

  @Override
  public FonePayIntentQRResponse getIntentQR(FonePayIntentQRRequest request) throws Exception {

    String jsonBody = new ObjectMapper().writeValueAsString(request).replace(" ", "");
    String signature = FonePaySignatureUtil.sign(jsonBody);

    String fonePayIntentQRUrl =
        SystemUtil.getEnv(FonePayEnvConstants.FONE_PAY_BASE_URL) + FonePayEndpoints.INTENT_QR;

    HttpRequest httpRequest =
        HttpRequest.newBuilder()
            .uri(URI.create(fonePayIntentQRUrl))
            .timeout(Duration.ofSeconds(15))
            .header("Content-Type", "application/json")
            .header(
                "Authorization",
                request.getBearerToken().startsWith("Bearer ")
                    ? request.getBearerToken()
                    : "Bearer " + request.getBearerToken())
            .header("Signature", signature)
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();

    HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15)).build();
    HttpResponse<String> response =
        httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    return switch (response.statusCode()) {
      case 202 -> new ObjectMapper().readValue(response.body(), FonePayIntentQRResponse.class);
      case 401 -> throw new DynamicAppException("Invalid Credentials");
      case 403 -> throw new DynamicAppException("Access Denied");
      case 404 -> throw new DynamicAppException("Not Found");
      case 500 -> throw new DynamicAppException("Internal Server Error");
      default -> throw new DynamicAppException(response.body());
    };
  }
}
