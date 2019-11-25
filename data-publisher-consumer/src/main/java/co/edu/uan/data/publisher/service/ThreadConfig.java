package co.edu.uan.data.publisher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadConfig {

	@Autowired
	private Environment env;

	@Bean
	public TaskExecutor threadPoolTaskExecutor() {

		int numConsumers = Integer.valueOf(env.getProperty("kafka.numConsumers", "1"));

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(numConsumers);
		executor.setMaxPoolSize(numConsumers);
		executor.setThreadNamePrefix("default_task_executor_thread");
		executor.initialize();

		return executor;
	}

}