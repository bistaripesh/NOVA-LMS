package com.inviq.core.util;

import com.inviq.core.constants.FileExtension;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author ripesh created on 06/05/2026
 */
public class FileUtil {

  public static String writeFileTemporarily(byte[] fileByte, FileExtension fileExtension)
      throws Exception {

    String prefix = StringUtil.getUUID();
    String suffix = fileExtension.getExtension();

    Path tempFile = Files.createTempFile(prefix, suffix);

    Files.write(tempFile, fileByte);

    return tempFile.toString();
  }

  public static String writeFile(
      byte[] fileByte, String directory, String fileName, FileExtension fileExtension)
      throws Exception {

    String fullFileName = fileName + fileExtension.getExtension();

    Path path = Paths.get(directory, fullFileName);

    Files.createDirectories(path.getParent());

    Files.write(path, fileByte);

    return path.toString();
  }
}
