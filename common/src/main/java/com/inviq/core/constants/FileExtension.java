package com.inviq.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 06/05/2026
 */
@Getter
@AllArgsConstructor
public enum FileExtension {
  PDF(".pdf"),
  PNG(".png");

  private final String extension;
}
