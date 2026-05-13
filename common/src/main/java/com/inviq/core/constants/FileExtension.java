package com.inviq.core.constants;

import com.inviq.core.exception.DynamicAppException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 06/05/2026
 */
@Getter
@AllArgsConstructor
public enum FileExtension {
  PDF(".pdf"),
  TEXT(".txt"),
  PNG(".png");

  private final String extension;

  public static FileExtension getExtension(String extension) {
    return Arrays.stream(FileExtension.values())
        .filter(fileExtension -> fileExtension.extension.equals(extension))
        .findFirst()
        .orElseThrow(() -> new DynamicAppException(extension + " file extension not supported"));
  }
}
