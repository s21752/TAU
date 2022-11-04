package pl.edu.pjwstk.tau.chain_of_responsibility.validators;

import pl.edu.pjwstk.tau.chain_of_responsibility.Message;

public class MessageTitleValidator extends StringEmptinessValidator{

    @Override
    protected MessageValidatorException getComponentException() {
        return new MessageValidatorException("No message title provided");
    }

    @Override
    protected boolean isInnerValidationCorrect(Message message) {
        return isStringPopulated(message.getTitle());
    }
}
