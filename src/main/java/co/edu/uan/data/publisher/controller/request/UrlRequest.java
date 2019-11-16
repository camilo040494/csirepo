package co.edu.uan.data.publisher.controller.request;

import java.util.List;

import co.edu.uan.data.publisher.util.validator.ListValidator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlRequest {
  
  @ListValidator(message = "You must specify more than 1 url's")
  private List<String> urls;
  
}
