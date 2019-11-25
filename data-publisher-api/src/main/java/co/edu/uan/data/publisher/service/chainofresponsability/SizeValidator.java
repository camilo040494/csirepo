package co.edu.uan.data.publisher.service.chainofresponsability;

import java.io.File;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SizeValidator extends HandlerValidator {

	private static final String LOG_VALIDATING_FILE_SIZE = "Validating file size";
	private static final String LOG_ATTACHED_FILE_TOO_LONG = "Attached file too long";
	
	private static final int FILE_SIZE_IN_GB = 3;

	@Override
	public boolean validate(File file) {
		log.info(LOG_VALIDATING_FILE_SIZE);
		long size = 0L;
		size = file.length() / (1024 * 1024);
		if (size >= FILE_SIZE_IN_GB * 1000) {
			log.error(LOG_ATTACHED_FILE_TOO_LONG);
			return false;
		}

		return checkNext(file);
	}

}
