package com.inviq.core.service;

import com.inviq.core.constants.FileExtension;
import com.inviq.core.exception.DynamicAppException;
import com.inviq.core.util.FileUtil;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 06/05/2026
 */
@Service
public class FileService {

  public String writeFileTemporarily(byte[] file, FileExtension fileExtension) {
    try {

      return FileUtil.writeFileTemporarily(file, fileExtension);
    } catch (Exception ex) {
      throw new DynamicAppException(ex.getMessage());
    }
  }
}
