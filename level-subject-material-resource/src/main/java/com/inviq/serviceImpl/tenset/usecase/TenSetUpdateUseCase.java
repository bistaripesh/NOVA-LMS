package com.inviq.serviceImpl.tenset.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.constant.TenSetConstant;
import com.inviq.core.constants.FileExtension;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.exception.DynamicAppException;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.core.util.FileUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.entity.LevelSubjectMaterialEntity;
import com.inviq.dao.tenset.entity.TenSetEntity;
import com.inviq.dao.tenset.filter.TenSetFilter;
import com.inviq.dto.tenset.request.TenSetUpdateRequest;
import com.inviq.dto.tenset.response.TenSetUpdateResponse;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author ripesh created on 06/05/2026
 */
public class TenSetUpdateUseCase
    extends AbstractUpdateUseCase<
        TenSetEntity, TenSetUpdateRequest, TenSetUpdateResponse, TenSetFilter> {

  public TenSetUpdateUseCase(
      BaseDao<TenSetEntity, TenSetFilter> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(TenSetUpdateRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      TenSetUpdateRequest request, TenSetEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(TenSetUpdateRequest request) {
    if (StringUtil.isNotBlankOrNull(request.getPdfLocation())) {
      try {
        request.setPdfLocation(
            FileUtil.writeFile(
                Files.readAllBytes(Path.of(request.getPdfLocation())),
                TenSetConstant.TEN_SET_FILE_PATH,
                StringUtil.getUUID().concat("_").concat(request.getTitle()),
                FileExtension.PDF));
      } catch (Exception ex) {
        throw new DynamicAppException(ex.getMessage());
      }
    }
  }

  @Override
  protected Module getModule() {
    return Module.TEN_SET_MATERIALS;
  }

  @Override
  protected TenSetFilter prepareFilterToFindDuplicateRecord(TenSetUpdateRequest request) {
    TenSetFilter tenSetFilter = new TenSetFilter();
    tenSetFilter.setLevelSubjectMaterialId(request.getLevelSubjectMaterialId());
    tenSetFilter.setTitle(request.getTitle());
    return tenSetFilter;
  }

  @Override
  protected TenSetEntity prepareEntityForUpdate(TenSetEntity entity, TenSetUpdateRequest request) {
    entity.setTitle(request.getTitle());
    entity.setYear(request.getYear());
    entity.setPdfUrl(request.getPdfLocation());
    entity.setLevelSubjectMaterial(
        new LevelSubjectMaterialEntity() {
          {
            setId(request.getLevelSubjectMaterialId());
          }
        });
    return entity;
  }

  @Override
  protected TenSetUpdateResponse prepareResponse(TenSetEntity entityToUpdate) {
    return new TenSetUpdateResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
