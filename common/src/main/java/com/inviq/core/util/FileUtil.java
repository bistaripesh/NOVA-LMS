package com.inviq.core.util;

import com.inviq.core.constants.FileExtension;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.springframework.web.multipart.MultipartFile;

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

  public static FileExtension getFileExtension(MultipartFile file) throws Exception {
    Tika tika = new Tika();
    String mimeType = tika.detect(file.getInputStream(), file.getOriginalFilename());
    MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
    MimeType mime = allTypes.forName(mimeType);

    return FileExtension.getExtension(mime.getExtension());
  }
}
