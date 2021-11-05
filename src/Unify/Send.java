package Unify;
import java.security.PublicKey;

public class Send extends Transaction {
    public String    id;
    public PublicKey recipient; //new owner
    public float     value; //# of owed coin
    public String    parentTransactionID; //id of this transaction

    //Constructor
    public Send(PublicKey recipient, float value, String parentTransactionID, String id)
    {
        this.recipient           = recipient;
        this.value               = value;
        this.parentTransactionID = parentTransactionID;
        this.id                  = id;
    }

    public boolean isOwned(PublicKey publicKey){
        return (publicKey == recipient);
    }
}
