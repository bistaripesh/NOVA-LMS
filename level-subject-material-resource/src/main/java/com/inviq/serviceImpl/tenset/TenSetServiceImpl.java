package com.inviq.serviceImpl.tenset;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.entity.exception.DatabaseExceptionManager;
import com.inviq.core.exception.AppException;
import com.inviq.core.exception.DynamicAppException;
import com.inviq.dao.tenset.TenSetDao;
import com.inviq.dao.tenset.entity.TenSetEntity;
import com.inviq.dao.tenset.filter.TenSetFilter;
import com.inviq.dto.tenset.request.TenSetAddRequest;
import com.inviq.dto.tenset.request.TenSetDeleteRequest;
import com.inviq.dto.tenset.request.TenSetUpdateRequest;
import com.inviq.dto.tenset.response.TenSetDetailSearchResponse;
import com.inviq.dto.tenset.response.TenSetSearchResponse;
import com.inviq.service.tenset.TenSetCrudService;
import com.inviq.serviceImpl.tenset.usecase.TenSetAddUseCase;
import com.inviq.serviceImpl.tenset.usecase.TenSetDeleteUseCase;
import com.inviq.serviceImpl.tenset.usecase.TenSetUpdateUseCase;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 06/05/2026
 */
@Service
public class TenSetServiceImpl implements TenSetCrudService {

  private final TenSetDao tenSetDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  @Autowired
  public TenSetServiceImpl(TenSetDao tenSetDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.tenSetDao = tenSetDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Override
  public Response create(TenSetAddRequest request) {
    return new TenSetAddUseCase(this.tenSetDao, this.auditDao, this.objectMapper).execute(request);
  }

  @Override
  public Response update(TenSetUpdateRequest request) {
    return new TenSetUpdateUseCase(this.tenSetDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Response delete(TenSetDeleteRequest request) {
    return new TenSetDeleteUseCase(this.tenSetDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Optional<Response> findOne(TenSetFilter request) {
    // TODO CHECK PAYMENT ONCE INTEGRATED
    TenSetEntity tenSetEntity =
        this.tenSetDao
            .findOne(request)
            .orElseThrow(
                () -> new AppException(DatabaseExceptionManager.DatabaseError.RECORD_NOT_FOUND));

    TenSetDetailSearchResponse dto = new TenSetDetailSearchResponse();
    dto.setId(tenSetEntity.getId());
    dto.setTitle(tenSetEntity.getTitle());
    dto.setYear(tenSetEntity.getYear());

    try (PDDocument document =
        Loader.loadPDF(Files.readAllBytes(Path.of(tenSetEntity.getPdfUrl())))) {
      PDFRenderer renderer = new PDFRenderer(document);
      BufferedImage image = renderer.renderImageWithDPI(request.getBookPageNumber() - 1, 150);
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      ImageIO.write(image, "png", byteArrayOutputStream);
      dto.setPdfByte(byteArrayOutputStream.toByteArray());
      dto.setBookTotalPageNumber(document.getNumberOfPages());

    } catch (Exception ex) {
      throw new DynamicAppException(ex.getMessage());
    }
    return Optional.of(dto);
  }

  @Override
  public List<Response> findAll(TenSetFilter request) {
    return this.tenSetDao.findAll(request).stream()
        .map(
            entity -> {
              TenSetSearchResponse dto = new TenSetSearchResponse();
              dto.setId(entity.getId());
              dto.setTitle(entity.getTitle());
              dto.setYear(entity.getYear());
              return (Response) dto;
            })
        .toList();
  }

  @Override
  public Pageable findWithPagination(TenSetFilter tenSetFilter) {
    return this.tenSetDao.findWithPagination(tenSetFilter);
  }
}
