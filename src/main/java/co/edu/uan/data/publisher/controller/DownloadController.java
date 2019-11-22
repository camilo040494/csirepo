package co.edu.uan.data.publisher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uan.data.publisher.controller.request.AuthenticatedRequest;
import co.edu.uan.data.publisher.controller.request.DownloadRequest;
import co.edu.uan.data.publisher.service.DownloadService;

@RestController
@RequestMapping({ "/download" })
@CrossOrigin(origins = "*")
public class DownloadController {
  
//  @Autowired
  private DownloadService downloadService;
  
  @GetMapping(value = "/health")
  public ResponseEntity<String> health() {
   return new ResponseEntity<>("OK", HttpStatus.OK);
 }
  
  @GetMapping("/document")
  public ResponseEntity<byte[]> downloadDocument(@RequestBody @Validated DownloadRequest request) {
	  return null;
	  //    return new ResponseEntity<>(authentication, HttpStatus.OK);
  }
  
  @GetMapping("/result")
  public ResponseEntity<Object> documentResult(@RequestHeader(required = true) AuthenticatedRequest authentication, @RequestBody @Validated DownloadRequest request) {
	  return null;
//    return new ResponseEntity<>(authentication, HttpStatus.OK);
  }
  
}
