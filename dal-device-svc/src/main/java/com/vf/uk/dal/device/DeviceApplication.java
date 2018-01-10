package com.vf.uk.dal.device;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vf.uk.dal.common.annotation.Service;

/**
 * DeviceApplication, will start the service as SpringBoot Application Added
 * Comments
 **/

@Service
@EnableTransactionManagement
@EnableAsync 
public class DeviceApplication {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(DeviceApplication.class, args);
	}

	@Bean
	/**
	 * 
	 * @return
	 */
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(50);
		executor.setThreadNamePrefix("DeviceApplication-");
		executor.initialize();
		return executor;
	}
	
	
}