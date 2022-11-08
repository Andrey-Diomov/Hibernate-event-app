package by.diomov.event.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "eventNotFoundMessage";
    private final Long id;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}