package service.user;

import launcher.ComponentFactory;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.user.UserRepository;

/**
 * Created by Alex on 11/03/2017.
 */
public class AuthenticationServiceMySQLTest {

    public static final String TEST_USERNAME_ADMIN = "test@usernameAdmin.com";
    public static final String TEST_PASSWORD_ADMIN = "TestPasswordAdmin1@";
    public static final String TEST_USERNAME_EMPLOYEE = "test@usernameEmployee.com";
    public static final String TEST_PASSWORD_EMPLOYEE = "TestPasswordEmployee1@";
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        userRepository = componentFactory.getUserRepository();
        authenticationService = componentFactory.getAuthenticationService();
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }


    @Test
    public void registerAdmin() {
        Assert.assertTrue(
                authenticationService.registerAdmin(TEST_USERNAME_ADMIN, TEST_PASSWORD_ADMIN).getResult()
        );
    }

    @Test
    public void registerEmployee(){
        Assert.assertTrue(
                authenticationService.registerEmployee(TEST_USERNAME_EMPLOYEE, TEST_PASSWORD_EMPLOYEE).getResult()
        );
    }

    @Test
    public void loginAdmin() throws Exception {
        authenticationService.registerAdmin(TEST_USERNAME_ADMIN, TEST_PASSWORD_ADMIN);
        User user = authenticationService.loginAdmin(TEST_USERNAME_ADMIN, TEST_PASSWORD_ADMIN).getResult();
        Assert.assertNotNull(user);
    }

    @Test
    public void loginEmployee() throws Exception{
        authenticationService.registerEmployee(TEST_USERNAME_EMPLOYEE, TEST_PASSWORD_EMPLOYEE);
        User user1 = authenticationService.loginEmployee(TEST_USERNAME_EMPLOYEE, TEST_PASSWORD_EMPLOYEE).getResult();
        Assert.assertNotNull(user1);
    }

    @Test
    public void logout() {

    }

}