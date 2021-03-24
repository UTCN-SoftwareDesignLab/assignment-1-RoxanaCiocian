package repository.user;

import model.User;
import model.validation.Notification;

/**
 * Created by Alex on 11/03/2017.
 */
public interface UserRepository {

    //List<User> findAll();

    Notification<User> findByUsernameAndPasswordEmployee(String username, String password);

    Notification<User> findByUsernameAndPasswordAdmin(String username, String password);

    boolean save(User user);

//    List<User> viewEmployees();
//
//    boolean remove(Long id);
//
//    boolean updateUsername(User employee);
//
//    boolean updatePassword(User employee);
//
    void removeAll();

}
