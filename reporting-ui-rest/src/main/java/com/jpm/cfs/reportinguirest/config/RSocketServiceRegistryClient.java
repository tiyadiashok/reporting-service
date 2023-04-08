package com.jpm.cfs.reportinguirest.config;

import com.jpm.cfs.reportinguirest.dto.RSocketServerInstance;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RSocketServiceRegistryClient {

    @Autowired
    private EurekaClient eurekaClient;

    public List<RSocketServerInstance> getRSocketServerInstances(String serviceName) {
        return eurekaClient.getApplication(serviceName).getInstances().stream()
                .map(instanceInfo -> new RSocketServerInstance(instanceInfo.getIPAddr(), instanceInfo.getPort() + 1))
                .collect(Collectors.toList());
    }

}
