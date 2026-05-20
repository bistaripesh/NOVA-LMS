package com.inviq;

import com.inviq.constants.FonePayEnvConstants;
import com.inviq.constants.FonePayPaymentMode;
import com.inviq.constants.FonePayQRType;
import com.inviq.core.util.SystemUtil;
import com.inviq.dto.fonepay.request.FonePayBankListRequest;
import com.inviq.dto.fonepay.request.FonePayIntentQRRequest;
import com.inviq.dto.fonepay.request.FonePayLoginRequest;
import com.inviq.dto.fonepay.request.FonePayPaymentStatusRequest;
import com.inviq.dto.fonepay.response.*;
import com.inviq.serviceImpl.fonepay.FonePayServiceImpl;
import java.math.BigDecimal;

// Bearer
// eyJhbGciOiJkaXIiLCJlbmMiOiJBMTI4R0NNIn0..-4nugCyGMjvqGhzj.6l44eZqRFHoFkVUOhJ7oyWjB2VE9SUh6YgbQm0ESyEuATHWYWQWFoNDDkx62HBXVHm1O729sCX2FQ-CVCeBDSD7rLanORaK7V8olhW2VudK5l0W8iIIXSgmcpyiY_9lXJE8Ub-DoREDacSXRsLoY1fWdi24Nh2peFapr8DcFtaVESbezgh7wAKf7VVkjEOhE4xS6xJW8VeW3LY6z9B7OgrDq6IMuaSec-LUPhAIS83HbeddQ0-bkNG5EZm47iN3zZuyUunFPaHtF7dr57uIbWlwKGG_lb2Ki0oYpOSb2z0Sl2aJxxWMWodCJQW1vIVIfsuOiUZRH09F1DvfjH2S5caraaO7JJpV0-6XQVD1r_-HYAEdTgezYQIFS-YW7.zPNcZuLs1f2bSRCGtjB69w
/**
 * @author ripesh created on 14/05/2026
 */
public class Main {
  public static void main(String[] args) throws Exception {
    FonePayServiceImpl fonePayService = new FonePayServiceImpl();
    FonePayLoginResponse loginResp =
        fonePayService.oAuthLogin(
            new FonePayLoginRequest(
                SystemUtil.getEnv(FonePayEnvConstants.FONE_PAY_USERNAME),
                SystemUtil.getEnv(FonePayEnvConstants.FONE_PAY_PASSWORD)));
    System.out.println(loginResp.getAccessToken());
    FonePayBankListRequest fonePayBankListRequest = new FonePayBankListRequest();
    fonePayBankListRequest.setBearerToken(loginResp.getAccessToken());
    fonePayBankListRequest.setPaymentMode(FonePayPaymentMode.INTENT);
    FonePayBankListResponse fonePayBankListResponse =
        fonePayService.getBankList(fonePayBankListRequest);

    String uniqueRef = "REF" + System.currentTimeMillis();
    FonePayIntentQRRequest fonePayIntentQRRequest = new FonePayIntentQRRequest();
    fonePayIntentQRRequest.setBearerToken(loginResp.getAccessToken());
    fonePayIntentQRRequest.setAmount(BigDecimal.valueOf(1.00));
    fonePayIntentQRRequest.setBillId("BILL-" + System.currentTimeMillis());
    fonePayIntentQRRequest.setTerminalId("2222090019524882");
    fonePayIntentQRRequest.setPaymentMode(FonePayPaymentMode.QR.getCode());
    fonePayIntentQRRequest.setReferenceLabel(uniqueRef);
    fonePayIntentQRRequest.setFonePayQrType(FonePayQRType.INTENT_QR.getCode());

    System.out.println("----UNIQUE---REF");
    System.out.println(uniqueRef);


    FonePayPaymentStatusRequest req= new FonePayPaymentStatusRequest();
    req.setBearerToken(loginResp.getAccessToken());
    req.setTerminalId("2222090019524882");
    req.setReferenceLabel("REF1779290578333");

    FonePayPaymentStatusResponse resp = fonePayService.getPaymentStatus(req);
    System.out.println(resp.getPaymentStatus());

    for (BankDetails response : fonePayBankListResponse.getBankDetails()) {
    //  System.out.println(response.getBankName());
   //   System.out.println(response.getBankCode());
     // System.out.println(response.getBankIcon());
    }

  //  FonePayIntentQRResponse qrResponse = fonePayService.getIntentQR(fonePayIntentQRRequest);

 //  System.out.println(qrResponse.getQrString());
  }
}
