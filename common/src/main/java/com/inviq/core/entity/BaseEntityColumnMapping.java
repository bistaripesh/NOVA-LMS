package com.inviq.core.entity;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 28/04/2026
 */
@Getter
@AllArgsConstructor
public enum BaseEntityColumnMapping implements ColumnMapping {
  ID("id", "id", "Id"),
  CREATED_ON("created_on", "createdOn", "Created On"),
  CREATED_BY("created_by", "createdBy", "Created By"),
  MODIFIED_ON("modified_on", "modifiedOn", "Modified On"),
  ;

  private final String columnName;
  private final String variableName;
  private final String displayName;

  public static String getEntityColumnName(String columnName) {
    return Arrays.stream(BaseEntityColumnMapping.values())
        .filter(colMap -> colMap.columnName.equals(columnName))
        .findFirst()
        .map(colMap -> colMap.columnName)
        .orElse(BaseEntityColumnMapping.CREATED_ON.columnName);
  }

  public static String getVariableName(String variableName) {
    return Arrays.stream(BaseEntityColumnMapping.values())
        .filter(colMap -> colMap.variableName.equals(variableName))
        .findFirst()
        .map(colMap -> colMap.variableName)
        .orElse(BaseEntityColumnMapping.CREATED_ON.variableName);
  }
}
