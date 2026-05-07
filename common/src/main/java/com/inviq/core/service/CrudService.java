package com.inviq.core.service;

import com.inviq.core.dto.filter.BaseFilterRequest;
import com.inviq.core.dto.filter.Filter;
import com.inviq.core.dto.request.crud.add.BaseAddRequest;
import com.inviq.core.dto.request.crud.delete.BaseDeleteRequest;
import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * @author ripesh created on 05/04/2025
 */
public interface CrudService<
    AddReq extends BaseAddRequest,
    UpdateReq extends BaseUpdateRequest,
    DelReq extends BaseDeleteRequest,
    Filter extends BaseFilterRequest> {
  Response create(AddReq request);

  Response update(UpdateReq request);

  Response delete(DelReq request);

  Optional<Response> findOne(Filter request);

  List<Response> findAll(Filter request);

  Pageable findWithPagination(Filter filter);
}
