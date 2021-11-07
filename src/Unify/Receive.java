package Unify;

import javax.swing.*;
import java.sql.SQLException;

public class Receive extends Transaction {
    public String walletAddress;
    public Send unspentTransactionOutput;


    public Receive(String walletAddress) throws SQLException {
        super();
        this.walletAddress = walletAddress;
    }
}
