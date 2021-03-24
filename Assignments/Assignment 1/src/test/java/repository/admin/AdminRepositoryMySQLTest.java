package repository.admin;

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

public class AdminRepositoryMySQLTest {

    public static final String TEST_USERNAME_EMPLOYEE1 = "test@yahooEmployee1.com";
    public static final String TEST_PASSWORD_EMPLOYEE1 = "TestPasswordEmployee199@";
    public static final String TEST_USERNAME_EMPLOYEE = "test@yahooEmployee.com";
    public static final String TEST_PASSWORD_EMPLOYEE = "TestPasswordEmployee99@";
    private static AdminRepository adminRepository;
    private static RightsRolesRepository rightsRolesRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        adminRepository = componentFactory.getAdminRepository();
        rightsRolesRepository = componentFactory.getRightsRolesRepository();
    }

    @Before
    public void cleanUp() {
        adminRepository.removeAll();
    }

    @Test
    public void saveEmployee() throws Exception{
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        Assert.assertTrue(adminRepository.saveEmployee(new UserBuilder()
                .setUsername(TEST_USERNAME_EMPLOYEE1)
                .setPassword(TEST_PASSWORD_EMPLOYEE1)
                .setRoles(Collections.singletonList(customerRole))
                .build()));

        Role customerRole1 = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        Assert.assertTrue(adminRepository.saveEmployee(new UserBuilder()
                .setUsername(TEST_USERNAME_EMPLOYEE)
                .setPassword(TEST_PASSWORD_EMPLOYEE)
                .setRoles(Collections.singletonList(customerRole1))
                .build()));
    }

    @Test
    public void viewEmployees() throws Exception{
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        Assert.assertTrue(adminRepository.saveEmployee(new UserBuilder()
                .setUsername(TEST_USERNAME_EMPLOYEE1)
                .setPassword(TEST_PASSWORD_EMPLOYEE1)
                .setRoles(Collections.singletonList(customerRole))
                .build()));

        Role customerRole1 = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        Assert.assertTrue(adminRepository.saveEmployee(new UserBuilder()
                .setUsername(TEST_USERNAME_EMPLOYEE)
                .setPassword(TEST_PASSWORD_EMPLOYEE)
                .setRoles(Collections.singletonList(customerRole1))
                .build()));

        Assert.assertEquals(2, adminRepository.viewEmployees().size());
    }

    @Test
    public void remove() throws Exception{
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        Assert.assertTrue(adminRepository.saveEmployee(new UserBuilder()
                .setUsername(TEST_USERNAME_EMPLOYEE1)
                .setPassword(TEST_PASSWORD_EMPLOYEE1)
                .setRoles(Collections.singletonList(customerRole))
                .build()));

        Role customerRole1 = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        Assert.assertTrue(adminRepository.saveEmployee(new UserBuilder()
                .setUsername(TEST_USERNAME_EMPLOYEE)
                .setPassword(TEST_PASSWORD_EMPLOYEE)
                .setRoles(Collections.singletonList(customerRole1))
                .build()));
        Assert.assertTrue(adminRepository.remove((long) 2));
    }

    @Test
    public void updateEmployeeUsername() throws Exception{
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        Assert.assertTrue(adminRepository.saveEmployee(new UserBuilder()
                .setUsername(TEST_USERNAME_EMPLOYEE1)
                .setPassword(TEST_PASSWORD_EMPLOYEE1)
                .setRoles(Collections.singletonList(customerRole))
                .build()));
        User user = new User();
        user.setUsername("updated@yahoo.com");
        user.setId((long) 1);
        Assert.assertTrue(adminRepository.updateEmployeeUsername(user));
    }

    @Test
    public void updateEmployeePassword() throws Exception
    {
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        Assert.assertTrue(adminRepository.saveEmployee(new UserBuilder()
                .setUsername(TEST_USERNAME_EMPLOYEE)
                .setPassword(TEST_PASSWORD_EMPLOYEE)
                .setRoles(Collections.singletonList(customerRole))
                .build()));
        User user = new User();
        user.setPassword("uUpdatedPass1@");
        user.setId((long) 1);
        Assert.assertTrue(adminRepository.updateEmployeePassword(user));
    }
}
