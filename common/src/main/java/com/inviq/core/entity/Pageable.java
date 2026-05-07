package com.inviq.core.entity;

import com.inviq.core.dto.response.Response;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ripesh created on 18/04/2025
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pageable {
  private int pageNumber;
  private int pageSize;
  private int totalCount;
  private List<Response> data;
}
