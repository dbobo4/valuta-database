package hu.dbobo.exceptions;

public class IncompleteDatabaseException extends RuntimeException{
    public IncompleteDatabaseException(String message) {
        super(message);
    }
}
