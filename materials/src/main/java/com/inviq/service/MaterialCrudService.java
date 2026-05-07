package com.inviq.service;

import com.inviq.core.service.CrudService;
import com.inviq.dao.filter.MaterialFilter;
import com.inviq.dto.tenset.request.MaterialAddRequest;
import com.inviq.dto.tenset.request.MaterialDeleteRequest;
import com.inviq.dto.tenset.request.MaterialUpdateRequest;

/**
 * @author ripesh created on 05/05/2026
 */
public interface MaterialCrudService
    extends CrudService<
        MaterialAddRequest, MaterialUpdateRequest, MaterialDeleteRequest, MaterialFilter> {}
