package com.jpm.cfs.reportingserver;

import com.jpm.cfs.reportingserver.dto.MultiplicationTableRequest;
import com.jpm.cfs.reportingserver.dto.MultiplicationTableResponse;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class ReportingServerApplicationTests {

    @Autowired
    private RSocketRequester.Builder builder;

    @Test
    void reportingServerTest() {
        RSocketRequester requester = builder
                .transport(TcpClientTransport.create("localhost", 7071));

        Flux<MultiplicationTableResponse> flux = requester.route("multiplication.table")
                .data(MultiplicationTableRequest.create(5, 10))
                .retrieveFlux(MultiplicationTableResponse.class);
        //.subscribe(System.out::println);

        StepVerifier.create(flux)
                .expectNext(MultiplicationTableResponse.create(5, 1, 5))
                .expectNext(MultiplicationTableResponse.create(5, 2, 10))
                .expectNext(MultiplicationTableResponse.create(5, 3, 15))
                .expectNext(MultiplicationTableResponse.create(5, 4, 20))
                .expectNext(MultiplicationTableResponse.create(5, 5, 25))
                .expectNext(MultiplicationTableResponse.create(5, 6, 30))
                .expectNext(MultiplicationTableResponse.create(5, 7, 35))
                .expectNext(MultiplicationTableResponse.create(5, 8, 40))
                .expectNext(MultiplicationTableResponse.create(5, 9, 45))
                .expectNext(MultiplicationTableResponse.create(5, 10, 50))
                .verifyComplete();

    }
}
