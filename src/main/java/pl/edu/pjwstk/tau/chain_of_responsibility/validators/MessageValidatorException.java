package pl.edu.pjwstk.tau.chain_of_responsibility.validators;

public class MessageValidatorException extends Exception {

    MessageValidatorException(String message) {
        super(message);
    }

    MessageValidatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
