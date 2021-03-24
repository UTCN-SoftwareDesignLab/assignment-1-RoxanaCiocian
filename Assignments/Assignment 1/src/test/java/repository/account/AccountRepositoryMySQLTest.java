package repository.account;

import launcher.ComponentFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.employee.EmployeeRepository;

import java.sql.Date;
import java.time.LocalDate;

public class AccountRepositoryMySQLTest {
    public static final String ACCOUNT_IDENTNR = "213131";
    public static final String ACCOUNT_TYPE = "Debit";
    public static final float ACCOUNT_BALANCE = 55577;
    public static final Date ACCOUNT_DATE = Date.valueOf(LocalDate.now());
    public static final Long ACCOUNT_CLIENTID = (long)1;

    private static AccountRepository accountRepository;
    private static EmployeeRepository employeeRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        accountRepository = componentFactory.getAccountRepository();
        employeeRepository = componentFactory.getEmployeeRepository();

    }
    @Before
    public void setup() {
        employeeRepository.removeAll();
        Client client = new ClientBuilder()
                .setName("Adina Morosan")
                .setCardNumber("232323")
                .setPnc("1234567891234")
                .setAddress("str Macesului")
                .build();
        employeeRepository.saveClient(client);

        Account account = new AccountBuilder()
                .setIdentNumber(ACCOUNT_IDENTNR)
                .setType(ACCOUNT_TYPE)
                .setBalance(ACCOUNT_BALANCE)
                .setCreationDate(ACCOUNT_DATE)
                .setIdClient(ACCOUNT_CLIENTID)
                .build();

        accountRepository.saveAccount(account);

    }

    @Before
    public void cleanUp() {
        accountRepository.removeAll();
    }

    @Test
    public void saveAccount() throws Exception{
       // Assert.assertEquals(1,accountRepository.findAccountsByClientId((long)1).size());
    }

    @Test
    public void removeAccount() throws Exception{
//        Assert.assertTrue(accountRepository.saveAccount(new AccountBuilder()
//                .setIdentNumber(ACCOUNT_IDENTNR)
//                .setType(ACCOUNT_TYPE)
//                .setBalance(ACCOUNT_BALANCE)
//                .setCreationDate(ACCOUNT_DATE)
//                .setIdClient(ACCOUNT_CLIENTID)
//                .build()));
//        Assert.assertTrue(accountRepository.removeAccount((long) 1));
    }

    @Test
    public void findAllAccounts()throws Exception{
//        Assert.assertTrue(accountRepository.saveAccount(new AccountBuilder()
//                .setIdentNumber(ACCOUNT_IDENTNR)
//                .setType(ACCOUNT_TYPE)
//                .setBalance(ACCOUNT_BALANCE)
//                .setCreationDate(ACCOUNT_DATE)
//                .setIdClient(ACCOUNT_CLIENTID)
//                .build()));
//        Assert.assertEquals(1, accountRepository.findAllAccounts().size());
    }

    @Test
    public void updateBalance() throws Exception{
//        Assert.assertTrue(accountRepository.saveAccount(new AccountBuilder()
//                .setIdentNumber(ACCOUNT_IDENTNR)
//                .setType(ACCOUNT_TYPE)
//                .setBalance(ACCOUNT_BALANCE)
//                .setCreationDate(ACCOUNT_DATE)
//                .setIdClient(ACCOUNT_CLIENTID)
//                .build()));
//        Account account= new Account();
//        account.setBalance((float)3332);
//        account.setId((long) 1);
//        Assert.assertTrue(accountRepository.updateBalance(account));
    }

    @Test
    public void findAccountById() throws Exception {
        //Assert.assertEquals("213131",accountRepository.findAccountsByClientId((long)1));
    }
}
