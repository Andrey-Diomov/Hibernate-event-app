package by.diomov.event.exception;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;
import java.util.Locale;

@Log4j2
@RestControllerAdvice
@AllArgsConstructor
public class ResponseHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    private static final String SERVER_ERROR_MESSAGE = "serverErrorMessage";
    private static final String SPACE_DELIMITER = " ";
    private static final String COMMA_DELIMITER = ";";
    private static final String DASH = " - ";

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(EventNotFoundException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getId();
        return new ErrorResponse(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(WrongDataException ex, Locale locale) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(
                messageSource.getMessage(ex.getErrorMessage(), null, locale));
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

        errors.stream().filter(FieldError.class::isInstance)
                .forEach(objectError -> errorMessage
                        .append(SPACE_DELIMITER)
                        .append(((FieldError) objectError).getField())
                        .append(DASH)
                        .append(messageSource.getMessage(objectError, locale))
                        .append(COMMA_DELIMITER));
        errorMessage.deleteCharAt(errorMessage.length() - 1);
        return new ErrorResponse(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(RuntimeException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(SERVER_ERROR_MESSAGE, null, locale);
        log.error(ex);
        return new ErrorResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WrongIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(WrongIdException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getId();
        return new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdNotNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(IdNotNumberException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getId();
        return new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyBodyRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(EmptyBodyRequestException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        return new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }
}