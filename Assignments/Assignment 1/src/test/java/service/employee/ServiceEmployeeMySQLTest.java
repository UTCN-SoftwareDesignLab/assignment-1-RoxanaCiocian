package service.employee;

import launcher.ComponentFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.employee.EmployeeRepository;

public class ServiceEmployeeMySQLTest {
    public static final String CLIENT1_NAME = "Ana Morosan";
    public static final String CLIENT1_CARD_NUMBER = "123456";
    public static final String CLIENT1_PNC = "1112223334445";
    public static final String CLIENT1_ADDRESS = "Strada Macesului, nr.2";

    public static final String CLIENT2_NAME = "Corina Caragea";
    public static final String CLIENT2_CARD_NUMBER = "654321";
    public static final String CLIENT2_PNC = "9998887776665";
    public static final String CLIENT2_ADDRESS = "Strada Floriilor, nr.2";

    private static ServiceEmployee serviceEmployee;
    private static EmployeeRepository employeeRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        employeeRepository = componentFactory.getEmployeeRepository();
        serviceEmployee = componentFactory.getServiceEmployee();
    }

    @Before
    public void cleanUp() {
        employeeRepository.removeAll();
    }

    @Test
    public void createClient() throws Exception{
        Assert.assertTrue(
                serviceEmployee.createClient(CLIENT1_NAME,CLIENT1_CARD_NUMBER,CLIENT1_PNC,CLIENT1_ADDRESS).getResult()
        );
        Assert.assertTrue(
                serviceEmployee.createClient(CLIENT2_NAME,CLIENT2_CARD_NUMBER,CLIENT2_PNC,CLIENT2_ADDRESS).getResult()
        );
    }

    @Test
    public void view() throws Exception{
        serviceEmployee.createClient(CLIENT1_NAME,CLIENT1_CARD_NUMBER,CLIENT1_PNC,CLIENT1_ADDRESS);
        serviceEmployee.createClient(CLIENT2_NAME,CLIENT2_CARD_NUMBER,CLIENT2_PNC,CLIENT2_ADDRESS);
        Assert.assertEquals(2, serviceEmployee.viewClients().size());
    }

    @Test
    public void updateClientName() throws Exception{
        serviceEmployee.createClient(CLIENT1_NAME,CLIENT1_CARD_NUMBER,CLIENT1_PNC,CLIENT1_ADDRESS);
        serviceEmployee.createClient(CLIENT2_NAME,CLIENT2_CARD_NUMBER,CLIENT2_PNC,CLIENT2_ADDRESS);
        Assert.assertTrue(serviceEmployee.updateClientName1("Andreea Marin", (long)2).getResult());

    }
}
