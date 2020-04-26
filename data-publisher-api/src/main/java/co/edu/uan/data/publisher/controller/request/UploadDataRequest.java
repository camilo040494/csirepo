package co.edu.uan.data.publisher.controller.request;

import org.wololo.geojson.Point;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadDataRequest {
	
	protected String username;
	protected Point location;
	
}
