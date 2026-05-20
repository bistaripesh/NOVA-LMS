package com.inviq.serviceImpl.tenset.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.constant.TenSetConstant;
import com.inviq.core.constants.FileExtension;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.exception.AppException;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.core.util.FileUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.entity.LevelSubjectMaterialEntity;
import com.inviq.dao.tenset.entity.TenSetEntity;
import com.inviq.dao.tenset.filter.TenSetFilter;
import com.inviq.dto.tenset.request.TenSetAddRequest;
import com.inviq.dto.tenset.response.TenSetAddResponse;
import org.springframework.http.HttpStatus;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author ripesh created on 06/05/2026
 */
public class TenSetAddUseCase
    extends AbstractAddUseCase<TenSetEntity, TenSetAddRequest, TenSetAddResponse, TenSetFilter> {

  public TenSetAddUseCase(
      BaseDao<TenSetEntity, TenSetFilter> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(TenSetAddRequest request) {
    try {
      request.setPdfLocation(
          FileUtil.writeFile(
              Files.readAllBytes(Path.of(request.getPdfLocation())),
              TenSetConstant.TEN_SET_FILE_PATH,
              StringUtil.getUUID().concat("_").concat(request.getTitle()),
              FileExtension.PDF));
    } catch (Exception ex) {
      throw new AppException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),ex.getMessage());
    }
  }

  @Override
  protected void preValidateRequest(TenSetAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.TEN_SET_MATERIALS;
  }

  @Override
  protected TenSetFilter prepareFilterToFindDuplicateRecord(TenSetAddRequest request) {
    TenSetFilter tenSetFilter = new TenSetFilter();
    tenSetFilter.setLevelSubjectMaterialId(request.getLevelSubjectMaterialId());
    tenSetFilter.setTitle(request.getTitle());
    return tenSetFilter;
  }

  @Override
  protected TenSetEntity prepareEntity(TenSetAddRequest request) {
    TenSetEntity tenSetEntity = new TenSetEntity();
    tenSetEntity.setYear(request.getYear());
    tenSetEntity.setTitle(request.getTitle());
    tenSetEntity.setPdfUrl(request.getPdfLocation());
    tenSetEntity.setLevelSubjectMaterial(
        new LevelSubjectMaterialEntity() {
          {
            setId(request.getLevelSubjectMaterialId());
          }
        });
    return tenSetEntity;
  }

  @Override
  protected TenSetAddResponse prepareResponse(TenSetEntity addedEntity) {
    return new TenSetAddResponse() {
      {
        setId(addedEntity.getId());
      }
    };
  }
}
