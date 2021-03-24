package repository.user;

import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;

import static database.Constants.Tables.USER;

/**
 * Created by Alex on 11/03/2017.
 */
public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

//    @Override
//    public List<User> findAll() {
//        return null;
//    }

    @Override
    public Notification<User> findByUsernameAndPasswordEmployee(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` INNER JOIN user_role ON user.id=user_role.user_id where user_role.role_id=2 and `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid employee email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database");
        }
        return findByUsernameAndPasswordNotification;
    }

    @Override
    public Notification<User> findByUsernameAndPasswordAdmin(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` INNER JOIN user_role ON user.id=user_role.user_id where user_role.role_id=1 and `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid admin email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database");
        }
        return findByUsernameAndPasswordNotification;
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

//    @Override
//    public List<User> viewEmployees(){
//        List<User> employees = new ArrayList<User>();
//        try {
//            Statement statement = connection.createStatement();
//            String sql = "SELECT user.id, user.username, user.password, role.role FROM user INNER JOIN user_role ON user.id=user_role.user_id INNER JOIN role ON user_role.role_id=role.id WHERE user_role.role_id=2";
//            ResultSet rs = statement.executeQuery(sql);
//
//            while (rs.next()) {
//                employees.add(getUsersFromResultSet(rs));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return employees;
//
//    }
//
//    private User getUsersFromResultSet(ResultSet rs) throws SQLException {
//        return new UserBuilder()
//                .setId(rs.getLong("id"))
//                .setPassword(rs.getString("password"))
//                .setUsername(rs.getString("username"))
//                .setRoles(rightsRolesRepository.findRolesForUser(rs.getLong("id")))
//                .build();
//    }
//
//    @Override
//    public boolean remove(Long id){
//        try {
//            Statement statement = connection.createStatement();
//            String sql = "DELETE from user where id = " + id;
//            statement.executeUpdate(sql);
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean updateUsername(User employee){
//        try {
//            PreparedStatement insertUserStatement = connection
//                    .prepareStatement("UPDATE user SET username = ? WHERE id = " + employee.getId());
//            insertUserStatement.setString(1, employee.getUsername());
//            insertUserStatement.executeUpdate();
//
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
//    public boolean updatePassword(User employee){
//        try {
//            PreparedStatement insertUserStatement = connection
//                    .prepareStatement("UPDATE user SET password = ? WHERE id = " + employee.getId());
//            insertUserStatement.setString(1, employee.getPassword());
//            insertUserStatement.executeUpdate();
//
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            String auto = "ALTER TABLE user AUTO_INCREMENT = 1";
            statement.executeUpdate(sql);
            statement.executeUpdate(auto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
   }


}
