package Unify;
import javax.swing.*;
import java.security.PublicKey;
import java.sql.SQLException;

public class Send extends Transaction {
    /**
     * this class is another child class of the transaction class
     * the purpose of this one is to send other wallets crypto
     */
    public String    id;
    public PublicKey recipient; //new owner
    public double     value; //# of owed coin
    public String    parentTransactionID; //id of this transaction


    /**
     * @param recipient
     * @param value
     * @param parentTransactionID
     * @param id
     * @throws SQLException
     *
     * This is the constructor method that creates the 'send' class and stores all the nessesary information needed
     * to complete a transfer
     */
    public Send(PublicKey recipient, double value, String parentTransactionID, String id) throws SQLException {
        super();
        this.recipient           = recipient;
        this.value               = value;
        this.parentTransactionID = parentTransactionID;
        this.id                  = id;
    }

    /**
     * @param publicKey
     * @return
     * This is not currently in use because we are still developing the use case for a public key
     * to add more security to transactions
     */
    public boolean isOwned(PublicKey publicKey){
        return (publicKey == recipient);
    }
}
