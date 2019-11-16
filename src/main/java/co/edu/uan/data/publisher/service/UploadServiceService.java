package co.edu.uan.data.publisher.service;

import co.edu.uan.data.publisher.controller.request.UploadDataRequest;
import co.edu.uan.data.publisher.controller.request.UploadFileRequest;

public interface UploadServiceService {
	
	public void uploadFile(UploadFileRequest file);
	
	public void uploadData(UploadDataRequest data);
	
}
