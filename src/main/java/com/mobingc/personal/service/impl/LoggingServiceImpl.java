package com.mobingc.personal.service.impl;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.model.entity.Logging;
import com.mobingc.personal.repository.LoggingRepository;
import com.mobingc.personal.service.LoggingService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Service
public class LoggingServiceImpl implements LoggingService {

    private final LoggingRepository loggingRepository;

    public LoggingServiceImpl(LoggingRepository loggingRepository) {
        this.loggingRepository = loggingRepository;
    }

    @Override
    public BaseRepository<Logging, Long> getRepository() {
        return this.loggingRepository;
    }

}
