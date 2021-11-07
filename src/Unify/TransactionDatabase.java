package Unify;

import java.sql.*;

public class TransactionDatabase {

    Connection sending = DriverManager.getConnection("jdbc:sqlserver://unify-ledger.database.windows.net:1433;database=User;user=phsavov@unify-ledger;password=PhiLeTo2001BL;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
    Connection receiving = DriverManager.getConnection("jdbc:sqlserver://unify-ledger.database.windows.net:1433;database=User;user=phsavov@unify-ledger;password=PhiLeTo2001BL;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
    Connection transaction = DriverManager.getConnection("jdbc:sqlserver://unify-ledger.database.windows.net:1433;database=Ledger;user=phsavov@unify-ledger;password=PhiLeTo2001BL;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

    private double amount;
    private String address;
    User sendingUser;

    // Default constructor
    public TransactionDatabase(double amount, String address, User sendingUser) throws SQLException {
        this.amount = amount;
        this.address = address;
        this.sendingUser = sendingUser;
    }

    public TransactionDatabase() throws SQLException {

    }

    /**
     * Add a transaction to the Transaction database
     * @param amount: double
     * @param address: String
     * @param sendingUser: User
     * @return boolean
     * @throws SQLException
     */
    public boolean sendingCrypto(double amount, String address, User sendingUser) throws SQLException {
        Statement send = sending.createStatement();
        Statement receive = receiving.createStatement();
        String getSending = "update Users set accountTotal = ? where accountID = ?";
        PreparedStatement prep1 = sending.prepareStatement(getSending);
        
        // Check if sending amount is more than account balance
        if ((sendingUser.getAccountTotal() - amount) < 0) {
            System.out.println("Sending amount more than account balance!");
            return false;
        }
        
        prep1.setString(1, String.valueOf((sendingUser.getAccountTotal() - amount)));
        prep1.setString(2, String.valueOf(sendingUser.getAccountID()));
        prep1.executeUpdate();
        sendingUser.setAccountTotal(sendingUser.getAccountTotal() - amount);

        String getReceivingUser = "Select * from Users where receivingAddress = ?";
        PreparedStatement prep2 = receiving.prepareStatement(getReceivingUser);
        prep2.setString(1, address);
        ResultSet tempUser = prep2.executeQuery();
        tempUser.next();

        User temporary  = new User(tempUser.getInt(1), tempUser.getString(4),
                tempUser.getString(5), tempUser.getString(6), tempUser.getDouble(3));

        String getCrypto = "update Users set accountTotal = ? where accountID = ?";
        prep2 = receiving.prepareStatement(getCrypto);
        prep2.setString(1, String.valueOf((temporary.getAccountTotal() + amount)));
        prep2.setString(2, String.valueOf(sendingUser.getAccountID()));
        prep1.executeUpdate();

        updateTransactionDB(sendingUser, temporary, amount);

        sendingUser.setAccountTotal(sendingUser.getAccountTotal() - amount);

        return true;
    }

    /**
     * Inserts a new Transaction into the Transaction database
     * @param sendingUser: User
     * @param receivingUser: User
     * @throws SQLException
     */
    public void updateTransactionDB(User sendingUser, User receivingUser, double amount) throws SQLException {


        Statement update = transaction.createStatement();
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

        PreparedStatement newStatement = transaction.prepareStatement(query2);
        newStatement.setString(1, String.valueOf(receivingUser.getAccountID()));
        newStatement.setString(2, "RECEIVING");
        newStatement.setString(3, String.valueOf(amount));
        newStatement.setString(4, String.valueOf(sendingUser.getAccountID()));
        newStatement.executeUpdate(query);
    }
}
