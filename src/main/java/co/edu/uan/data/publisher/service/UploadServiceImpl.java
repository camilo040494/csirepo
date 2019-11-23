package co.edu.uan.data.publisher.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.edu.uan.data.publisher.adapter.LoadDataAdapter;
import co.edu.uan.data.publisher.controller.request.UploadFileRequest;
import co.edu.uan.data.publisher.model.Transaction;
import co.edu.uan.data.publisher.model.TransactionBuilder;
import co.edu.uan.data.publisher.service.chainofresponsability.HandlerValidator;
import co.edu.uan.data.publisher.service.kafka.GenericProducerService;
import co.edu.uan.data.publisher.util.validator.ParserFileUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UploadServiceImpl implements UploadServiceService {

	private static final String ERROR_KAFKA_SERVER = "An error happenned in the comunication with the server with message: [{}]";
	private static final String ERROR_IO_FILE_DELETION = "An IO error happened when trying to delete file [{}] with cause:[{}]";
	private static final String ERROR_TEMP_FILE_DELETION = "An error happened trying to delete temporally file [{}] with message: [{}]";
	private static final String ERROR_UPSTREAMING_DATA = "Error loading data: [{}]";
	private static final String ERROR_READING_FILE = "Error loading file with cause: [{}]";

	@Autowired
	private GenericProducerService genericProducer;
	
	@Autowired
	@Qualifier("handlerValidatorBean")
	private HandlerValidator handlerValidatorBean;

	@Override
	public long uploadFile(UploadFileRequest file) {
		Transaction transaction = TransactionBuilder.createEmptyTransaction();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		log.info("\nBegin: " + sdf.format(new Date()));
		
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
		} catch (IOException e) {
			log.error(ERROR_READING_FILE, e.getCause());
			throw new RuntimeException();
		} catch (ExecutionException | InterruptedException e) {
			log.error(ERROR_KAFKA_SERVER, e.getCause());
			throw new RuntimeException();
		}
		
		try {
			Files.delete(parsedFile.toPath());
		} catch (NoSuchFileException | DirectoryNotEmptyException e) {
			log.error(ERROR_TEMP_FILE_DELETION, parsedFile.toPath(), e.getCause());
			throw new RuntimeException();
		} catch (IOException e) {
			log.error(ERROR_IO_FILE_DELETION, parsedFile.toPath(), e.getCause());
		}
		
		return transaction.getUuid();
	}

	@Override
	public long uploadData(LoadDataAdapter data) {
		Transaction transaction = TransactionBuilder.createEmptyTransaction();
		try {
			genericProducer.send(data.parseToComputable());
		} catch (InterruptedException | ExecutionException e) {
			log.error(ERROR_UPSTREAMING_DATA, e.getCause());
		}
		return transaction.getUuid();
	}

}
