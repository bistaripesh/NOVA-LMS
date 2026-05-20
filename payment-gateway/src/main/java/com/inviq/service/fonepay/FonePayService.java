package com.inviq.service.fonepay;

import com.inviq.dto.fonepay.request.FonePayBankListRequest;
import com.inviq.dto.fonepay.request.FonePayIntentQRRequest;
import com.inviq.dto.fonepay.request.FonePayLoginRequest;
import com.inviq.dto.fonepay.request.FonePayPaymentStatusRequest;
import com.inviq.dto.fonepay.response.FonePayBankListResponse;
import com.inviq.dto.fonepay.response.FonePayIntentQRResponse;
import com.inviq.dto.fonepay.response.FonePayLoginResponse;
import com.inviq.dto.fonepay.response.FonePayPaymentStatusResponse;

/**
 * @author ripesh created on 14/05/2026
 */
public interface FonePayService {

  FonePayLoginResponse oAuthLogin(FonePayLoginRequest request) throws Exception;

  FonePayBankListResponse getBankList(FonePayBankListRequest request) throws Exception;

  FonePayIntentQRResponse getIntentQR(FonePayIntentQRRequest request) throws Exception;

  FonePayPaymentStatusResponse getPaymentStatus(FonePayPaymentStatusRequest request) throws Exception;

}
