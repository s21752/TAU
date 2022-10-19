package pl.edu.pjwstk.tau.chain_of_responsibility;

public class Message {

    private String title;
    private String message;
    private String receiverAddress;

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    Message(String title, String message, String receiverAddress) {
        this.title = title;
        this.message = message;
        this.receiverAddress = receiverAddress;
    }
}
