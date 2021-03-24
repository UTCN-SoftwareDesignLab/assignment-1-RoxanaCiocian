package launcher;

import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.admin.AdminRepository;
import repository.admin.AdminRepositoryMySQL;
import repository.employee.EmployeeRepository;
import repository.employee.EmployeeRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.ServiceAccount;
import service.account.ServiceAccountMySQL;
import service.admin.ServiceAdmin;
import service.admin.ServiceAdminMySQL;
import service.employee.ServiceEmployee;
import service.employee.ServiceEmployeeMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Alex on 18/03/2017.
 */
public class ComponentFactory {

    private final LoginView loginView;
    private final AdminView adminView;
    private final EmployeeView employeeView;

    private final LoginController loginController;
    private final AdminController adminController;
    private final EmployeeController employeeController;

    private final AuthenticationService authenticationService;
    private final ServiceAdmin serviceAdmin;
    private final ServiceEmployee serviceEmployee;
    private final ServiceAccount serviceAccount;

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final AccountRepository accountRepository;
    //private final ClientRepository clientRepository;
    private final RightsRolesRepository rightsRolesRepository;


    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        //this.clientRepository = new ClientRepositoryMySQL(connection);
        this.adminRepository = new AdminRepositoryMySQL(connection, this.rightsRolesRepository);
        this.employeeRepository = new EmployeeRepositoryMySQL(connection);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.serviceAdmin = new ServiceAdminMySQL(this.adminRepository, this.rightsRolesRepository);
        this.serviceEmployee = new ServiceEmployeeMySQL(this.employeeRepository, connection);
        this.serviceAccount = new ServiceAccountMySQL(this.accountRepository);
        this.loginView = new LoginView();
        this.adminView = new AdminView();
        this.employeeView = new EmployeeView();
        this.adminController = new AdminController(adminView, serviceAdmin, loginView);
        this.employeeController = new EmployeeController(employeeView, serviceEmployee, serviceAccount, loginView);
        this.loginController = new LoginController(loginView, authenticationService, adminController, serviceAdmin, employeeController, serviceEmployee);

    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public ServiceAdmin getServiceAdmin() {return serviceAdmin;}

    public ServiceEmployee getServiceEmployee() {return  serviceEmployee;}

    public ServiceAccount getServiceAccount() {return  serviceAccount;}

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public AdminRepository getAdminRepository() {return adminRepository;}

    public EmployeeRepository getEmployeeRepository() {return  employeeRepository;}

    public AccountRepository getAccountRepository() {return accountRepository;}

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public LoginView getLoginView() {
        return loginView;
    }


    public LoginController getLoginController() {
        return loginController;
    }
}
