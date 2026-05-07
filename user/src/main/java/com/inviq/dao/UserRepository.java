package com.inviq.dao;

import com.inviq.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 15/04/2026
 */
@Repository
public interface UserRepository
    extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {}
