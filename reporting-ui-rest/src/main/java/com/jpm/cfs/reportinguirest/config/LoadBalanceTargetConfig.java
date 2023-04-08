package com.jpm.cfs.reportinguirest.config;

import com.jpm.cfs.reportinguirest.dto.RSocketServerInstance;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.transport.ClientTransport;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class LoadBalanceTargetConfig {

    @Autowired
    private RSocketServiceRegistryClient rSocketServiceRegistryClient;

    @Bean
    public Flux<List<LoadbalanceTarget>> targetFlux() {
        return Flux.from(targets());
    }

    private Mono<List<LoadbalanceTarget>> targets() {
        return Mono.fromSupplier(() -> rSocketServiceRegistryClient.getRSocketServerInstances("reporting-server")
                .stream()
                .map(server -> LoadbalanceTarget.from(key(server), transport(server)))
                .collect(Collectors.toList()));
    }

    private String key(RSocketServerInstance server) {
        return server.getHost() + ":" + server.getPort();
    }

    private ClientTransport transport(RSocketServerInstance server) {
        return TcpClientTransport.create(
                TcpClient.create().host(server.getHost()).port(server.getPort()).secure()
        );
    }


}
