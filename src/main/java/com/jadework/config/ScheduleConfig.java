package com.jadework.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Copyright: Copyright (c) 2016,${year},
 * @Description: ${todo}
 */

@Configuration
public class ScheduleConfig {

    /**
     * config Scheduler
     * @return
     */
    @Bean(name = "uniScheduler")
    public Scheduler getScheduler() {
        Scheduler scheduler = null;

        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return scheduler;
    }
}
