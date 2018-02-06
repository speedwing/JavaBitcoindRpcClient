package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;
import java.util.List;

public interface TransactionsSinceBlock extends Serializable {

    List<Transaction> transactions();

    String lastBlock();
}
