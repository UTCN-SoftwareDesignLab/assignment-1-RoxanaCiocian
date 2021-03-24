package service.account;

import launcher.ComponentFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepository;



import java.sql.Date;
import java.time.LocalDate;


public class ServiceAccountMySQLTest {
    public static final String ACCOUNT_IDENTNR = "213131";
    public static final String ACCOUNT_TYPE = "Debit";
    public static final float ACCOUNT_BALANCE = 55577;
    public static final Date ACCOUNT_DATE = Date.valueOf(LocalDate.now());
    public static final Long ACCOUNT_CLIENTID = (long)1;

    private static ServiceAccount serviceAccount;
    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        serviceAccount = componentFactory.getServiceAccount();
        accountRepository = componentFactory.getAccountRepository();
    }

    @Before
    public void cleanUp() {
        accountRepository.removeAll();
    }

    @Test
    public void createAccount() throws Exception{
        Assert.assertTrue(
                serviceAccount.createAccount(ACCOUNT_IDENTNR, ACCOUNT_TYPE, ACCOUNT_BALANCE, ACCOUNT_DATE, ACCOUNT_CLIENTID).getResult()
        );

    }

    @Test
    public void viewAccounts() throws Exception{
        serviceAccount.createAccount(ACCOUNT_IDENTNR, ACCOUNT_TYPE, ACCOUNT_BALANCE, ACCOUNT_DATE, ACCOUNT_CLIENTID);

        Assert.assertEquals(1, serviceAccount.viewAccounts().size());
    }

    @Test
    public void removeAccount() throws Exception{
        serviceAccount.createAccount(ACCOUNT_IDENTNR, ACCOUNT_TYPE, ACCOUNT_BALANCE, ACCOUNT_DATE, ACCOUNT_CLIENTID);

        Assert.assertTrue(serviceAccount.removeAccount((long)1));
    }

    @Test
    public void updateClientBalance() throws Exception{
        serviceAccount.createAccount(ACCOUNT_IDENTNR, ACCOUNT_TYPE, ACCOUNT_BALANCE, ACCOUNT_DATE, ACCOUNT_CLIENTID);
        Assert.assertTrue(serviceAccount.updateClientBalance((float)2233, (long)1));

    }
}
