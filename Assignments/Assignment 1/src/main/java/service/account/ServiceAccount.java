package service.account;

import model.Account;
import model.validation.Notification;

import java.sql.Date;
import java.util.List;


public interface ServiceAccount {

    Notification<Boolean> createAccount(String ident_number, String type, float balance, Date creation_date, Long id_client);

    List<Account> viewAccounts();

    boolean removeAccount(Long id);

    boolean updateClientBalance(Float balance, Long id);
}
