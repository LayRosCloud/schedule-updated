package by.betrayal.audienceservice.utils.validation;

import by.betrayal.audienceservice.exception.BadRequestException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

public class DateValidator implements ConstraintValidator<MinDate, Date> {

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        var now = new Date();

        if (value != null && now.after(value)) {
            throw new BadRequestException(context.getDefaultConstraintMessageTemplate());
        }

        return value != null && now.before(value);
    }
}
