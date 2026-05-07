package com.inviq.core.dto.request.crud;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 30/04/2026
 */
@Getter
@Setter
public abstract class BulkRequest<Req extends Request> implements Request {

  private List<Req> items;

  public BulkRequest() {
    this.items = new ArrayList<>();
  }

  @Override
  public void validateRequest() {
    items.forEach(Request::validateRequest);
  }
}
