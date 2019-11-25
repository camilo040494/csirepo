package co.edu.uan.data.publisher.service.kafka;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uan.data.publisher.properties.ApplicationKafkaProperties;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducerServiceImpl implements GenericProducerService {

	//KAFKA values
	private static final int LINGERS_MS = 5;
	private static final String RETRIES_NUMBER = "3";
	private static final String ACKS_NUMBER = "1";
	//KAFKA properties
	private static final String LINGER_MS_PROPERTY = "linger.ms";
	private static final String RETRIES_PROPERTY = "retries";
	private static final String ACKS_PROPERTY = "acks";
	private static final String VALUE_SERIALIZER_PROPERTY = "value.serializer";
	private static final String KEY_SERIALIZER_PROPERTY = "key.serializer";
	private static final String BOOTSTRAP_SERVERS_PROPERTY = "bootstrap.servers";
	
	private static final String KAFKA_STRING_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";

	private Producer<String, String> producer;
	
	@Autowired
	private ApplicationKafkaProperties applicationKafkaProperties;

	@PostConstruct
	private void init() {
		Properties kafkaProperties = new Properties();

		kafkaProperties.put(BOOTSTRAP_SERVERS_PROPERTY, applicationKafkaProperties.getServer());
		kafkaProperties.put(KEY_SERIALIZER_PROPERTY, KAFKA_STRING_SERIALIZER);
		kafkaProperties.put(VALUE_SERIALIZER_PROPERTY, KAFKA_STRING_SERIALIZER);
		kafkaProperties.put(ACKS_PROPERTY, ACKS_NUMBER);
		kafkaProperties.put(RETRIES_PROPERTY, RETRIES_NUMBER);
		kafkaProperties.put(LINGER_MS_PROPERTY, LINGERS_MS);

		producer = new KafkaProducer<>(kafkaProperties);
	}

	@Override
	public void send(String topic, String key, String row) throws InterruptedException, ExecutionException {
		ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, row);
		try {
			log.debug("Sending message to topic [{}], payload [{}]", topic, row);
			producer.send(record, (metada, exception) -> {
				if (Objects.nonNull(metada))					
					log.debug("Sending message to topic [{}], payload [{}] was received sucessfully", topic, row);
				else {
					if (Objects.nonNull(exception))
						log.error("Message to topic [{}], payload [{}] failed with exception error [{}]", topic, row, exception.getCause());
				}
			});
			producer.flush();
		} catch (Exception e) {
			log.error("Connection lost to topic [{}], payload [{}]", topic, row);
			init();
			producer.send(record).get();
		}
	}
		
	@Override
	public void send(String key, String row) throws InterruptedException, ExecutionException {
		String topic = applicationKafkaProperties.getTopic();
		send(topic, key, row);
	}

	@PreDestroy
	public void close() {
		producer.close();
	}
}
