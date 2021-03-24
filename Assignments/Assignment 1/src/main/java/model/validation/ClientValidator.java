package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private static final int CNP_LENGTH = 13;
    private static final int CARD_NUMBER_LENGTH = 6;
    private static String[] Name_Split = null;

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final Client client;

    public ClientValidator(Client client) {
        errors = new ArrayList<>();
        this.client = client;
    }

    public boolean validate(){
        validateName(client.getName());
        validateCardNumber(client.getCard_number());
        validatePNC(client.getPnc());
        return errors.isEmpty();
    }

    public boolean validateUpdate(){
        validateName(client.getName());
        return errors.isEmpty();
    }

    private void validatePNC(String pnc){
        if(pnc.length() != CNP_LENGTH){
            errors.add("Wrong PNC!");
        }
    }

    private void validateCardNumber(String cardNumber){
        if(cardNumber.length() != CARD_NUMBER_LENGTH){
            errors.add("Wrong identity card number!");
        }
    }

    private void validateName(String name){
        Name_Split = name.split(" ");
        if (Name_Split.length == 1){
            errors.add("Wrong name format!");
        }
    }
}
