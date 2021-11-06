package Unify;
import java.sql.*;

public class UserDatabase {
    public Connection connection;
    public Statement statement;

    public UserDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://unify-ledger.database.windows.net:1433;database=User;user=phsavov@unify-ledger;password=PhiLeTo2001BL;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

    }

    public boolean checkUsername(String username) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "Select * from Users where userName = ?";
        PreparedStatement prepStatement = connection.prepareStatement(query);
        prepStatement.setString(1,username);
        ResultSet result = prepStatement.executeQuery();
        //ResultSet result = statement.executeQuery("SELECT * FROM Users WHERE userName LIKE '"+username+"'");
        result.next();
        String user = result.getString(6);
        if (user.equals(username)){
            return true;
        }
        return false;
    }

    public boolean checkPassword(String password) throws SQLException {
        return true;
    }


}