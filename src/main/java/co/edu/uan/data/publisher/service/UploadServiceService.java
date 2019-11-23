package co.edu.uan.data.publisher.service;

import co.edu.uan.data.publisher.adapter.LoadDataAdapter;
import co.edu.uan.data.publisher.controller.request.UploadFileRequest;

public interface UploadServiceService {
	
	public long uploadFile(UploadFileRequest file);
	
	public long uploadData(LoadDataAdapter data);
	
}