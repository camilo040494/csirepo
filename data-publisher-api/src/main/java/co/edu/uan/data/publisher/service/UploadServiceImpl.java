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

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import co.edu.uan.data.publisher.adapter.LoadDataAdapter;
import co.edu.uan.data.publisher.controller.request.UploadDataRequest;
import co.edu.uan.data.publisher.controller.request.UploadFileRequest;
import co.edu.uan.data.publisher.model.DataLocation;
import co.edu.uan.data.publisher.model.MovilEnum;
import co.edu.uan.data.publisher.model.Transaction;
import co.edu.uan.data.publisher.model.TransactionBuilder;
import co.edu.uan.data.publisher.repository.DataLocationRepository;
import co.edu.uan.data.publisher.service.chainofresponsability.HandlerValidator;
import co.edu.uan.data.publisher.util.validator.ParserFileUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UploadServiceImpl implements UploadServiceService {

	private static final String ERROR_IO_FILE_DELETION = "An IO error happened when trying to delete file [{}] with cause:[{}]";
	private static final String ERROR_TEMP_FILE_DELETION = "An error happened trying to delete temporally file [{}] with message: [{}]";
	private static final String ERROR_UPSTREAMING_DATA = "Error loading data: [{}]";
	private static final String ERROR_READING_FILE = "Error loading file with cause: [{}]";
	
	@Autowired
	@Qualifier("handlerValidatorBean")
	private HandlerValidator handlerValidatorBean;
	
	@Autowired
	private DataLocationRepository dataLocationRepository;

	@Override
	public long uploadFile(UploadFileRequest file) {
		Transaction transaction = TransactionBuilder.createEmptyTransaction();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		log.info("\nBegin: " + sdf.format(new Date()));
		
		File parsedFile = ParserFileUtils.parseContentToFile(file.getData());
		
		boolean validate = handlerValidatorBean.validate(parsedFile);
		if (!validate) {
			//TODO
			deleteFile(parsedFile);
			throw new RuntimeException();
		}
		
		log.info("Validations passed correctly");
		
		try (BufferedReader br = new BufferedReader(new FileReader(parsedFile))) {
			for (String line = null; StringUtils.isNoneEmpty((line = br.readLine()));) {
				
			}
		} catch (IOException e) {
			log.error(ERROR_READING_FILE, e.getCause());
			throw new RuntimeException();
		}
		
		deleteFile(parsedFile);
		
		return transaction.getUuid();
	}

	private void deleteFile(File parsedFile) {
		try {
			Files.delete(parsedFile.toPath());
		} catch (NoSuchFileException | DirectoryNotEmptyException e) {
			log.error(ERROR_TEMP_FILE_DELETION, parsedFile.toPath(), e.getCause());
			throw new RuntimeException();
		} catch (IOException e) {
			log.error(ERROR_IO_FILE_DELETION, parsedFile.toPath(), e.getCause());
		}
	}

	@Override
	public long uploadData(UploadDataRequest data) {
	  Transaction transaction = TransactionBuilder.createEmptyTransaction();
	  DataLocation location = new DataLocation();
	  
	  Point point = parseToPoint(data.getLocation ());
   
   location.setLocation(point);
	  location.setMovilAgresor(MovilEnum.ARMA_BLANCA);
	  location.setVictimsAge(data.getEdadVictima());
	  
   dataLocationRepository.save(location);
	  return transaction.getUuid();
	}

  private Point parseToPoint(org.wololo.geojson.Point data) {
    double[] coordinates = data.getCoordinates();
	  Coordinate[] coords = new Coordinate[1];
   coords[0] = new Coordinate(coordinates[0], coordinates[1]);
   CoordinateArraySequence coordArraySeq = new CoordinateArraySequence(coords);
   Point point = new Point(coordArraySeq, new GeometryFactory(new PrecisionModel(), 4326));
    return point;
  }
	  
	@Override
	public long uploadData(LoadDataAdapter data) {
		Transaction transaction = TransactionBuilder.createEmptyTransaction();
		throw new NotImplementedException("Not implemented");
//		try {
//			genericProducer.send(Long.toString(transaction.getUuid()), data.parseToComputable());
//		} catch (InterruptedException | ExecutionException e) {
//			log.error(ERROR_UPSTREAMING_DATA, e.getCause());
//		}
//		return transaction.getUuid();
	}

}
