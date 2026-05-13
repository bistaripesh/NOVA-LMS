package com.inviq.core.storage;

import com.inviq.core.constants.FileExtension;
import com.inviq.core.constants.NovaLmsEnvConstants;
import com.inviq.core.util.SystemUtil;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import org.springframework.http.MediaType;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

/**
 * @author ripesh created on 11/05/2026
 */
public class ObjectStorageUtil {

  public static byte[] getBytes(BucketType bucketType, String bucketName, String file)
      throws Exception {
    S3Client s3Client = getS3ClientConnection(bucketType);
    ResponseInputStream<GetObjectResponse> response =
        s3Client.getObject(GetObjectRequest.builder().bucket(bucketName).key(file).build());
    return response.readAllBytes();
  }

  public static String getPreSignedUrl(BucketType bucketType, String bucketName, String file) {
    switch (bucketType) {
      case CONTABO -> {
        S3Configuration s3Config = S3Configuration.builder().pathStyleAccessEnabled(true).build();
        String accessKey = SystemUtil.getEnv(NovaLmsEnvConstants.CONTABO_S3_ACCESS_KEY);
        String secretKey = SystemUtil.getEnv(NovaLmsEnvConstants.CONTABO_S3_SECRET_KEY);
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        String bucketUrl = SystemUtil.getEnv(NovaLmsEnvConstants.CONTABO_S3_URL);
        S3Presigner preSignUrl =
            S3Presigner.builder()
                .serviceConfiguration(s3Config)
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .endpointOverride(URI.create(bucketUrl))
                .build();

        GetObjectRequest getObjectRequest =
            GetObjectRequest.builder().bucket(bucketName).key(file).build();

        GetObjectPresignRequest presignRequest =
            GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .getObjectRequest(getObjectRequest)
                .build();

        URL url = preSignUrl.presignGetObject(presignRequest).url();

        preSignUrl.close();
        return url.toString();
      }
      default -> throw new UnsupportedOperationException("NOT SUPPORTED BUCKET TYPE");
    }
  }

  public static boolean delete(BucketType bucketType, String bucketName, String fileName) {
    S3Client s3Client = getS3ClientConnection(bucketType);
    DeleteObjectRequest deleteObjectRequest =
        DeleteObjectRequest.builder().bucket(bucketName).key(fileName).build();
    s3Client.deleteObject(deleteObjectRequest);
    s3Client.close();
    return true;
  }

  public static String upload(
      BucketType bucketType,
      String bucketName,
      byte[] data,
      String locationAndFileName,
      FileExtension fileExtension) {

    S3Client s3Client = getS3ClientConnection(bucketType);

    String key = locationAndFileName + fileExtension.getExtension();

    PutObjectRequest request =
        PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)
            .build();

    s3Client.putObject(request, RequestBody.fromBytes(data));
    s3Client.close();
    return key;
  }

  public static S3Client getS3ClientConnection(BucketType bucketType) {
    switch (bucketType) {
      case CONTABO -> {
        String accessKey = SystemUtil.getEnv(NovaLmsEnvConstants.CONTABO_S3_ACCESS_KEY);
        String secretKey = SystemUtil.getEnv(NovaLmsEnvConstants.CONTABO_S3_SECRET_KEY);
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        String bucketUrl = SystemUtil.getEnv(NovaLmsEnvConstants.CONTABO_S3_URL);
        return S3Client.builder()
            .region(Region.US_EAST_1)
            .endpointOverride(URI.create(bucketUrl))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .forcePathStyle(true)
            .build();
      }
      case AMAZON_S3 -> throw new UnsupportedOperationException("AMAZON S3 NOT SUPPORTED YET");
      default -> throw new UnsupportedOperationException("NOT SUPPORTED BUCKET TYPE");
    }
  }
}
