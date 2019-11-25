package co.edu.uan.data.publisher.service;

import java.util.List;

public interface WriterHDFS {

	public String appendToFile(List<String> content);

}
