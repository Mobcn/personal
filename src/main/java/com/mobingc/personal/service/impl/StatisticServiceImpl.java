package com.mobingc.personal.service.impl;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.model.entity.Statistic;
import com.mobingc.personal.repository.StatisticRepository;
import com.mobingc.personal.service.StatisticService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章统计数据表 服务实现类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;

    public StatisticServiceImpl(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @Override
    public BaseRepository<Statistic, Long> getRepository() {
        return this.statisticRepository;
    }

}
