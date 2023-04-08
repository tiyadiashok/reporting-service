package com.jpm.cfs.reportingserver.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.redisson.api.RAtomicLongReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@Slf4j
public class RedisSpringApplicationTest {


    @Autowired
    private RedissonReactiveClient client;

    @RepeatedTest(3)
    void redissonTest() {
        RAtomicLongReactive atomicLong = client.getAtomicLong("user:1:visit");
        long before = System.currentTimeMillis();
        Mono<Void> mono = Flux.range(1, 500_000)
                .flatMap(i -> atomicLong.incrementAndGet())
                .then();

        StepVerifier.create(mono)
                .verifyComplete();
        long after = System.currentTimeMillis();
        log.info("Time taken: {} ms", after - before);
    }
}
