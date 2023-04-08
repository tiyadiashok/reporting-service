package com.jpm.cfs.reportinguirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ReportingUiRestApplication {

	static {
		System.setProperty("javax.net.ssl.trustStore", "/Users/tiyadiashok/IdeaProjects/reporting-service/rsocket/ssl-tls/client.truststore");
		System.setProperty("javax.net.ssl.trustStorePassword", "password");
	}

	public static void main(String[] args) {
		SpringApplication.run(ReportingUiRestApplication.class, args);
	}

}
