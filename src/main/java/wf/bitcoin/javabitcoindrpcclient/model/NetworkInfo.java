package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface NetworkInfo extends Serializable {

    long version();

    String subversion();

    long protocolVersion();

    String localServices();

    boolean localRelay();

    long timeOffset();

    long connections();

    List<Network> networks();

    BigDecimal relayFee();

    List<String> localAddresses();

    String warnings();
}
