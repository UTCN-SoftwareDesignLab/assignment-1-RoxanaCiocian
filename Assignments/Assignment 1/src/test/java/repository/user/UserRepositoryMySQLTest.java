package repository.user;

import launcher.ComponentFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RightsRolesRepository;

import java.util.Collections;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class UserRepositoryMySQLTest {
    public static final String TEST_USERNAME_ADMIN = "test@yahooAdmin.com";
    public static final String TEST_PASSWORD_ADMIN = "TestPasswordAdmin99@";
    public static final String TEST_USERNAME_EMPLOYEE = "test@yahooEmployee.com";
    public static final String TEST_PASSWORD_EMPLOYEE = "TestPasswordEmployee99@";
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        userRepository = componentFactory.getUserRepository();
        rightsRolesRepository = componentFactory.getRightsRolesRepository();
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }

    @Test
    public void save() throws Exception{
        Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
        Assert.assertTrue(userRepository.save(new UserBuilder()
        .setUsername(TEST_USERNAME_ADMIN)
        .setPassword(TEST_PASSWORD_ADMIN)
        .setRoles(Collections.singletonList(customerRole))
        .build()));

        Role customerRole1 = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        Assert.assertTrue(userRepository.save(new UserBuilder()
                .setUsername(TEST_USERNAME_EMPLOYEE)
                .setPassword(TEST_PASSWORD_EMPLOYEE)
                .setRoles(Collections.singletonList(customerRole1))
                .build()));
    }

    @Test
    public void findByUsernameAndPasswordAdmin() throws Exception{
        Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
        User user = new UserBuilder()
                .setUsername(TEST_USERNAME_ADMIN)
                .setPassword(TEST_PASSWORD_ADMIN)
                .setRoles(Collections.singletonList(customerRole))
                .build();
        userRepository.save(user);
       User user1 = userRepository.findByUsernameAndPasswordAdmin(user.getUsername(), user.getPassword()).getResult();
       Assert.assertNotNull(user1);
    }

    @Test
    public void findByUsernameAndPasswordEmployee() throws Exception{
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(TEST_USERNAME_EMPLOYEE)
                .setPassword(TEST_PASSWORD_EMPLOYEE)
                .setRoles(Collections.singletonList(customerRole))
                .build();
        userRepository.save(user);
        User user1 = userRepository.findByUsernameAndPasswordEmployee(user.getUsername(), user.getPassword()).getResult();
        Assert.assertNotNull(user1);
    }
}
