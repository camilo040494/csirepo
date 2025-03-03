package co.edu.uan.data.publisher.util.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ListValidatorConstraint.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListValidator {
  
  String message() default "{ListValidator}";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
    
}
