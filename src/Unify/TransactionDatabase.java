package Unify;

import java.sql.*;

public class TransactionDatabase {

    Connection sending = DriverManager.getConnection("jdbc:sqlserver://unify-ledger.database.windows.net:1433;database=User;user=phsavov@unify-ledger;password=PhiLeTo2001BL;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
    Connection receiving = DriverManager.getConnection("jdbc:sqlserver://unify-ledger.database.windows.net:1433;database=User;user=phsavov@unify-ledger;password=PhiLeTo2001BL;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
    Connection transaction = DriverManager.getConnection("jdbc:sqlserver://unify-ledger.database.windows.net:1433;database=Ledger;user=phsavov@unify-ledger;password=PhiLeTo2001BL;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

    private double amount;
    private String address;
    User sendingUser;

    public TransactionDatabase(double amount, String address, User sendingUser) throws SQLException {
        this.amount = amount;
        this.address = address;
        this.sendingUser = sendingUser;
    }

    public boolean sendingCrypto(double amount, String address, User sendingUser) throws SQLException {
        Statement send = sending.createStatement();
        Statement receive = receiving.createStatement();
        String getSending = "update Users set accoutTotal = ? where accountID = ?";
        PreparedStatement prep1 = sending.prepareStatement(getSending);
        prep1.setString(1, String.valueOf((sendingUser.getAccountTotal() - amount)));
        prep1.setString(2, sendingUser.getAccountID());
        ResultSet result1 = prep1.executeQuery();
        sendingUser.setAccountTotal(sendingUser.getAccountTotal() - amount);

        String getReceivingUser = "Select * from Users where receivingAdress = ?";
        PreparedStatement prep2 = receiving.prepareStatement(getReceivingUser);
        prep2.setString(1, address);
        ResultSet tempUser = prep2.executeQuery();
        tempUser.next();

        User temporary  = new User(tempUser.getString(1), tempUser.getString(6),
                tempUser.getString(7), tempUser.getString(8), tempUser.getDouble(5));

        String getCrypto = "update Users set accoutTotal = ? where accountID = ?";
        prep2 = receiving.prepareStatement(getCrypto);
        prep2.setString(1, String.valueOf((temporary.getAccountTotal() + amount)));
        prep2.setString(2, temporary.getAccountID());
        ResultSet sent = prep1.executeQuery();

        updateTransactionDB(sendingUser, temporary);

        return true;
    }

    public void updateTransactionDB(User sendingUser, User receivingUser) throws SQLException {
        Statement update = transaction.createStatement();
        String query = "insert into Transactions (accountID, transactionType, ammount, receievingAccountID) " +
                "values (= ?, = ?, = ?, =? );";
        PreparedStatement statement = transaction.prepareStatement(query);
        statement.setString(1, sendingUser.getAccountID());
        statement.setString(2, "SENDING");
        statement.setString(3, String.valueOf(sendingUser.getAccountTotal()));
        statement.setString(4, receivingUser.getAccountID());
        ResultSet set1 = statement.executeQuery();

        PreparedStatement newStatement = transaction.prepareStatement(query);
        statement.setString(1, receivingUser.getAccountID());
        statement.setString(2, "RECEIVING");
        statement.setString(3, String.valueOf(sendingUser.getAccountTotal()));
        statement.setString(4, sendingUser.getAccountID());
        ResultSet set2 = newStatement.executeQuery(query);
    }
}
