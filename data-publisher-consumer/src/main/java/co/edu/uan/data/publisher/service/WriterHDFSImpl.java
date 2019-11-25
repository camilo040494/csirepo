package co.edu.uan.data.publisher.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import co.edu.uan.data.publisher.properties.ApplicationHDFSProperties;
import co.edu.uan.data.publisher.properties.ApplicationKafkaProperties;
import lombok.extern.slf4j.Slf4j;

@Component
@Scope("prototype")
@Slf4j
public class WriterHDFSImpl implements WriterHDFS {

	private FileSystem fileSystem;
	private Path destPath;
	
	@Autowired
	private ApplicationHDFSProperties applicationHdfsProperties;
	
	@Autowired
	private ApplicationKafkaProperties applicationKafkaProperties;

	@PostConstruct
	public void init() {
        try {
            Configuration conf = new Configuration();
            conf.setBoolean("dfs.support.append", true);
            conf.set("fs.defaultFS", applicationHdfsProperties.getServer());
            fileSystem = FileSystem.get(conf);
        } catch (IOException ex) {
            log.error("Error occurred while configuring FileSystem");
        }
		
	    destPath = new Path(String.format(applicationHdfsProperties.getDestinationFile(), applicationKafkaProperties.getTopic()));
	}
	
	public String appendToFile(List<String> content) {

	    try {
			if (!fileSystem.exists(destPath)) {
				log.error("File doesn't exist");
			    return "Failure";
			}
		} catch (IOException e1) {
			log.error("File doesn't exist");
			return "Failure";
		}

	    Boolean isAppendable = Boolean.valueOf(fileSystem.getConf().get("dfs.support.append"));

	    if(isAppendable) {
	        try (FSDataOutputStream fs_append = fileSystem.append(destPath);
	        		PrintWriter writer = new PrintWriter(fs_append)){
	        	for (String string : content) {
	        		writer.append(string);					
				}
	        	writer.flush();
	        	fs_append.hflush();
	        	
	        } catch (IOException e) {
	        	log.error("An error happen when trying to write into the file");
			}
	        return "Success";
	    }
	    else {
	        log.error("Please set the dfs.support.append property to true");
	        return "Failure";
	    }
	}

}
