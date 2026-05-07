package com.inviq.service;

import com.inviq.core.service.CrudService;
import com.inviq.dao.filter.LevelSubjectMaterialFilter;
import com.inviq.dto.tenset.request.LevelSubjectMaterialAddRequest;
import com.inviq.dto.tenset.request.LevelSubjectMaterialDeleteRequest;
import com.inviq.dto.tenset.request.LevelSubjectMaterialUpdateRequest;

/**
 * @author ripesh created on 06/05/2026
 */
public interface LevelSubjectMaterialCrudService
    extends CrudService<
        LevelSubjectMaterialAddRequest,
        LevelSubjectMaterialUpdateRequest,
        LevelSubjectMaterialDeleteRequest,
        LevelSubjectMaterialFilter> {}
