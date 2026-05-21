package com.inviq.dao.tenset;

import com.inviq.core.entity.BaseDao;
import com.inviq.dao.tenset.entity.TenSetEntity;
import com.inviq.dao.tenset.filter.TenSetFilter;
import java.util.Optional;

/**
 * @author ripesh created on 06/05/2026
 */
public interface TenSetDao extends BaseDao<TenSetEntity, TenSetFilter> {
  Optional<String> findLevelSubjectIdById(String id);
}
