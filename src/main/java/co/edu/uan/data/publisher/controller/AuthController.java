package co.edu.uan.data.publisher.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uan.data.publisher.controller.request.AuthenticatedRequest;
import co.edu.uan.data.publisher.controller.request.LoginRequest;
import co.edu.uan.data.publisher.controller.request.UrlRequest;
import co.edu.uan.data.publisher.service.AuthenticationService;

@RestController
@RequestMapping({ "/authentication" })
@CrossOrigin(origins = "*")
public class AuthController {
  
  @Autowired
  private AuthenticationService authenticationService;
  
  @GetMapping(value = "/health")
  public ResponseEntity<String> health() {
   return new ResponseEntity<>("OK", HttpStatus.OK);
 }
  
  @PostMapping("/login")
  public ResponseEntity<String> create(@RequestHeader(required = true) @Validated LoginRequest request) {
    return null;
//    return new ResponseEntity<>(processRssFeeds, HttpStatus.OK);
  }
  
}
