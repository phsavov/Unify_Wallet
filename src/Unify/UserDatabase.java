package Unify;
import java.sql.*;

public class UserDatabase {
    public Connection connection;
    public Statement statement;

    public UserDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://unify-ledger.database.windows.net:1433;database=User;user=phsavov@unify-ledger;password=PhiLeTo2001BL;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
    }

    public User getUserInfo(String username, String password) throws SQLException {
        statement = connection.createStatement();
        String query = "Select * from Users where userName = ? and password = ?";
        PreparedStatement prepStatement = connection.prepareStatement(query);
        prepStatement.setString(1,username);
        prepStatement.setString(2,password);
        ResultSet result = prepStatement.executeQuery();
        result.next();

        String accountID = result.getString(1);
        String userName = result.getString(6);
        String pass = result.getString(7);
        String spendPass = result.getString(8);
        Double total = result.getDouble(5);

        return new User(accountID, userName, pass, spendPass, total);
    }

    public boolean checkUsername(String username) throws SQLException {
        statement = connection.createStatement();
        String query = "Select * from Users where userName = ?";
        PreparedStatement prepStatement = connection.prepareStatement(query);
        prepStatement.setString(1,username);
        ResultSet result = prepStatement.executeQuery();
        result.next();
        if (result.getString(6).equals(username)){
            return true;
        }
        return false;
    }

    public boolean checkCredentials(String username, String password) throws SQLException {
        statement = connection.createStatement();
        String query = "Select * from Users where userName = ? and password = ?";
        PreparedStatement prepStatement = connection.prepareStatement(query);
        prepStatement.setString(1, username);
        prepStatement.setString(2, password);
        ResultSet result = prepStatement.executeQuery();
        result.next();
        if (result.getString(6).equals(username) && result.getString(7).equals(password)) {
            return true;
        }
        return false;
    }

}