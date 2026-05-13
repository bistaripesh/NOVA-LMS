package com.inviq.dao.affiliate;

import com.inviq.core.entity.BaseDao;
import com.inviq.dao.affiliate.entity.AffiliateEntity;
import com.inviq.dao.affiliate.filter.AffiliateFilter;
import org.springframework.stereotype.Repository;

/**
 * @author ripesh created on 11/05/2026
 */
@Repository
public interface AffiliateDao extends BaseDao<AffiliateEntity, AffiliateFilter> {}
