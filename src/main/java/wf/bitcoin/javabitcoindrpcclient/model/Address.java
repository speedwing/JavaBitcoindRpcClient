package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;

public interface Address extends Serializable {

    String address();

    String connected();
}
