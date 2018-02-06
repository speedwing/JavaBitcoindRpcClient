package wf.bitcoin.javabitcoindrpcclient.model;

public class BasicTxOutput implements TxOutput {

    String address;
    double amount;

    public BasicTxOutput(String address, double amount) {
        this.address = address;
        this.amount = amount;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public double amount() {
        return amount;
    }
}
