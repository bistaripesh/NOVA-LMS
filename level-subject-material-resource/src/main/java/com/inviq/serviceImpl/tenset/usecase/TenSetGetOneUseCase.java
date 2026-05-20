package com.inviq.serviceImpl.tenset.usecase;

import com.inviq.constant.TenSetConstant;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.exception.AppException;
import com.inviq.core.service.usecase.AbstractGetOneUseCase;
import com.inviq.dao.tenset.converter.TenSetEntityConverter;
import com.inviq.dao.tenset.entity.TenSetEntity;
import com.inviq.dao.tenset.filter.TenSetFilter;
import com.inviq.dto.tenset.response.TenSetDetailSearchResponse;
import com.inviq.serviceImpl.CacheService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.http.HttpStatus;

public class TenSetGetOneUseCase
    extends AbstractGetOneUseCase<TenSetEntity, TenSetDetailSearchResponse, TenSetFilter> {

  private final CacheService cacheService;

  public TenSetGetOneUseCase(
      BaseDao<TenSetEntity, TenSetFilter> entityDao, CacheService cacheService) {

    super(entityDao);
    this.cacheService = cacheService;
  }

  @Override
  protected TenSetDetailSearchResponse prepareResponse(TenSetEntity entity, TenSetFilter filter) {

    TenSetEntityConverter converter = new TenSetEntityConverter();

    TenSetDetailSearchResponse response =
        (TenSetDetailSearchResponse) converter.getResponse(entity);

    byte[] pdfBytes = cacheService.getByteData(response.getId());

    if (pdfBytes == null) {

      try {

        pdfBytes = Files.readAllBytes(Path.of(entity.getPdfUrl()));

      } catch (IOException e) {

        throw new AppException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), e.getMessage());
      }

      byte[] finalPdfBytes = pdfBytes;

      new Thread(
              () ->
                  cacheService.addByteData(
                      response.getId(), finalPdfBytes, TenSetConstant.TEN_SET_CACHE_EXPIRY))
          .start();
    }

    try (PDDocument document = Loader.loadPDF(pdfBytes)) {

      PDFRenderer renderer = new PDFRenderer(document);

      BufferedImage image = renderer.renderImageWithDPI(filter.getBookPageNumber() - 1, 150);

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      ImageIO.write(image, "png", outputStream);

      response.setPdfByte(outputStream.toByteArray());

      response.setBookTotalPageNumber(document.getNumberOfPages());

    } catch (Exception ex) {

      throw new AppException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), ex.getMessage());
    }

    return response;
  }
}
