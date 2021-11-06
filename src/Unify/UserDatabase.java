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
        
        return new User(result.getString(1), result.getString(6), result.getString(7), 
                result.getString(8), result.getInt(5));
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

    public boolean checkPassword(String username, String password) throws SQLException {
        statement = connection.createStatement();
        String query = "Select * from Users where userName = ? and password = ?";
        PreparedStatement prepStatement = connection.prepareStatement(query);
        prepStatement.setString(1,username);
        prepStatement.setString(2,password);
        ResultSet result = prepStatement.executeQuery();
        result.next();
        if (result.getString(6).equals(username) && result.getString(7).equals(password)){
            return true;
        }
        return false;
    }



}