package com.inviq.daoImpl;

import com.inviq.core.entity.Pageable;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.UserDao;
import com.inviq.dao.UserRepository;
import com.inviq.dao.entity.UserEntity;
import com.inviq.dao.filter.UserFilter;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 15/04/2026
 */
@Repository
public class UserDaoImpl implements UserDao {

  private final UserRepository userRepository;

  @Autowired
  public UserDaoImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserEntity insert(UserEntity entity) {
    return this.userRepository.save(entity);
  }

  @Override
  public UserEntity update(UserEntity entity) {
    return this.userRepository.save(entity);
  }

  @Override
  public int delete(UserEntity entity) {
    try {
      this.userRepository.delete(entity);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<UserEntity> findAll(UserFilter filter) {
    return List.of();
  }

  @Override
  public long count(UserFilter filter) {
    return 0;
  }

  @Override
  public Pageable findWithPagination(UserFilter filter) {
    return null;
  }

  @Override
  public Optional<UserEntity> findOne(UserFilter filter) {
    Specification<UserEntity> userEntityFindOneSpec =
        (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (StringUtil.isNotBlankOrNull(filter.getId())) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
          }

          if (StringUtil.isNotBlankOrNull(filter.getEmail())) {
            predicates.add(cb.equal(root.get("email"), filter.getEmail()));
          }

          return cb.and(predicates.toArray(new Predicate[0]));
        };

    return this.userRepository.findOne(userEntityFindOneSpec);
  }

  @Override
  public Optional<UserEntity> findById(String id) {
    return this.userRepository.findById(id);
  }
}
