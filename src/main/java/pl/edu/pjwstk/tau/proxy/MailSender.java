package pl.edu.pjwstk.tau.proxy;

public interface MailSender {

    public void sendMail(String receiverAddress, String title, String message);
}
