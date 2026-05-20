package com.inviq.service;

import com.inviq.core.service.CrudService;
import com.inviq.dao.filter.LevelSubjectsFilter;
import com.inviq.dto.request.LevelSubjectsAddRequest;
import com.inviq.dto.request.LevelSubjectsDeleteRequest;
import com.inviq.dto.request.LevelSubjectsUpdateRequest;

/**
 * @author ripesh created on 05/05/2026
 */
public interface LevelSubjectsCrudService
    extends CrudService<
        LevelSubjectsAddRequest,
        LevelSubjectsUpdateRequest,
        LevelSubjectsDeleteRequest,
        LevelSubjectsFilter> {}
