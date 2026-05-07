package com.inviq.daoImpl;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.core.entity.PageableUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.LevelSubjectChapterDao;
import com.inviq.dao.LevelSubjectChapterRepository;
import com.inviq.dao.entity.LevelSubjectChapterEntity;
import com.inviq.dao.filter.LevelSubjectChapterFilter;
import com.inviq.dto.response.LevelSubjectChapterSearchResponse;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 07/05/2026
 */
@Repository
public class LevelSubjectChapterDaoImpl implements LevelSubjectChapterDao {

  private final LevelSubjectChapterRepository levelSubjectChapterRepository;

  @Autowired
  public LevelSubjectChapterDaoImpl(LevelSubjectChapterRepository levelSubjectChapterRepository) {
    this.levelSubjectChapterRepository = levelSubjectChapterRepository;
  }

  @Override
  public LevelSubjectChapterEntity insert(LevelSubjectChapterEntity entity) {
    return this.levelSubjectChapterRepository.save(entity);
  }

  @Override
  public LevelSubjectChapterEntity update(LevelSubjectChapterEntity entity) {
    return this.levelSubjectChapterRepository.save(entity);
  }

  @Override
  public int delete(LevelSubjectChapterEntity entity) {
    try {
      this.levelSubjectChapterRepository.delete(entity);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<LevelSubjectChapterEntity> findAll(LevelSubjectChapterFilter filter) {
    return this.levelSubjectChapterRepository.findAll(
        this.getLevelSubjectChapterSpecification(filter));
  }

  @Override
  public long count(LevelSubjectChapterFilter filter) {
    return 0;
  }

  @Override
  public Pageable findWithPagination(LevelSubjectChapterFilter filter) {

    PageRequest pageRequest =
        PageRequest.of(
            PageableUtil.getPageNumberForPagination(filter.getPageNumber()), filter.getPageSize());

    Page<LevelSubjectChapterEntity> page =
        this.levelSubjectChapterRepository.findAll(
            this.getLevelSubjectChapterSpecification(filter), pageRequest);

    List<Response> responses =
        page.getContent().stream()
            .map(
                entity -> {
                  LevelSubjectChapterSearchResponse dto = new LevelSubjectChapterSearchResponse();
                  dto.setId(entity.getId());
                  dto.setChapterName(entity.getChapter());
                  dto.setDescription(entity.getDescription());
                  dto.setLevelSubjectId(entity.getLevelSubject().getId());
                  dto.setLevelId(entity.getLevelSubject().getLevel().getId());
                  dto.setLevelName(entity.getLevelSubject().getLevel().getLevel());
                  dto.setSubjectId(entity.getLevelSubject().getSubject().getId());
                  dto.setSubjectName(entity.getLevelSubject().getSubject().getSubject());
                  return dto;
                })
            .collect(Collectors.toList());

    return new Pageable(
        filter.getPageNumber(), page.getSize(), (int) page.getTotalElements(), responses);
  }

  @Override
  public Optional<LevelSubjectChapterEntity> findOne(LevelSubjectChapterFilter filter) {
    Specification<LevelSubjectChapterEntity> levelSubjectChapterFindOneSpc =
        ((root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();
          if (StringUtil.isNotBlankOrNull(filter.getId())) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
          }
          if (StringUtil.isNotBlankOrNull(filter.getChapterName())) {
            predicates.add(
                cb.equal(cb.lower(root.get("chapter")), filter.getChapterName().toLowerCase()));
          }
          if (StringUtil.isNotBlankOrNull(filter.getLevelSubjectId())) {
            predicates.add(
                cb.equal(root.get("levelSubject").get("id"), filter.getLevelSubjectId()));
          }
          return cb.and(predicates.toArray(new Predicate[0]));
        });

    return this.levelSubjectChapterRepository.findOne(levelSubjectChapterFindOneSpc);
  }

  @Override
  public Optional<LevelSubjectChapterEntity> findById(String id) {
    return this.levelSubjectChapterRepository.findById(id);
  }

  private Specification<LevelSubjectChapterEntity> getLevelSubjectChapterSpecification(
      LevelSubjectChapterFilter filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtil.isNotBlankOrNull(filter.getLevelSubjectId())) {
        predicates.add(cb.equal(root.get("levelSubject").get("id"), filter.getLevelSubjectId()));
      }
      if (StringUtil.isNotBlankOrNull(filter.getChapterName())) {
        predicates.add(
            cb.equal(cb.lower(root.get("chapterName")), filter.getChapterName().toLowerCase()));
      }
      if (StringUtil.isNotBlankOrNull(filter.getSearchParameter())) {
        predicates.add(
            cb.like(cb.lower(root.get("chapterName")), filter.getSearchParameter().toLowerCase()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
