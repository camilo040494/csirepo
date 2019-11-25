package co.edu.uan.data.publisher.properties;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "kafka")
@Data
public class ApplicationKafkaProperties {
  
  private String server;
  private String topic;
  private Set<String> blackListWords;
  
}
