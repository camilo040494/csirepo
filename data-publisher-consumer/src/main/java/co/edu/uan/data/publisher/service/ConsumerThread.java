package co.edu.uan.data.publisher.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import co.edu.uan.data.publisher.properties.ApplicationKafkaProperties;

@Component
@Scope("prototype")
public class ConsumerThread implements Runnable {

	@Autowired
	private ApplicationKafkaProperties applicationKafkaProperties;
	
	@Autowired
	private WriterHDFS writter;

	static final Logger logger = LoggerFactory.getLogger(ConsumerThread.class);

	private KafkaConsumer<String, String> consumer;

	private int idThread;

	public void setIdThread(int idThread) {
		this.idThread = idThread;
	}

	@Override
	public void run() {
		Properties kafkaProps = new Properties();
		kafkaProps.put("bootstrap.servers", applicationKafkaProperties.getServer());
		kafkaProps.setProperty("group.id", "0");
		kafkaProps.setProperty("max.poll.records", "1");
		kafkaProps.setProperty("session.timeout.ms", "30000");
		kafkaProps.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProps.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProps.setProperty("enable.auto.commit", "false");

		consumer = new KafkaConsumer<String, String>(kafkaProps);
		try {
			String kafkaTopic = applicationKafkaProperties.getTopic();
			consumer.subscribe(Collections.singletonList(kafkaTopic));

			logger.info(this.idThread + ">Listening on topic '" + kafkaTopic + "'");

//			consumerTask.setId(idThread);

			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(60000));
				if (records.isEmpty()) {
					logger.info(this.idThread + ">No messages in the last 60 seconds");
				}

				List<String> lines = new ArrayList<String>();
				for (ConsumerRecord<String, String> record : records) {
					String row = record.value().trim();
					lines.add(row);
					logger.debug(this.idThread + ">Message from '" + row + "'");
					consumer.commitSync();
				}
				writter.appendToFile(lines);
			}
		} catch (Exception e) {
			logger.error(this.idThread + ">Error starting kafka consumer", e);
		} finally {
			consumer.close();
		}
	}

	public void shutdown() {
		if (consumer != null)
			consumer.wakeup();
	}
}
