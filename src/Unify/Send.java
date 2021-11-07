package Unify;
import javax.swing.*;
import java.security.PublicKey;
import java.sql.SQLException;

public class Send extends Transaction {
    public String    id;
    public PublicKey recipient; //new owner
    public double     value; //# of owed coin
    public String    parentTransactionID; //id of this transaction



    //Constructor
    public Send(PublicKey recipient, double value, String parentTransactionID, String id) throws SQLException {
        super();
        this.recipient           = recipient;
        this.value               = value;
        this.parentTransactionID = parentTransactionID;
        this.id                  = id;
    }

    public boolean isOwned(PublicKey publicKey){
        return (publicKey == recipient);
    }
}
