package com.inviq.dao.entity;

import com.inviq.core.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author ripesh created on 15/04/2026
 */
@Entity
@Table(name = "users")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends BaseEntity<UserEntity> {
  private String firstName;
  private String middleName;
  private String lastName;
  private String email;
  private String password;
  private String role;

  public UserEntity(UserEntity other) {
    this.firstName = other.firstName;
    this.middleName = other.middleName;
    this.lastName = other.lastName;
    this.email = other.email;
    this.password = other.password;
    this.role = other.role;
  }

  public UserEntity(String id) {
    this.id = id;
  }

  @Override
  public UserEntity deepCopy(UserEntity other) {
    other = super.deepCopy(other);
    return new UserEntity(other);
  }
}
