package co.edu.uan.data.publisher.controller.request;

import org.apache.commons.lang3.StringUtils;

import co.edu.uan.data.publisher.adapter.LoadDataAdapter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadDataRequestAdapter extends UploadDataRequest implements LoadDataAdapter {

	private static final String DEFAULT_SEPARATOR = ";";
	private String movil;
	private String movilidadVictima;
	
	@Override
	public String parseToComputable() {
		return StringUtils.join(new String[]{movil, movilidadVictima}, DEFAULT_SEPARATOR);
	}
	
}
