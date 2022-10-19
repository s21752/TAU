package pl.edu.pjwstk.tau.chain_of_responsibility.validators;

import pl.edu.pjwstk.tau.chain_of_responsibility.Message;

public abstract class BaseMessageValidatorChainComponent implements IMessageComponentValidator {

    BaseMessageValidatorChainComponent nextValidator;

    public void setNextChainNode(BaseMessageValidatorChainComponent validator) {
        this.nextValidator = validator;
    }

    @Override
    public void validate(Message message) throws MessageValidatorException {
        if (isInnerValidationCorrect(message)) {
            if (nextValidator != null) {
                nextValidator.validate(message);
            }
        } else {
            throw getComponentException();
        }
    }

    protected abstract MessageValidatorException getComponentException();

    protected abstract boolean isInnerValidationCorrect(Message message);
}
