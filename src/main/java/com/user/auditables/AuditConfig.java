package com.user.auditables;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditingAuthroizer")
public class AuditConfig {

	@Bean
	AuditorAwareImpl auditingAuthroizer(){
		return new AuditorAwareImpl();
	}
}
