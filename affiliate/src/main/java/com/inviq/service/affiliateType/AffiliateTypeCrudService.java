package com.inviq.service.affiliateType;

import com.inviq.core.service.CrudService;
import com.inviq.dao.affiliateType.filter.AffiliateTypeFilter;
import com.inviq.dto.affiliateType.request.AffiliateTypeAddRequest;
import com.inviq.dto.affiliateType.request.AffiliateTypeDeleteRequest;
import com.inviq.dto.affiliateType.request.AffiliateTypeUpdateRequest;

/**
 * @author ripesh created on 12/05/2026
 */
public interface AffiliateTypeCrudService
    extends CrudService<
        AffiliateTypeAddRequest,
        AffiliateTypeUpdateRequest,
        AffiliateTypeDeleteRequest,
        AffiliateTypeFilter> {}
