package com.inviq.dto.tenset.response;

import com.inviq.core.dto.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ripesh created on 05/05/2026
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LevelSearchResponse extends BaseResponse {
  private String id;
  private String levelName;
}
