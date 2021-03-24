package repository.employee;

import launcher.ComponentFactory;
import model.Client;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;

public class EmployeeRepositoryMySQLTest {
    public static final String CLIENT1_NAME = "Dorin Mastan";
    public static final String CLIENT1_CARD_NUMBER = "878787";
    public static final String CLIENT1_PNC = "1112223334445";
    public static final String CLIENT1_ADDRESS = "Strada Primaverii, nr.32";

    public static final String CLIENT2_NAME = "Corina Caragea";
    public static final String CLIENT2_CARD_NUMBER = "654321";
    public static final String CLIENT2_PNC = "9998887776665";
    public static final String CLIENT2_ADDRESS = "Strada Floriilor, nr.2";
    private static EmployeeRepository employeeRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        employeeRepository = componentFactory.getEmployeeRepository();
    }

    @Before
    public void cleanUp() {
        employeeRepository.removeAll();
    }

    @Test
    public void findAll(){
        Assert.assertTrue(employeeRepository.saveClient(new ClientBuilder()
                .setName(CLIENT1_NAME)
                .setCardNumber(CLIENT1_CARD_NUMBER)
                .setPnc(CLIENT1_PNC)
                .setAddress(CLIENT1_ADDRESS)
                .build()));

        Assert.assertEquals(1, employeeRepository.findAll().size());
    }

    @Test
    public void saveClient() throws Exception{
        Assert.assertTrue(employeeRepository.saveClient(new ClientBuilder()
                .setName(CLIENT1_NAME)
                .setCardNumber(CLIENT1_CARD_NUMBER)
                .setPnc(CLIENT1_PNC)
                .setAddress(CLIENT1_ADDRESS)
                .build()));
    }

    @Test
    public void removeClient() throws Exception{
        Assert.assertTrue(employeeRepository.saveClient(new ClientBuilder()
                .setName(CLIENT1_NAME)
                .setCardNumber(CLIENT1_CARD_NUMBER)
                .setPnc(CLIENT1_PNC)
                .setAddress(CLIENT1_ADDRESS)
                .build()));

        Assert.assertTrue(employeeRepository.saveClient(new ClientBuilder()
                .setName(CLIENT2_NAME)
                .setCardNumber(CLIENT2_CARD_NUMBER)
                .setPnc(CLIENT2_PNC)
                .setAddress(CLIENT2_ADDRESS)
                .build()));

        Assert.assertTrue(employeeRepository.removeClient((long) 1));
    }

    @Test
    public void updateClientName() throws Exception{
        Assert.assertTrue(employeeRepository.saveClient(new ClientBuilder()
                .setName(CLIENT2_NAME)
                .setCardNumber(CLIENT2_CARD_NUMBER)
                .setPnc(CLIENT2_PNC)
                .setAddress(CLIENT2_ADDRESS)
                .build()));
        Client client = new Client();
        client.setName("Nea Catavencu");
        client.setId((long) 1);
        Assert.assertTrue(employeeRepository.updateClientName(client));
    }
}
