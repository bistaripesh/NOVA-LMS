package com.inviq.core.entity;

import java.time.Instant;

/**
 * @author ripesh created on 09/04/2025
 */
public interface Entity<E> extends DeepCopyAble<E> {

  String getId();

  void setId(String id);

  String getCreatedBy();

  void setCreatedBy(String createdBy);

  String getModifiedBy();

  void setModifiedBy(String modifiedBy);

  int getVersion();

  void setVersion(int version);

  Instant getCreatedOn();

  void setCreatedOn(Instant createdOn);

  Instant getModifiedOn();

  void setModifiedOn(Instant modifiedOn);
}
