package co.edu.uan.data.publisher.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ConsumerLauncher {

	static final Logger logger = LoggerFactory.getLogger(ConsumerLauncher.class);

	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private Environment env;

	@PostConstruct
	public void start() {
		int numConsumers = Integer.valueOf(env.getProperty("kafka.numConsumers", "1"));
		logger.info("Launching " + numConsumers + " kafka consumers");
		for (int i = 0; i <= numConsumers; i++) {
			ConsumerThread consumerThread = applicationContext.getBean(ConsumerThread.class);
			consumerThread.setIdThread(i + 1);
			taskExecutor.execute(consumerThread);
		}
	}
}
