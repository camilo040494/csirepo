package co.edu.uan.data.publisher.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "hdfs")
@Data
public class ApplicationHDFSProperties {
  
  private String server;
  private String destinationFile;
  
}
