package com.inviq.controller;

import com.inviq.core.constants.FileExtension;
import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.core.service.FileService;
import com.inviq.dao.tenset.filter.TenSetFilter;
import com.inviq.dto.tenset.request.TenSetAddRequest;
import com.inviq.dto.tenset.request.TenSetDeleteRequest;
import com.inviq.dto.tenset.request.TenSetUpdateRequest;
import com.inviq.service.tenset.TenSetCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ripesh created on 06/05/2026
 */
@RestController
@RequestMapping("/ten-sets")
public class TenSetController {

  private final FileService fileService;
  private final TenSetCrudService tenSetCrudService;

  @Autowired
  public TenSetController(TenSetCrudService tenSetCrudService, FileService fileService) {
    this.tenSetCrudService = tenSetCrudService;
    this.fileService = fileService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<RestResponseBody> createTenSet(
      @RequestPart("data") TenSetAddRequest request, @RequestPart("file") MultipartFile file)
      throws Exception {
    request.setPdfLocation(
        this.fileService.writeFileTemporarily(file.getBytes(), FileExtension.PDF));
    return RestResponse.ok(this.tenSetCrudService.create(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<RestResponseBody> updateTenSet(
      @RequestPart("data") TenSetUpdateRequest request, @RequestPart("file") MultipartFile file)
      throws Exception {
    if (!file.isEmpty())
      request.setPdfLocation(
          this.fileService.writeFileTemporarily(file.getBytes(), FileExtension.PDF));
    return RestResponse.ok(this.tenSetCrudService.update(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RestResponseBody> deleteTenSet(
      @ModelAttribute TenSetDeleteRequest request) {
    return RestResponse.ok(this.tenSetCrudService.delete(request));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER','WRITER')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getTenSets(@ModelAttribute TenSetFilter tenSetFilter) {
    return RestResponse.ok(this.tenSetCrudService.findAll(tenSetFilter));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER','WRITER')")
  @GetMapping("{id}/render/{pageNum}")
  public ResponseEntity<RestResponseBody> getPdf(
      @PathVariable("id") String id, @PathVariable("pageNum") int pageNum) {
    return RestResponse.ok(
        this.tenSetCrudService.findOne(
            new TenSetFilter() {
              {
                setId(id);
                setBookPageNumber(pageNum);
              }
            }));
  }
}
