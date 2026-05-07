package com.inviq;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * @author ripesh created on 17/04/2026
 */
@Table(name = "audit")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Entity
public class AuditEntity {
  @Id private String id;

  @JdbcTypeCode(SqlTypes.JSON)
  private HashMap<String, Object> newData;

  @JdbcTypeCode(SqlTypes.JSON)
  private HashMap<String, Object> oldData;

  private String entity;

  @Enumerated(EnumType.STRING)
  private AuditType eventType;

  private Instant eventDate;
}
