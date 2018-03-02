package com.vf.uk.dal.device;

import java.util.concurrent.Executor;

import org.elasticsearch.client.RestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vf.uk.dal.common.annotation.Service;
import com.vf.uk.dal.device.config.ElasticsearchRestCient;

/**
 * DeviceApplication, will start the service as SpringBoot Application Added
 * Comments
 * @author
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
	 * @author 
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
	
	@Bean
	public RestClient getRestClientObject() {
		return ElasticsearchRestCient.getClient();
	}
}