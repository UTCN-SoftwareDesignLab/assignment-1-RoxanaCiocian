package repository.admin;

import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepositoryMySQL implements AdminRepository{

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;

    public AdminRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public boolean saveEmployee(User user) {
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

    @Override
    public List<User> viewEmployees(){
        List<User> employees = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT user.id, user.username, user.password, role.role FROM user INNER JOIN user_role ON user.id=user_role.user_id INNER JOIN role ON user_role.role_id=role.id WHERE user_role.role_id=2";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                employees.add(getUsersFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;

    }

    private User getUsersFromResultSet(ResultSet rs) throws SQLException {
        return new UserBuilder()
                .setId(rs.getLong("id"))
                .setPassword(rs.getString("password"))
                .setUsername(rs.getString("username"))
                .setRoles(rightsRolesRepository.findRolesForUser(rs.getLong("id")))
                .build();
    }

    @Override
    public boolean remove(Long id){
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id = " + id;
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateEmployeeUsername(User employee){
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE user SET username = ? WHERE id = " + employee.getId());
            insertUserStatement.setString(1, employee.getUsername());
            insertUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean updateEmployeePassword(User employee){
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE user SET password = ? WHERE id = " + employee.getId());
            insertUserStatement.setString(1, employee.getPassword());
            insertUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

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
