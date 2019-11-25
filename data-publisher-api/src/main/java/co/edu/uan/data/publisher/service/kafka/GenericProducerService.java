package co.edu.uan.data.publisher.service.kafka;

import java.util.concurrent.ExecutionException;

public interface GenericProducerService {
	
	void send(String topic, String key, String row) throws InterruptedException, ExecutionException;
	
	void send(String key, String row) throws InterruptedException, ExecutionException;
	
}
