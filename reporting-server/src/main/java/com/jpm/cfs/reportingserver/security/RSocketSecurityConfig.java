package com.jpm.cfs.reportingserver.security;

/*import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;*/

//@Configuration
//@EnableRSocketSecurity
public class RSocketSecurityConfig {
/*
    //@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //@Bean
    public RSocketStrategiesCustomizer rSocketStrategiesCustomizer() {
        return strategies -> strategies.encoder(new SimpleAuthenticationEncoder());
    }

    //@Bean
    public PayloadSocketAcceptorInterceptor interceptor(RSocketSecurity rSocketSecurity) {
        return rSocketSecurity
                .authorizePayload(authorize -> authorize
                        //.setup().permitAll()
                        .setup().hasRole("TRUSTED_CLIENT")
                        .anyRequest().permitAll()

                )
                .simpleAuthentication(Customizer.withDefaults())
                .build();
    }*/
}
