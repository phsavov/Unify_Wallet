package Unify;

public class Receive extends Transaction {
    public String id;
    public Send unspentTransactionOutput;

    public Receive(String id)
    {
        this.id = id;
    }
}
