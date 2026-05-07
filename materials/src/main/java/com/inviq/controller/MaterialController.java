package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.filter.MaterialFilter;
import com.inviq.dto.tenset.request.MaterialAddRequest;
import com.inviq.dto.tenset.request.MaterialDeleteRequest;
import com.inviq.dto.tenset.request.MaterialUpdateRequest;
import com.inviq.service.MaterialCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 05/05/2026
 */
@RestController
@RequestMapping("/materials")
public class MaterialController {

  private final MaterialCrudService materialCrudService;

  @Autowired
  public MaterialController(MaterialCrudService materialCrudService) {
    this.materialCrudService = materialCrudService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<RestResponseBody> createMaterial(
      @RequestBody MaterialAddRequest materialAddRequest) {
    return RestResponse.ok(this.materialCrudService.create(materialAddRequest));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping
  public ResponseEntity<RestResponseBody> updateMaterial(
      @RequestBody MaterialUpdateRequest materialUpdateRequest) {
    return RestResponse.ok(this.materialCrudService.update(materialUpdateRequest));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping
  public ResponseEntity<RestResponseBody> deleteMaterial(
      @ModelAttribute MaterialDeleteRequest materialDeleteRequest) {
    return RestResponse.ok(this.materialCrudService.delete(materialDeleteRequest));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getAllMaterials(
      @ModelAttribute MaterialFilter materialFilter) {
    return RestResponse.ok(this.materialCrudService.findAll(materialFilter));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getMaterialsWithPagination(
      @ModelAttribute MaterialFilter materialFilter) {
    return RestResponse.ok(this.materialCrudService.findWithPagination(materialFilter));
  }
}
