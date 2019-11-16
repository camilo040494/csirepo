package co.edu.uan.data.publisher.controller.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessRequest {
	
	private Date initialDate, finalDate;
	private boolean includeUploaders;
	private String urlToPost;
	
}
