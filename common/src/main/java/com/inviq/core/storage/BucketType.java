package com.inviq.core.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 11/05/2026
 */
@Getter
@AllArgsConstructor
public enum BucketType {
  AMAZON_S3("AMAZON_S3", "Amazon S3"),
  CONTABO("CONTABO", "Contabo"),
  ;

  private final String code;
  private final String displayName;
}
