package ws.ament.numbers.rest;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable t) {
        super(message,t);
    }
}
