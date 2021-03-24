package repository.account;

import model.Account;

import java.util.List;

public interface AccountRepository {

     boolean saveAccount( Account account);

    boolean removeAccount(Long id);

    List<Account> findAllAccounts();

    boolean updateBalance(Account account);

    void removeAll();

    List<Account> findAccountsByClientId(Long id);
}
