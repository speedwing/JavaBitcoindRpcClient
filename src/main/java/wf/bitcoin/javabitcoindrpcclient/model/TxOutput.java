package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;

public interface TxOutput extends Serializable {

    String address();

    double amount();
}
