package model.builder;

import model.Account;

import java.sql.Date;

public class AccountBuilder {

    private Account account;

    public AccountBuilder(){ account = new Account();}

    public AccountBuilder setId(Long id){
        account.setId(id);
        return this;
    }

    public AccountBuilder setIdentNumber(String identNumber){
        account.setIdent_number(identNumber);
        return this;
    }

    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }

    public AccountBuilder setBalance(float balance){
        account.setBalance(balance);
        return this;
    }

    public AccountBuilder setCreationDate(Date creationDate){
        account.setCreation_date(creationDate);
        return this;
    }

    public AccountBuilder setIdClient(Long id){
        account.setId_client(id);
        return this;
    }

    public Account build(){
        return account;
    }

}
