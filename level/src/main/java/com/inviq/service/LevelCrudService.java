package com.inviq.service;

import com.inviq.core.service.CrudService;
import com.inviq.dao.filter.LevelFilter;
import com.inviq.dto.tenset.request.LevelAddRequest;
import com.inviq.dto.tenset.request.LevelDeleteRequest;
import com.inviq.dto.tenset.request.LevelUpdateRequest;

/**
 * @author ripesh created on 05/05/2026
 */
public interface LevelCrudService
    extends CrudService<LevelAddRequest, LevelUpdateRequest, LevelDeleteRequest, LevelFilter> {}
