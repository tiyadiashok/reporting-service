package com.jpm.cfs.reportinguirest.config;

import io.rsocket.core.Resume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketConnectorConfigurer;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
@Slf4j
public class RSocketClientConfig {

    @Bean
    public RSocketConnectorConfigurer connectorConfigurer() {
        return connector -> connector.reconnect(retryStrategy());
                //.resume(resumeStrategy());
    }

    /**
     * Retry establishing fresh connection forever with a 2 seconds delay between retries
     * This is needed because the RSocket server is started after the RSocket client
     * Note: Existing connections are not affected by this strategy
     */
    private Retry retryStrategy() {
        return Retry.fixedDelay(Long.MAX_VALUE, Duration.ofSeconds(2))
                .doBeforeRetry(retrySignal -> log.info("Retrying connection to RSocket server. Attempt: {}", retrySignal.totalRetriesInARow()));
    }

    private Resume resumeStrategy() {
        return new Resume()
                .retry(Retry.fixedDelay(2000, Duration.ofSeconds(2))
                        .doBeforeRetry(retrySignal -> log.info("Resume connection to RSocket server. Attempt: {}", retrySignal.totalRetriesInARow())));
    }

}
