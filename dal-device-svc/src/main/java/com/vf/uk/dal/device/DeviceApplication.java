package com.vf.uk.dal.device;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vf.uk.dal.common.annotation.Service;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * DeviceApplication, will start the service as SpringBoot Application Added
 * Comments
 **/

@Service
@EnableTransactionManagement
@EnableAsync
@EnableSwagger2 
public class DeviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DeviceApplication.class, args);
	}

	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(50);
		executor.setThreadNamePrefix("DeviceApplication-");
		executor.initialize();
		return executor;
	}
	
	@Bean
	 public Docket deviceApi()
	 {
		 return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()) 
		          .select().apis(RequestHandlerSelectors.basePackage("com.vf.uk.dal.device.controller")).paths(PathSelectors.any()).build();  
	 }
	private ApiInfo apiInfo(){
		return new ApiInfoBuilder()
				.title("Device APIs")
				.description("Device API service operations")
				.version("1.0.0")
				.build();
	}
}