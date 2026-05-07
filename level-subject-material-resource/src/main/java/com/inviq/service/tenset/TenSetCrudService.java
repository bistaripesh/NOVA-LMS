package com.inviq.service.tenset;

import com.inviq.core.service.CrudService;
import com.inviq.dao.tenset.filter.TenSetFilter;
import com.inviq.dto.tenset.request.TenSetAddRequest;
import com.inviq.dto.tenset.request.TenSetDeleteRequest;
import com.inviq.dto.tenset.request.TenSetUpdateRequest;

/**
 * @author ripesh created on 06/05/2026
 */
public interface TenSetCrudService
    extends CrudService<TenSetAddRequest, TenSetUpdateRequest, TenSetDeleteRequest, TenSetFilter> {}
