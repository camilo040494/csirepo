package co.edu.uan.data.publisher.service.kafka;

public interface GenericProducerService {
	
	void send(String topic, String row) throws Exception;
	
	void send(String row) throws Exception;
	
}
