package co.edu.uan.data.publisher.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.uan.data.publisher.service.chainofresponsability.HandlerValidator;

@Configuration
public class ChainResponsabilityBean {
	
	@Qualifier(value = "nameValidator")
	@Autowired
	private HandlerValidator nameValidator;
	
	@Qualifier(value = "sizeValidator")
	@Autowired
	private HandlerValidator sizeValidator;
	
	@Bean({"handlerValidatorBean"})
	public HandlerValidator handlerValidatorBean(){
		nameValidator.setNext(sizeValidator);
		return nameValidator;
	}
	
}
