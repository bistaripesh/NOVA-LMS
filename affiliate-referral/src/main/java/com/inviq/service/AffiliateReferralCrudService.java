package com.inviq.service;

import com.inviq.core.service.CrudService;
import com.inviq.dao.filter.AffiliateReferralFilter;
import com.inviq.dto.request.AffiliateReferralAddRequest;
import com.inviq.dto.request.AffiliateReferralDeleteRequest;
import com.inviq.dto.request.AffiliateReferralUpdateRequest;

/**
 * @author ripesh created on 13/05/2026
 */
public interface AffiliateReferralCrudService
    extends CrudService<
        AffiliateReferralAddRequest,
        AffiliateReferralUpdateRequest,
        AffiliateReferralDeleteRequest,
        AffiliateReferralFilter> {}
