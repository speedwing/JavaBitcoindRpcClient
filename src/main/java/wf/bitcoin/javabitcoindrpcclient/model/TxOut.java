package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface TxOut extends Serializable {
    String bestBlock();

    long confirmations();

    BigDecimal value();

    String asm();

    String hex();

    long reqSigs();

    String type();

    List<String> addresses();

    long version();

    boolean coinBase();

}
