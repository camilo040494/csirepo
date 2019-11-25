package co.edu.uan.data.publisher.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	
	private String loginIp;
	private String username;
	private String password;
	
}
