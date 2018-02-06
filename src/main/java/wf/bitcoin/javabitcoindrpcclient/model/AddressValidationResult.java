package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;

public interface AddressValidationResult extends Serializable {

    boolean isValid();

    String address();

    boolean isMine();

    boolean isScript();

    String pubKey();

    boolean isCompressed();

    String account();
}
