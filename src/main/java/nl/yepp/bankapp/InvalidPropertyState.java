package nl.yepp.bankapp;

public class InvalidPropertyState extends RuntimeException {
    public InvalidPropertyState() {
    }

    public InvalidPropertyState(String message) {
        super(message);
    }

    public InvalidPropertyState(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPropertyState(Throwable cause) {
        super(cause);
    }

    public InvalidPropertyState(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
