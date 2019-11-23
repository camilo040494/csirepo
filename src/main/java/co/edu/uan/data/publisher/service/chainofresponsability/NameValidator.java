package co.edu.uan.data.publisher.service.chainofresponsability;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NameValidator extends HandlerValidator {

	private static final String LOG_IO_READING = "Error when reading the file with message: [{}]";
	private static final String LOG_CHECKING_FILE_TYPE = "Checking file type";
	private static final String LOG_CORRUPTED_FILE = "Could not recognized file extension [{}]";
	private static final String LOG_INVALID_FILE_EXTENSION = "Invalid file extension [{}]";

	@Override
	public boolean validate(File file) {
		log.info(LOG_CHECKING_FILE_TYPE);
		ContentInfoUtil util = new ContentInfoUtil();
		ContentInfo info = null;
		try {
			info = util.findMatch(file.getAbsoluteFile());
		} catch (IOException e) {
			log.error(LOG_IO_READING, e.getCause());
			return false;
		}

		if (Objects.isNull(info)) {
			log.error(LOG_CORRUPTED_FILE, file.getName());
			return false;
		}

		String contentType = info.getContentType().toString();

		if (!checkContentType(contentType.toLowerCase())) {
			log.error(LOG_INVALID_FILE_EXTENSION, contentType.toLowerCase());
			return false;
		}

		return checkNext(file);
	}
	
	private static final String SEPARATOR = "|";
	private static final String PATTERN = "^(%s)$";
	private List<String> supportedExtensions = Lists.newArrayList("csv");
	
	public boolean checkContentType(String contentType) {
		String join = StringUtils.join(supportedExtensions, SEPARATOR);
		String patternString = String.format(PATTERN, join);
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(contentType);

		return matcher.matches();
	}

}
