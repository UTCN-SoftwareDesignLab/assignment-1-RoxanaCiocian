package service.admin;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface ServiceAdmin {

    Notification<java.lang.Boolean> createEmployeeAccount(String username, String password);


    boolean logout(User user);

    List<User> view();

    boolean remove(Long id);

    boolean updateEmployeeUsername(String username, Long id);

    boolean updateEmployeePassword(String password, Long id);
}
