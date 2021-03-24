package service.admin;

import launcher.ComponentFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.admin.AdminRepository;

public class ServiceAdminMySQLTest {
    public static final String TEST_USERNAME_EMPLOYEE = "test@usernameEmployee.com";
    public static final String TEST_PASSWORD_EMPLOYEE = "TestPasswordEmployee111@";
    public static final String TEST_USERNAME_EMPLOYEE1 = "test@usernameEmployee1.com";
    public static final String TEST_PASSWORD_EMPLOYEE1 = "TestPasswordEmployeeV1@";
    private static ServiceAdmin serviceAdmin;
    private static AdminRepository adminRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        adminRepository = componentFactory.getAdminRepository();
        serviceAdmin = componentFactory.getServiceAdmin();
    }

    @Before
    public void cleanUp() {
        adminRepository.removeAll();
    }

    @Test
    public void createEmployeeAccount() throws Exception{
        Assert.assertTrue(
                serviceAdmin.createEmployeeAccount(TEST_USERNAME_EMPLOYEE, TEST_PASSWORD_EMPLOYEE).getResult()
        );
        Assert.assertTrue(
                serviceAdmin.createEmployeeAccount(TEST_USERNAME_EMPLOYEE1, TEST_PASSWORD_EMPLOYEE1).getResult()
        );
    }

    @Test
    public void view() throws Exception{
        serviceAdmin.createEmployeeAccount(TEST_USERNAME_EMPLOYEE, TEST_PASSWORD_EMPLOYEE);
        serviceAdmin.createEmployeeAccount(TEST_USERNAME_EMPLOYEE1, TEST_PASSWORD_EMPLOYEE1);
        Assert.assertEquals(2, serviceAdmin.view().size());
    }

    @Test
    public void remove() throws Exception{
        serviceAdmin.createEmployeeAccount(TEST_USERNAME_EMPLOYEE, TEST_PASSWORD_EMPLOYEE);
        serviceAdmin.createEmployeeAccount(TEST_USERNAME_EMPLOYEE1, TEST_PASSWORD_EMPLOYEE1);
        Assert.assertTrue(serviceAdmin.remove((long) 1));
    }

    @Test
    public void updateEmployeeUsername() throws Exception{
        serviceAdmin.createEmployeeAccount(TEST_USERNAME_EMPLOYEE, TEST_PASSWORD_EMPLOYEE);
        serviceAdmin.createEmployeeAccount(TEST_USERNAME_EMPLOYEE1, TEST_PASSWORD_EMPLOYEE1);
        Assert.assertTrue(serviceAdmin.updateEmployeeUsername("Elena@yahoo.com", (long)2));

    }

    @Test
    public void updateEmployeePassword() throws Exception{
        serviceAdmin.createEmployeeAccount(TEST_USERNAME_EMPLOYEE, TEST_PASSWORD_EMPLOYEE);
        serviceAdmin.createEmployeeAccount(TEST_USERNAME_EMPLOYEE1, TEST_PASSWORD_EMPLOYEE1);
        Assert.assertTrue(serviceAdmin.updateEmployeePassword("newUpdatedPass3@", (long)2));

    }

}
