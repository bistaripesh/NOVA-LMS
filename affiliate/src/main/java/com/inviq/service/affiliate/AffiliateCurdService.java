package com.inviq.service.affiliate;

import com.inviq.core.service.CrudService;
import com.inviq.dao.affiliate.filter.AffiliateFilter;
import com.inviq.dto.affiliate.request.AffiliateAddRequest;
import com.inviq.dto.affiliate.request.AffiliateDeleteRequest;
import com.inviq.dto.affiliate.request.AffiliateUpdateRequest;

/**
 * @author ripesh created on 12/05/2026
 */
public interface AffiliateCurdService
    extends CrudService<
        AffiliateAddRequest, AffiliateUpdateRequest, AffiliateDeleteRequest, AffiliateFilter> {}
