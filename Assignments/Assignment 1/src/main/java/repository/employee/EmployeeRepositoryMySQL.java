package repository.employee;

import model.Client;

import model.builder.ClientBuilder;
import repository.EntityNotFoundException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryMySQL implements EmployeeRepository {

    private final Connection connection;

    public EmployeeRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<Client>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM client";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                clients.add(getClientsFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    private Client getClientsFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setCardNumber(rs.getString("card_number"))
                .setPnc(rs.getString("pnc"))
                .setAddress(rs.getString("address"))
                .build();
    }

    @Override
    public boolean saveClient(Client client) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, client.getName());
            insertUserStatement.setString(2, client.getCard_number());
            insertUserStatement.setString(3, client.getPnc());
            insertUserStatement.setString(4, client.getAddress());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            client.setId(userId);

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean removeClient(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where id = " + id;
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateClientName(Client client) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE client SET name = ? WHERE id = " + client.getId());
            insertUserStatement.setString(1, client.getName());
            insertUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Client findById(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getClientsFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(id, Client.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Client.class.getSimpleName());
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where id >= 0";
            String auto = "ALTER TABLE client AUTO_INCREMENT = 1";
            statement.executeUpdate(sql);
            statement.executeUpdate(auto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
