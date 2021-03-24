package repository.account;

import model.Account;
import model.builder.AccountBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean saveAccount( Account account) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, account.getIdent_number());
            insertUserStatement.setString(2, account.getType());
            insertUserStatement.setFloat(3, account.getBalance());
            insertUserStatement.setDate(4, account.getCreation_date());
            insertUserStatement.setLong(5, account.getId_client());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long accountId = rs.getLong(1);
            account.setId(accountId);

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeAccount(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id = " + id;
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Account> findAllAccounts() {
        List<Account> accounts = new ArrayList<Account>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getAccountsFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public boolean updateBalance(Account account) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE account SET balance = ? WHERE id =" + account.getId());
            insertUserStatement.setFloat(1, account.getBalance());
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
            String sql = "DELETE from account where id >= 0";
            String auto = "ALTER TABLE account AUTO_INCREMENT = 1";
            statement.executeUpdate(sql);
            statement.executeUpdate(auto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> findAccountsByClientId(Long id) {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where idclient=" + id;
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                accounts.add(getAccountsFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return accounts;
        }
        return accounts;
    }

    private Account getAccountsFromResultSet(ResultSet rs) throws SQLException{
        return new AccountBuilder()
                .setId(rs.getLong("id"))
                .setIdentNumber(rs.getString("ident_number"))
                .setType(rs.getString("type"))
                .setBalance(rs.getFloat("balance"))
                .setCreationDate(rs.getDate("creation_date"))
                .setIdClient(rs.getLong("id_client"))
                .build();
    }
}
