package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {
    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final Account account;

    public AccountValidator( Account account) {
        errors = new ArrayList<>();
        this.account = account;
    }

    public boolean validate(){
        validatePNC(account.getId_client());
        return errors.isEmpty();
    }

    private void validatePNC(Long clientId){
        if(clientId == null){
            errors.add("No client selected");
        }
    }
}
