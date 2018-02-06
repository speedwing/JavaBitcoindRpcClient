package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;
import java.math.BigDecimal;

public interface TxOutSetInfo extends Serializable {

    long height();

    String bestBlock();

    long transactions();

    long txouts();

    long bytesSerialized();

    String hashSerialized();

    BigDecimal totalAmount();
}
