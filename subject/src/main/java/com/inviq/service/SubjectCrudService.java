package com.inviq.service;

import com.inviq.core.service.CrudService;
import com.inviq.dao.filter.SubjectFilter;
import com.inviq.dto.tenset.request.SubjectAddRequest;
import com.inviq.dto.tenset.request.SubjectDeleteRequest;
import com.inviq.dto.tenset.request.SubjectUpdateRequest;

/**
 * @author ripesh created on 05/05/2026
 */
public interface SubjectCrudService
    extends CrudService<
        SubjectAddRequest, SubjectUpdateRequest, SubjectDeleteRequest, SubjectFilter> {}
