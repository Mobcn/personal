package com.mobingc.personal.repository;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.model.entity.Logging;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 日志表 数据访问类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Repository
public interface LoggingRepository extends BaseRepository<Logging, Long> {
}