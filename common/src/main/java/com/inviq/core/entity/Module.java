package com.inviq.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 17/04/2026
 */
@Getter
@AllArgsConstructor
public enum Module {
  USER("USER", "User"),
  LEVEL("LEVEL", "Level"),
  SUBJECT("SUBJECT", "Subject"),
  LEVEL_SUBJECTS("LEVEL_SUBJECTS", "Level Subjects"),
  MATERIAL("MATERIAL", "Material"),
  LEVEL_SUBJECT_MATERIALS("LEVEL_SUBJECT_MATERIALS", "Level Subject Materials"),
  TEN_SET_MATERIALS("TEN_SET_MATERIALS", "Ten Set Materials"),
  ;

  private final String code;
  private final String displayName;
}
