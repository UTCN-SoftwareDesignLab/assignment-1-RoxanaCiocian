package repository.admin;

import model.User;

import java.util.List;

public interface AdminRepository {

    boolean saveEmployee(User user);

    List<User> viewEmployees();

    boolean remove(Long id);

    boolean updateEmployeeUsername(User employee);

    boolean updateEmployeePassword(User employee);

    void removeAll();
}

