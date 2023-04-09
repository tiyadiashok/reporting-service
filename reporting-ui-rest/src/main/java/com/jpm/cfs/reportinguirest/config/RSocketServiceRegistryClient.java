package com.jpm.cfs.reportinguirest.config;

import com.jpm.cfs.reportinguirest.dto.RSocketServerInstance;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RSocketServiceRegistryClient {

    @Autowired
    private EurekaClient eurekaClient;

    public List<RSocketServerInstance> getRSocketServerInstances(String serviceName) {
        log.info("Getting RSocket server instances for service: {}", serviceName);
        log.info("Applications: {}", eurekaClient.getApplications().getRegisteredApplications());
        return eurekaClient.getApplication(serviceName).getInstances().stream()
                .map(instanceInfo -> new RSocketServerInstance(instanceInfo.getIPAddr(), instanceInfo.getPort() + 1))
                .collect(Collectors.toList());
    }

}
