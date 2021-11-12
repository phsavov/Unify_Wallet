package Unify;

import javax.swing.*;
import java.sql.SQLException;

public class Receive extends Transaction {
    // this class is a child of transaction because it is a feature of the
    // transaction class
    public String walletAddress;
    public Send unspentTransactionOutput;

    /**
     *
     * @param walletAddress
     * @throws SQLException
     */
    // this is the constructor method that initializes the class and stores the wallet address inorder
    // to receive crypto
    public Receive(String walletAddress) throws SQLException {
        super();
        this.walletAddress = walletAddress;
    }
}
