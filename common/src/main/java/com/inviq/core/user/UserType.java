package com.inviq.core.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.boot.internal.Abstract;

/**
 * @author ripesh created on 17/05/2026
 */
@Getter
@AllArgsConstructor
public enum UserType {
  ADMIN("ADMIN", "Admin"),
  USER("USER", "User"),
  WRITER("WRITER", "Writer"),
  ANONYMOUS("ANONYMOUS", "Anonymous");

  private final String code;
  private final String displayName;


  public static UserType getByCode(String code) {
    return UserType.valueOf(code.toUpperCase());
  }

}
