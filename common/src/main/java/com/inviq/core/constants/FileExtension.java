package com.inviq.core.constants;

import com.inviq.core.exception.AppException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author ripesh created on 06/05/2026
 */
@Getter
@AllArgsConstructor
public enum FileExtension {
  PDF(".pdf"),
  TEXT(".txt"),
  PNG(".png"),
  JPG(".jpg"),
  JPEG(".jpeg"),
  ;

  private final String extension;

  public static FileExtension getExtension(String extension) {
    return Arrays.stream(FileExtension.values())
        .filter(fileExtension -> fileExtension.extension.equals(extension))
        .findFirst()
        .orElseThrow(
            () ->
                new AppException(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    extension + " file extension not supported"));
  }
}
