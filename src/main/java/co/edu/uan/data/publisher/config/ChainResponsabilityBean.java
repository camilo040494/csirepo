package co.edu.uan.data.publisher.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.uan.data.publisher.service.chainofresponsability.HandlerValidator;

@Configuration
public class ChainResponsabilityBean {
	
	@Bean({"handlerValidatorBean"})
	public HandlerValidator handlerValidatorBean(@Autowired @Qualifier(value = "nameValidator") HandlerValidator nameValidator, 
			@Autowired @Qualifier(value = "sizeValidator") HandlerValidator sizeValidator){
		nameValidator.setNext(sizeValidator);
		return nameValidator;
	}
	
}
