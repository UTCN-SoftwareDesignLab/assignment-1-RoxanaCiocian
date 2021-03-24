package service.account;

import model.Account;
import model.builder.AccountBuilder;
import model.validation.AccountValidator;
import model.validation.Notification;
import repository.account.AccountRepository;

import javax.swing.*;
import java.sql.Date;
import java.util.List;

public class ServiceAccountMySQL implements ServiceAccount{

    private AccountRepository accountRepository;

    public ServiceAccountMySQL(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification<Boolean> createAccount( String ident_number, String type, float balance, Date creation_date, Long id_client) {
            Account account = new AccountBuilder()
                .setIdentNumber(ident_number)
                .setType(type)
                .setBalance(balance)
                .setCreationDate(creation_date)
                .setIdClient(id_client)
                .build();

        AccountValidator accountValidator = new AccountValidator(account);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> accountNotification = new Notification<>();

        if (!accountValid) {
            accountValidator.getErrors().forEach(accountNotification::addError);
            accountNotification.setResult(Boolean.FALSE);
        } else {
            accountNotification.setResult(accountRepository.saveAccount(account));
        }

        return accountNotification;

    }

    @Override
    public List<Account> viewAccounts() {
        System.out.println(accountRepository.findAllAccounts().toString());
        return accountRepository.findAllAccounts();
    }

    @Override
    public boolean removeAccount(Long id) {
        return accountRepository.removeAccount(id);
    }

    @Override
    public boolean updateClientBalance(Float balance, Long id) {
        Account account = new AccountBuilder()
                .setId(id)
                .setBalance(balance)
                .build();
        return accountRepository.updateBalance(account);
    }
}
