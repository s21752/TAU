package pl.edu.pjwstk.tau.proxy;

import pl.edu.pjwstk.tau.chain_of_responsibility.MailSendingException;
import pl.edu.pjwstk.tau.chain_of_responsibility.TrueMailSender;

public class MailSenderProxy implements MailSender {

    TrueMailSender trueMailSender;
    private String lastReceiverAddress;
    private String lastMessageTitle;
    private String lastMessageText;

    @Override
    public void sendMail(String receiverAddress, String title, String message) {

        /*
         through the use of proxy we can for example store information about last receiver and last mail title/message
         so that exactly the same message cannot be send again
        */
        if (lastMessageTitle != null && lastReceiverAddress != null && lastMessageText != null
                && lastMessageText.equals(message) && lastMessageTitle.equals(title)
                && lastReceiverAddress.equals(receiverAddress)) {
            throw new MailSendingException("Exactly same message was already send to this receiver");
        }

        this.lastReceiverAddress = receiverAddress;
        this.lastMessageTitle = title;
        this.lastMessageText = message;

        /*
         proxy is a nice pattern if TrueMailSender instantiation eats up lots of resources, because it will be done only
         if we need it to send mail, and not at the creation of MailSenderProxy
        */
        if (trueMailSender == null) {
            this.trueMailSender = new TrueMailSender();
        }

        this.trueMailSender.sendMail(receiverAddress, title, message);
    }
}
