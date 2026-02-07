package aze.exception;

/**
 * Represents exceptions specific to the Aze application.
 */
public class AzeException extends Exception {

    /**
     * Constructs a new AzeException with the specified message.
     *
     * @param message The error message.
     */
    public AzeException(String message) {
        super(message);
    }
}
