package org.mfs.reactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAutoConfiguration
@EnableAspectJAutoProxy
public class AppConfig {

	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
		InterceptorReact.testMethod();
		System.exit(0);
	}

}