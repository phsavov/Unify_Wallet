package Unify;

public class Receive extends Transaction {
    public String walletAddress;
    public Send unspentTransactionOutput;

    public Receive(String walletAddress) {
        this.walletAddress = walletAddress;
    }
}
