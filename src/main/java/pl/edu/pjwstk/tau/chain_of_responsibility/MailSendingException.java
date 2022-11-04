package pl.edu.pjwstk.tau.chain_of_responsibility;

public class MailSendingException extends RuntimeException {

    public MailSendingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailSendingException(String message) {
        super(message);
    }
}
