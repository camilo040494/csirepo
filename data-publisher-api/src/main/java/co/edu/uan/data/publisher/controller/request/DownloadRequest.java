package co.edu.uan.data.publisher.controller.request;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class DownloadRequest {
	
	private int resultId;
	private DownloadTypeEnum downloadType;
	
}
