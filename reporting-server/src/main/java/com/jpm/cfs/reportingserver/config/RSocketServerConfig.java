package com.jpm.cfs.reportingserver.config;

import io.rsocket.core.Resume;
import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RSocketServerConfig {

    @Bean
    public RSocketServerCustomizer rSocketServerCustomizer() {
        return server -> server.resume(resumeStrategy());
    }

    private Resume resumeStrategy() {
        return new Resume()
                .sessionDuration(Duration.ofMinutes(5));
                //.retry(Retry.fixedDelay(10, Duration.ofSeconds(2)));
    }
}
