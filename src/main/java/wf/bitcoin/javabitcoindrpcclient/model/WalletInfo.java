package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;
import java.math.BigDecimal;

public interface WalletInfo extends Serializable {

    long walletVersion();

    BigDecimal balance();

    BigDecimal unconfirmedBalance();

    BigDecimal immatureBalance();

    long txCount();

    long keyPoolOldest();

    long keyPoolSize();

    long unlockedUntil();

    BigDecimal payTxFee();

    String hdMasterKeyId();
}
