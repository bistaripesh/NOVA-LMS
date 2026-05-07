package com.inviq.core.service;

import com.inviq.core.dto.request.crud.BulkRequest;
import com.inviq.core.dto.request.crud.Request;
import com.inviq.core.dto.response.Response;
import java.util.List;

/**
 * @author ripesh created on 30/04/2026
 */
public interface BulkCrudService<AddReq extends Request, UpdateReq extends Request> {

  List<Response> create(BulkRequest<AddReq> request);

  List<Response> update(BulkRequest<UpdateReq> request);

  List<Response> delete(List<Request> request);
}
