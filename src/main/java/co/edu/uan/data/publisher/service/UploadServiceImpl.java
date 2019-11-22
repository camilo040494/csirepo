package co.edu.uan.data.publisher.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.edu.uan.data.publisher.adapter.LoadDataAdapter;
import co.edu.uan.data.publisher.controller.request.UploadFileRequest;
import co.edu.uan.data.publisher.service.chainofresponsability.HandlerValidator;
import co.edu.uan.data.publisher.service.kafka.GenericProducerService;
import co.edu.uan.data.publisher.util.validator.ParserFileUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UploadServiceImpl implements UploadServiceService {

	@Autowired
	private GenericProducerService genericProducer;
	
//	@Autowired
	@Resource(name = "handlerValidatorBean")
	private HandlerValidator handlerValidatorBean;

	@Override
	public void uploadFile(UploadFileRequest file) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		System.out.println("\nBegin: " + sdf.format(new Date()));
		
		File parsedFile = ParserFileUtils.parseContentToFile(file.getData());
		
		boolean validate = handlerValidatorBean.validate(parsedFile);
		if (!validate) {
			//TODO
			throw new RuntimeException();
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(parsedFile))) {
			for (String line = null; StringUtils.isNoneEmpty((line = br.readLine()));) {
				genericProducer.send(line);
			}
		} catch (Exception e) {
			log.error("Error loading file", e);
		}
		
		if (!parsedFile.delete()) {
			log.info("The temporary file created was not deleted on method checkContentFile!");
		}

	}

	@Override
	public void uploadData(LoadDataAdapter data) {
		try {
			genericProducer.send(data.parseToComputable());
		} catch (Exception e) {
			log.error("Error producing data", e);
		}
	}

}
