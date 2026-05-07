package com.inviq.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 09/04/2025
 */
@Getter
@Setter
@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BaseEntity<E extends Entity<E>> implements Entity<E> {
  @Id
  @Column(updatable = false)
  protected String id;

  protected Instant createdOn;
  protected String createdBy;

  @Column(name = "last_modified_on")
  protected Instant modifiedOn;

  @Column(name = "last_modified_by")
  protected String modifiedBy;

  protected int version;

  public E deepCopy(E other) {
    other.setId(this.id);
    other.setCreatedOn(this.createdOn);
    other.setCreatedBy(this.createdBy);
    other.setModifiedOn(this.modifiedOn);
    other.setModifiedBy(this.modifiedBy);
    other.setVersion(this.version);
    return other;
  }
}
