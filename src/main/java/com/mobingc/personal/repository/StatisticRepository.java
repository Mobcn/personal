package com.mobingc.personal.repository;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.model.entity.Statistic;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 文章统计数据表 数据访问类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Repository
public interface StatisticRepository extends BaseRepository<Statistic, Long> {
}