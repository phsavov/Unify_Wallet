package Unify;

import java.sql.*;

public class TransactionDatabase {
    /**
     * these are the current connection strings to our azure sql database, which will change because we will merge the table from one database to the other
     */
    Connection sending = DriverManager.getConnection("jdbc:sqlserver://unify-ledger.database.windows.net:1433;database=User;user=phsavov@unify-ledger;password=PhiLeTo2001BL;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
    //Connection receiving = DriverManager.getConnection("jdbc:sqlserver://unify-ledger.database.windows.net:1433;database=User;user=phsavov@unify-ledger;password=PhiLeTo2001BL;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
    Connection transaction = DriverManager.getConnection("jdbc:sqlserver://unify-ledger.database.windows.net:1433;database=Ledger;user=phsavov@unify-ledger;password=PhiLeTo2001BL;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

    private double amount;
    private String address;
    User sendingUser;

    /**
     * this is one wat to construct the transaction database class where you pass everything in the beginning
     * @param amount
     * @param address
     * @param sendingUser
     * @throws SQLException
     */
    public TransactionDatabase(double amount, String address, User sendingUser) throws SQLException {
        this.amount = amount;
        this.address = address;
        this.sendingUser = sendingUser;
    }

    /**
     * another basic constructor class
     * @throws SQLException
     */
    public TransactionDatabase() throws SQLException {

    }

    /**
     * The sending Crypto method is created to send ADA from one user to the other user
     * this is done by sending some quires to the database and from there the account totals
     * in the database will be updated
     * @param amount: double
     * @param address: String
     * @param sendingUser: User
     * @return boolean
     * @throws SQLException
     */
    public boolean sendingCrypto(double amount, String address, User sendingUser) throws SQLException {

        sending.createStatement(); // creating a query statement
        //Statement receive = receiving.createStatement();
        String getSending = "update Users set accountTotal= ? where accountID = ?"; // this is the query that will be sent
        PreparedStatement prep1 = sending.prepareStatement(getSending); // creating a prepared statement

        // Check if sending amount is more than account balance
        if ((sendingUser.getAccountTotal() - amount) < 0) {
            System.out.println("Sending amount more than account balance!");
            return false;
        }
        double newTotal = sendingUser.getAccountTotal() - amount; // this is calculating the new account total

        // because prepared statements are like printf, we are setting which items go where in the getSending string
        prep1.setString(1, String.valueOf((newTotal)));
        prep1.setString(2, String.valueOf(sendingUser.getAccountID()));
        prep1.executeUpdate(); // executing the query
        sendingUser.setAccountTotal(sendingUser.getAccountTotal() - amount); // setting the account total locally

        // getting the account of the receiving user through a query
        String getReceivingUser = "Select * from Users where receivingAddress = ?";
        PreparedStatement prep2 = sending.prepareStatement(getReceivingUser);
        prep2.setString(1, address);
        ResultSet tempUser = prep2.executeQuery();
        tempUser.next();

        // creating a temporary user that holds the receiving user information to run another query to be able to finalize the transaction
        User temporary  = new User(tempUser.getInt(1), tempUser.getString(4),
                tempUser.getString(5), tempUser.getString(6), tempUser.getDouble(3));

        // creating a new prepared statement and query string and setting the variables
        String getCrypto = "update Users set accountTotal= ? where accountID = ?";
        prep2 = sending.prepareStatement(getCrypto);
        newTotal = temporary.getAccountTotal() + amount;
        prep2.setString(1, String.valueOf((newTotal)));
        prep2.setString(2, String.valueOf(temporary.getAccountID()));
        prep2.executeUpdate(); // executing the query to update the database

        updateTransactionDB(sendingUser, temporary, amount); // this method updates the ledger table so that a history of transactions can be kept

        // because the process was completed return true
        return true;
    }

    /**
     * Inserts the transaction from the sender and receiver point of view to the ledger table to keep a history
     * of transactions
     * @param sendingUser: User
     * @param receivingUser: User
     * @throws SQLException
     */
    public void updateTransactionDB(User sendingUser, User receivingUser, double amount) throws SQLException {

        /**
         * this first section creates a statement and then writes the string query that then becomes a prepared statement
         * which then the variables are set by some .setString statements and then the query is executed
         */
        transaction.createStatement();
        String query = "insert into Transactions (accountID, transactionType, ammount, receievingAccountID) " +
                "values (?, ?, ?, ?);";
        PreparedStatement statement = transaction.prepareStatement(query);
        statement.setString(1, String.valueOf(sendingUser.getAccountID()));
        statement.setString(2, "SENDING");
        statement.setString(3, String.valueOf(amount));
        statement.setString(4, String.valueOf(receivingUser.getAccountID()));
        statement.executeUpdate();

        String query2 = "insert into Transactions (accountID, transactionType, ammount, receievingAccountID) " +
                "values (?, ?, ?, ?);";

        /**
         * this second section creates a statement and then writes the string query that then becomes a prepared statement
         * which then the variables are set by some .setString statements and then the query is executed
         */

        PreparedStatement newStatement = transaction.prepareStatement(query2);
        newStatement.setString(1, String.valueOf(receivingUser.getAccountID()));
        newStatement.setString(2, "RECEIVING");
        newStatement.setString(3, String.valueOf(amount));
        newStatement.setString(4, String.valueOf(sendingUser.getAccountID()));
        newStatement.executeUpdate();
    }
}
