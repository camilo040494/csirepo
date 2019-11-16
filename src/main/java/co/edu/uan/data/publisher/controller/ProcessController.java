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
import co.edu.uan.data.publisher.controller.request.ProcessRequest;
import co.edu.uan.data.publisher.service.ProcessService;

@RestController
@RequestMapping({ "/process" })
@CrossOrigin(origins = "*")
public class ProcessController {
  
  @Autowired
  private ProcessService processService;
  
  @GetMapping(value = "/health")
  public ResponseEntity<String> health() {
   return new ResponseEntity<>("OK", HttpStatus.OK);
 }
  
  @PostMapping("/run")
  public ResponseEntity<Integer> create(@RequestHeader(required = true) AuthenticatedRequest authentication, @RequestBody @Validated ProcessRequest request) {
    return null;
  }
  
}
