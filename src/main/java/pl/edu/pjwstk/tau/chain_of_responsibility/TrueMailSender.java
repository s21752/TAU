package pl.edu.pjwstk.tau.chain_of_responsibility;

import pl.edu.pjwstk.tau.chain_of_responsibility.validators.*;
import pl.edu.pjwstk.tau.proxy.MailSender;

public class TrueMailSender implements MailSender {

    private BaseMessageValidatorChainComponent chainOfValidators;

    public TrueMailSender() {
        createChainOfValidators();
    }

    private void createChainOfValidators() {
        var step1 = new MessageReceiverMailAddressValidator();
        var step2 = new MessageReceiverMailAddressCorrectnessValidator();
        var step3 = new MessageTitleValidator();
        var step4 = new MessageTitleLenghtValidator();
        var step5 = new MessageTextValidator();

        step1.setNextChainNode(step2);
        step2.setNextChainNode(step3);
        step3.setNextChainNode(step4);
        step4.setNextChainNode(step5);
        this.chainOfValidators = step1;
    }

    @Override
    public void sendMail(String receiverAddress, String title, String message)  {
        try {
            var mail = new Message(title, message, receiverAddress);
            chainOfValidators.validate(mail);
            System.out.print("Mail sent correctly!");
        } catch (MessageValidatorException exception) {
            throw new MailSendingException("Couldn't send the mail because validation error occurred: ", exception);
        }
    }
}
