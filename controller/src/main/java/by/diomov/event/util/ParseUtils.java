package by.diomov.event.util;

import by.diomov.event.exception.IdNotNumberException;

public class ParseUtils {
    public static long parseId(String pathId) {
        try {
            return Long.parseLong(pathId);
        } catch (NumberFormatException ex) {
            throw new IdNotNumberException(pathId);
        }
    }
}