package pl.edu.pjwstk.tau.chain_of_responsibility.validators;

import pl.edu.pjwstk.tau.chain_of_responsibility.Message;

public interface IMessageComponentValidator {

    void validate(Message message) throws MessageValidatorException;
}
