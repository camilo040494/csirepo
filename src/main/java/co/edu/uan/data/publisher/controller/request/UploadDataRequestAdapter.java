package co.edu.uan.data.publisher.controller.request;

import co.edu.uan.data.publisher.adapter.LoadDataAdapter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadDataRequestAdapter extends UploadDataRequest implements LoadDataAdapter {

	@Override
	public String parseToComputable() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
