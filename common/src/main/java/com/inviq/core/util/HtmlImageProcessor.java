package com.inviq.core.util;

/**
 * @author ripesh created on 17/05/2026
 */
import com.inviq.core.constants.FileExtension;
import com.inviq.core.constants.NovaLmsEnvConstants;
import com.inviq.core.exception.AppException;
import com.inviq.core.exception.manager.ObjectStorageExceptionManager;
import com.inviq.core.storage.BucketType;
import com.inviq.core.storage.ObjectStorageUtil;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlImageProcessor {

  private static final Pattern IMG_PATTERN =
      Pattern.compile(
          "data:image/(png|jpeg|jpg|gif|webp);base64,([A-Za-z0-9+/=\\s]+)",
          Pattern.CASE_INSENSITIVE);
  private static final Pattern IMG_KEY_PATTERN =
      Pattern.compile("src=\"(/[^\"]+\\.(?:png|jpeg|jpg|gif|webp))\"", Pattern.CASE_INSENSITIVE);

  public static String processHtml(String html, String baseLocation) {
    Matcher matcher = IMG_PATTERN.matcher(html);
    StringBuilder updatedHtml = new StringBuilder();

    while (matcher.find()) {
      String extension = matcher.group(1);
      String base64Data = matcher.group(2).replaceAll("\\s+", "");

      try {
        byte[] imageBytes = Base64.getDecoder().decode(base64Data);
        String fileLocation = baseLocation + StringUtil.getUUID();
        String imageUrl = uploadToObjectStorage(fileLocation, imageBytes, "." + extension);
        matcher.appendReplacement(updatedHtml, Matcher.quoteReplacement(imageUrl));
      } catch (IllegalArgumentException e) {
        matcher.appendReplacement(updatedHtml, Matcher.quoteReplacement(matcher.group(0)));
      }
    }

    matcher.appendTail(updatedHtml);
    return updatedHtml.toString();
  }

  private static String uploadToObjectStorage(
      String locationAndFileName, byte[] bytes, String extension) {
    try {
      return ObjectStorageUtil.upload(
          BucketType.CONTABO,
          SystemUtil.getEnv(NovaLmsEnvConstants.CONTABO_S3_BUCKET_NAME),
          bytes,
          locationAndFileName,
          FileExtension.getExtension(extension));
    } catch (Exception e) {
      throw new AppException(
          ObjectStorageExceptionManager.ObjectStorageError.FAILED_TO_UPLOAD_FILE);
    }
  }

  public static String appendBaseUrlToImages(String html) {
    if (StringUtil.isBlankOrNull(html)) return html;

    String bucketName = SystemUtil.getEnv(NovaLmsEnvConstants.CONTABO_S3_BUCKET_NAME);

    Matcher matcher = IMG_KEY_PATTERN.matcher(html);
    StringBuilder updatedHtml = new StringBuilder();

    while (matcher.find()) {
      String key = matcher.group(1); // /question/1cefb908b5674eec92b5701e18f18e86.png
      String presignedUrl = ObjectStorageUtil.getPreSignedUrl(BucketType.CONTABO, bucketName, key);
      matcher.appendReplacement(
          updatedHtml, Matcher.quoteReplacement("src=\"" + presignedUrl + "\""));
    }

    matcher.appendTail(updatedHtml);
    return updatedHtml.toString();
  }
}
