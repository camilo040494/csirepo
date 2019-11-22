package co.edu.uan.data.publisher.service;

import co.edu.uan.data.publisher.adapter.LoadDataAdapter;
import co.edu.uan.data.publisher.controller.request.UploadFileRequest;

public interface UploadServiceService {
	
	public void uploadFile(UploadFileRequest file);
	
	public void uploadData(LoadDataAdapter data);
	
}