package pl.edu.pjwstk.tau.chain_of_responsibility.validators;

import pl.edu.pjwstk.tau.chain_of_responsibility.Message;

public class MessageTextValidator extends StringEmptinessValidator {

    @Override
    protected MessageValidatorException getComponentException() {
        return new MessageValidatorException("No text content provided");
    }

    @Override
    protected boolean isInnerValidationCorrect(Message message) {
        return isStringPopulated(message.getMessage());
    }
}
