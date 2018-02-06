package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;

public interface ReceivedAddress extends Serializable {

    String address();

    String account();

    double amount();

    int confirmations();
}
