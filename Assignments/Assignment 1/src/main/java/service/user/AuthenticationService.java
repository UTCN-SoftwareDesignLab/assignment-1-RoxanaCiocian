package service.user;

import model.User;
import model.validation.Notification;

/**
 * Created by Alex on 11/03/2017.
 */
public interface AuthenticationService {

    Notification<java.lang.Boolean> registerAdmin(String username, String password);

    Notification<java.lang.Boolean> registerEmployee(String username, String password);

    Notification<User> loginEmployee(String username, String password);

    Notification<User> loginAdmin(String username, String password);

    boolean logout(User user);



}
