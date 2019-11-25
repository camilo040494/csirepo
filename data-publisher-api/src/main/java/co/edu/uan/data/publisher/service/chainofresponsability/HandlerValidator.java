package co.edu.uan.data.publisher.service.chainofresponsability;

import java.io.File;
import java.util.Objects;

public abstract class HandlerValidator {
	
	protected HandlerValidator next;
	
	public void setNext(HandlerValidator nextHandler) {
		next = nextHandler;
	}
	
	public abstract boolean validate(File file);
	
	protected boolean checkNext(File file) {
        if (Objects.nonNull(next)) {
        	return next.validate(file);
        }
        return true;
    }
	
}
