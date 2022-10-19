package pl.edu.pjwstk.tau.chain_of_responsibility.validators;

abstract class StringEmptinessValidator extends BaseMessageValidatorChainComponent {

    boolean isStringPopulated(String string) {
        return string != null && !string.isBlank();
    }
}
