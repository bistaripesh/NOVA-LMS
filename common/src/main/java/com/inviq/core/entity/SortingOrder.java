package com.inviq.core.entity;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 18/04/2025
 */
@AllArgsConstructor
@Getter
public enum SortingOrder {
  ASC("ASC", "Ascending"),
  DESC("DESC", "Descending");
  private final String code;
  private final String description;

  public static SortingOrder getByCode(String code) {
    return Arrays.stream(SortingOrder.values())
        .filter(o -> o.getCode().equalsIgnoreCase(code))
        .findFirst()
        .orElse(ASC);
  }
}
