package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;

public interface MultiSig extends Serializable {

    String address();

    String redeemScript();
}
