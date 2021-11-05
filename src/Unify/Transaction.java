package Unify;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {


    public String    transactionID; //transaction hash
    public PublicKey recipient; //address for recipient
    public PublicKey sender; //address for sender
    public float     val;
    public byte[]    signature; //restricts access to only the user


    public ArrayList<Receive>  input  = new ArrayList<Receive>();
    public ArrayList<Send>     output = new ArrayList<Send>();


    //How many transactions have been created
    private static int transactionCount = 0;

    //Constructor
    public Transaction(PublicKey from, PublicKey to, float val, ArrayList<Receive> input)
    {
        this.sender    = from;
        this.recipient = to;
        this.val       = val;
        this.input     = input;
    }

    public Transaction(String[] args) {
    }

    public Transaction() {
    }

    public boolean processTransaction()
    {

        //Gather inputs
        /*for (Receive i : inputs){
            i.unspentTransactionOutput = Main.
        }*/
        return true;
    }

}
