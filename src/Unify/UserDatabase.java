package Unify;
import java.sql.*;

public class UserDatabase {

    public Connection connection;
    public Statement statement;

    /**
     * This is the default constructor of the class that also establishes a connection to the database
     * @throws SQLException
     */
    public UserDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://unify-ledger.database.windows.net:1433;database=User;user=phsavov@unify-ledger;password=PhiLeTo2001BL;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
    }

    //TODO add a method to change a users password (low priority)

    /**
     * This method returns the user based on the username and password
     * if the information enterd is correct then the user will be returned otherwise there will be a SQL exception
     * @param username: String
     * @param password: String
     * @return new User
     * @throws SQLException
     */
    public User getUserInfo(String username, String password) throws SQLException {
        // create the statement and then writing the string query to get the information
        // then getting the result of the query in the result list and returning the user object
        statement = connection.createStatement();
        String query = "Select * from Users where userName = ? and password = ?";
        PreparedStatement prepStatement = connection.prepareStatement(query);
        prepStatement.setString(1,username);
        prepStatement.setString(2,password);
        ResultSet result = prepStatement.executeQuery();
        result.next();

        int accountID = result.getInt(1);
        String userName = result.getString(4);
        String pass = result.getString(5);
        String spendPass = result.getString(6);
        Double total = result.getDouble(3);

        return new User(accountID, userName, pass, spendPass, total);
    }


    /**
     * Authenticates the user with their username and password
     * if the username and password are correct then the method will return true
     * otherwise false
     * @param username: String
     * @param password: String
     * @return boolean
     * @throws SQLException
     */
    public boolean checkCredentials(String username, String password) throws SQLException {
        // create the statement and then writing the string query to get the information
        // then getting the result of the query in the result list
        // then  comparing the username and password to see if they are correct
        statement = connection.createStatement();
        String query = "Select * from Users where userName = ? and password = ?";
        PreparedStatement prepStatement = connection.prepareStatement(query);
        prepStatement.setString(1, username);
        prepStatement.setString(2, password);
        ResultSet result = prepStatement.executeQuery();
        result.next();

        if (result.getString(4).equals(username) && result.getString(5).equals(password)) {
            return true;
        }
        return false;
    }

    /**
     * Creates a new unique accountID for the User
     * we determine the new accountID by getting the largest account ID number and
     * then adding one to it
     * @return newID: int
     * @throws SQLException
     */
    public int nextAccountId() throws SQLException {
        int newID;
        // the query gets the largest account id number and then just adding one to it is the new accountID
        statement = connection.createStatement();
        String query = "SELECT MAX(accountID) FROM Users"; //Query to get the greatest User account IDs in the User DB
        PreparedStatement prep = connection.prepareStatement(query);
        ResultSet result = prep.executeQuery();
        result.next(); //goes to the next result

        //System.out.println(result.getInt(1));
        newID = result.getInt(1) + 1;
        return newID; // returning the new accoount id
    }

    /**
     * Inserts a new User into the User database when the user makes a new account
     * @param user: User
     * @throws SQLException
     */
    public void updateUserDB(User user) throws SQLException {
        statement = connection.createStatement();
        String query = "INSERT INTO Users (accountID, accountTotal, userName, password, spendingPassword)"
                + "VALUES (?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(user.getAccountID()));
        preparedStatement.setString(2, String.valueOf(user.getAccountTotal()));
        preparedStatement.setString(3, user.getUsername());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getSpendingPassword());
        preparedStatement.executeUpdate();
    }

    /**
     * Modify the user password
     * @param user: User
     * @throws SQLException
     */
    public void changePassword(User user) throws SQLException {
        statement = connection.createStatement();
        String query = "UPDATE Users SET password = ? WHERE accountID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, String.valueOf(user.getPassword()));
        statement.setString(2, String.valueOf(user.getAccountID()));
        statement.executeUpdate();
    }

    /**
     * Inserts an amount of cardano into the User's account
     * @param user: User
     * @param amount: int
     * @return boolean
     * @throws SQLException
     */
    public boolean addFunds(User user, double amount) throws SQLException {
        statement = connection.createStatement();
        String query = "update Users set accountTotal = ? where accountID = ?";
        PreparedStatement prep = connection.prepareStatement(query);
        prep.setString(1, String.valueOf(user.getAccountTotal() + amount));
        prep.setString(2, String.valueOf(user.getAccountID()));
        prep.executeUpdate();
        user.setAccountTotal(user.getAccountTotal() + amount);
        return true;
    }

    /**
     * Updates the address of the user
     * @param user
     * @param address
     * @return boolean
     */
    public boolean updateAddress(User user, String address) throws SQLException {
        statement = connection.createStatement();
        String query = "update Users set receivingAddress = ? where accountID = ?";
        PreparedStatement prep = connection.prepareStatement(query);
        prep.setString(1, address);
        prep.setString(2, String.valueOf(user.getAccountID()));
        prep.executeUpdate();
        return true;
    }

    /**
     *
     * @param accountID
     * @return
     * @throws SQLException
     */

    public double getTotal(int accountID) throws SQLException {
        statement = connection.createStatement();
        String query = "select * from Users where accountID = ?";
        PreparedStatement prep = connection.prepareStatement(query);
        prep.setString(1, String.valueOf(accountID));
        ResultSet set = prep.executeQuery();
        set.next();
        return set.getDouble(3);
    }
}