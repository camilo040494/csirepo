package co.edu.uan.data.publisher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uan.data.publisher.controller.request.AuthenticatedRequest;
import co.edu.uan.data.publisher.controller.request.UploadDataRequest;
import co.edu.uan.data.publisher.controller.request.UploadFileRequest;
import co.edu.uan.data.publisher.controller.response.OkResponse;
import co.edu.uan.data.publisher.exception.business.UnauthorizedException;
import co.edu.uan.data.publisher.service.UploadServiceService;

@RestController
@RequestMapping({ "/upload" })
@CrossOrigin(origins = "*")
public class UploadController {
  
  @Autowired
  private UploadServiceService uploadService;
  
  @GetMapping(value = "/health")
  public ResponseEntity<String> health() {
   return new ResponseEntity<>("OK", HttpStatus.OK);
  }
  
  @PostMapping("/data")
  public ResponseEntity<Integer> uploadData(@RequestHeader(required = true) AuthenticatedRequest authentication, @RequestBody @Validated UploadDataRequest request) {
	  throw new UnauthorizedException();
  }
  
  @PostMapping("/file")
  public ResponseEntity<OkResponse> uploadFile(@RequestHeader(required = true) AuthenticatedRequest authentication, @RequestBody @Validated UploadFileRequest request) {
	  return null;
  }
  
}
