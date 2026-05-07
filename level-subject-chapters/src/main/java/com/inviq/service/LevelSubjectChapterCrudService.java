package com.inviq.service;

import com.inviq.core.service.CrudService;
import com.inviq.dao.filter.LevelSubjectChapterFilter;
import com.inviq.dto.request.LevelSubjectChapterAddRequest;
import com.inviq.dto.request.LevelSubjectChapterDeleteRequest;
import com.inviq.dto.request.LevelSubjectChapterUpdateRequest;

/**
 * @author ripesh created on 07/05/2026
 */
public interface LevelSubjectChapterCrudService
    extends CrudService<
        LevelSubjectChapterAddRequest,
        LevelSubjectChapterUpdateRequest,
        LevelSubjectChapterDeleteRequest,
        LevelSubjectChapterFilter> {}
