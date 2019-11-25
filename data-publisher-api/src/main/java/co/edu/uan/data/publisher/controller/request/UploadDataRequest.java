package co.edu.uan.data.publisher.controller.request;

import org.springframework.boot.origin.TextResourceOrigin.Location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadDataRequest {
	
	protected String username;
	protected Location location;
	
}
