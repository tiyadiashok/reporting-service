package com.jpm.cfs.reportingserver.service;

import com.jpm.cfs.reportingserver.dto.MultiplicationTableDto;
import com.jpm.cfs.reportingserver.dto.MultiplicationTableRequest;
import com.jpm.cfs.reportingserver.dto.MultiplicationTableResponse;
import com.jpm.cfs.reportingserver.entity.MultiplicationTable;
import com.jpm.cfs.reportingserver.repository.MultiplicationTableRepository;
import com.jpm.cfs.reportingserver.uitl.EntityDtoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
public class MathService {

    @Autowired
    private MultiplicationTableRepository multiplicationTableRepository;

    public Flux<MultiplicationTableResponse> multiplicationTable(MultiplicationTableRequest request) {
        return Flux.range(1, request.getLimit())
                //.delayElements(Duration.ofSeconds(1))
                .map(i -> MultiplicationTableResponse.create(request.getNumber(), i, request.getNumber() * i))
                .doOnNext(response -> log.info("Sending response: {}", response));
    }

    public Flux<MultiplicationTable> all() {
        return multiplicationTableRepository.findAll();
                //.delayElements(Duration.ofSeconds(1));
    }

    public Flux<MultiplicationTable> findByNumber(int number) {
        return multiplicationTableRepository.findByNumber(number);
    }

    public Flux<MultiplicationTable> createMultiplicationTable(Flux<MultiplicationTable> multiplicationTableFlux) {
        return multiplicationTableRepository.saveAll(multiplicationTableFlux);
    }
}
