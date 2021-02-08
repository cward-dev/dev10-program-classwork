package learn.powerball.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})                          // 2
@Retention(RUNTIME)
@Constraint(validatedBy = {DuplicatePickValidator.class}) // 3
@Documented
public @interface NoDuplicatePicks {                      // 1
    String message() default "{duplicate picks are forbidden}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}