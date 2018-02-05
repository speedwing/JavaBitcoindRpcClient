/*
 * BitcoindRpcClient-JSON-RPC-Client License
 * 
 * Copyright (c) 2013, Mikhail Yevchenko.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the 
 * Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject
 * to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
 /*
 * Repackaged with simple additions for easier maven usage by Alessandro Polverini
 */
package wf.bitcoin.javabitcoindrpcclient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BitcoindRpcClient {

    /* Missing methods:
     getblocktemplate ( "jsonrequestobject" )
     *getgenerate
     *gethashespersec
     *getwork ( "data" )
     help ( "command" )
     *listaddressgroupings
     *listlockunspent
     (DEPRECATED) listreceivedbyaccount ( minconf includeempty )
     lockunspent unlock [{"txid":"txid","vout":n},...]
     sendmany "fromaccount" {"address":amount,...} ( minconf "comment" )
     (DEPRECATED) setaccount "bitcoinaddress" "account"
     */
    interface TxInput extends Serializable {

        String txid();

        int vout();

        String scriptPubKey();
    }

    class BasicTxInput implements TxInput {

        String txid;
        int vout;
        String scriptPubKey;

        BasicTxInput(String txid, int vout) {
            this.txid = txid;
            this.vout = vout;
        }

        BasicTxInput(String txid, int vout, String scriptPubKey) {
            this(txid, vout);
            this.scriptPubKey = scriptPubKey;
        }

        @Override
        public String txid() {
            return txid;
        }

        @Override
        public int vout() {
            return vout;
        }

        @Override
        public String scriptPubKey() {
            return scriptPubKey;
        }

    }

    class ExtendedTxInput extends BasicTxInput {

        String redeemScript;
        BigDecimal amount;

        public ExtendedTxInput(String txid, int vout) {
            super(txid, vout);
        }

        public ExtendedTxInput(String txid, int vout, String scriptPubKey) {
            super(txid, vout, scriptPubKey);
        }

        public ExtendedTxInput(String txid, int vout, String scriptPubKey, String redeemScript, BigDecimal amount) {
            super(txid, vout, scriptPubKey);
            this.redeemScript = redeemScript;
            this.amount = amount;
        }

        String redeemScript() {
            return redeemScript;
        }

        BigDecimal amount() {
            return amount;
        }

    }

    interface TxOutput extends Serializable {

        String address();

        double amount();
    }

    class BasicTxOutput implements TxOutput {

        String address;
        double amount;

        BasicTxOutput(String address, double amount) {
            this.address = address;
            this.amount = amount;
        }

        @Override
        public String address() {
            return address;
        }

        @Override
        public double amount() {
            return amount;
        }
    }

    String createRawTransaction(List<TxInput> inputs, List<TxOutput> outputs) throws BitcoinRpcRuntimeException;

    String dumpPrivKey(String address) throws BitcoinRpcRuntimeException;

    String getAccount(String address) throws BitcoinRpcRuntimeException;

    String getAccountAddress(String address) throws BitcoinRpcRuntimeException;

    List<String> getAddressesByAccount(String account) throws BitcoinRpcRuntimeException;

    double getBalance() throws BitcoinRpcRuntimeException;

    double getBalance(String account) throws BitcoinRpcRuntimeException;

    double getBalance(String account, int minConf) throws BitcoinRpcRuntimeException;

    Info getInfo() throws BitcoinRpcRuntimeException;

    MiningInfo getMiningInfo() throws BitcoinRpcRuntimeException;

    MultiSig createMultiSig(int nRequired, List<String> keys) throws BitcoinRpcRuntimeException;

    NetworkInfo getNetworkInfo() throws BitcoinRpcRuntimeException;

    interface Info extends Serializable {

        long version();

        long protocolVersion();

        long walletVersion();

        double balance();

        int blocks();

        int timeOffset();

        int connections();

        String proxy();

        double difficulty();

        boolean testnet();

        long keyPoolOldest();

        long keyPoolSize();

        double payTxFee();

        double relayFee();

        String errors();
    }

    interface MiningInfo extends Serializable {

        int blocks();

        int currentBlockSize();

        int currentBlockWeight();

        int currentBlockTx();

        double difficulty();

        String errors();

        double networkHashps();

        int pooledTx();

        boolean testNet();

        String chain();
    }

    interface NetTotals extends Serializable {

        long totalBytesRecv();

        long totalBytesSent();

        long timeMillis();

        interface uploadTarget extends Serializable {

            long timeFrame();

            int target();

            boolean targetReached();

            boolean serveHistoricalBlocks();

            long bytesLeftInCycle();

            long timeLeftInCycle();
        }

        uploadTarget uploadTarget();
    }

    interface BlockChainInfo extends Serializable {

        String chain();

        int blocks();

        String bestBlockHash();

        double difficulty();

        double verificationProgress();

        String chainWork();
    }

    interface DecodedScript extends Serializable {

        String asm();

        String hex();

        String type();

        int reqSigs();

        List<String> addresses();

        String p2sh();
    }

    TxOutSetInfo getTxOutSetInfo();

    WalletInfo getWalletInfo();

    interface WalletInfo extends Serializable {

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

    interface NetworkInfo extends Serializable {

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

    interface Network extends Serializable {

        String name();

        boolean limited();

        boolean reachable();

        String proxy();

        boolean proxyRandomizeCredentials();
    }

    interface MultiSig extends Serializable {

        String address();

        String redeemScript();
    }

    interface NodeInfo extends Serializable {

        String addedNode();

        boolean connected();

        List<Address> addresses();

    }

    interface Address extends Serializable {

        String address();

        String connected();
    }

    interface TxOut extends Serializable {
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

    interface Block extends Serializable {

        String hash();

        int confirmations();

        int size();

        int height();

        int version();

        String merkleRoot();

        List<String> tx();

        Date time();

        long nonce();

        String bits();

        double difficulty();

        String previousHash();

        String nextHash();

        String chainwork();

        Block previous() throws BitcoinRpcRuntimeException;

        Block next() throws BitcoinRpcRuntimeException;
    }

    interface TxOutSetInfo extends Serializable {

        long height();

        String bestBlock();

        long transactions();

        long txouts();

        long bytesSerialized();

        String hashSerialized();

        BigDecimal totalAmount();
    }

    Block getBlock(int height) throws BitcoinRpcRuntimeException;

    Block getBlock(String blockHash) throws BitcoinRpcRuntimeException;

    String getBlockHash(int height) throws BitcoinRpcRuntimeException;

    BlockChainInfo getBlockChainInfo() throws BitcoinRpcRuntimeException;

    int getBlockCount() throws BitcoinRpcRuntimeException;

    String getNewAddress() throws BitcoinRpcRuntimeException;

    String getNewAddress(String account) throws BitcoinRpcRuntimeException;

    List<String> getRawMemPool() throws BitcoinRpcRuntimeException;

    String getBestBlockHash() throws BitcoinRpcRuntimeException;

    String getRawTransactionHex(String txId) throws BitcoinRpcRuntimeException;

    interface RawTransaction extends Serializable {

        String hex();

        String txId();

        int version();

        long lockTime();

        long size();

        long vsize();

        String hash();

        interface In extends TxInput, Serializable {

            Map<String, Object> scriptSig();

            long sequence();

            RawTransaction getTransaction();

            Out getTransactionOutput();
        }

        List<In> vIn(); // TODO : Create special interface instead of this

        interface Out extends Serializable {

            double value();

            int n();

            interface ScriptPubKey extends Serializable {

                String asm();

                String hex();

                int reqSigs();

                String type();

                List<String> addresses();
            }

            ScriptPubKey scriptPubKey();

            TxInput toInput();

            RawTransaction transaction();
        }

        List<Out> vOut(); // TODO : Create special interface instead of this

        String blockHash();

        int confirmations();

        Date time();

        Date blocktime();
    }

    RawTransaction getRawTransaction(String txId) throws BitcoinRpcRuntimeException;

    double getReceivedByAddress(String address) throws BitcoinRpcRuntimeException;

    /**
     * Returns the total amount received by &lt;bitcoinaddress&gt; in transactions
     * with at least [minconf] confirmations. While some might consider this
     * obvious, value reported by this only considers *receiving* transactions. It
     * does not check payments that have been made *from* this address. In other
     * words, this is not "getaddressbalance". Works only for addresses in the
     * local wallet, external addresses will always show 0.
     *
     * @param address a
     * @param minConf a
     * @return the total amount received by &lt;bitcoinaddress&gt;
     */
    double getReceivedByAddress(String address, int minConf) throws BitcoinRpcRuntimeException;

    void importPrivKey(String bitcoinPrivKey) throws BitcoinRpcRuntimeException;

    void importPrivKey(String bitcoinPrivKey, String label) throws BitcoinRpcRuntimeException;

    void importPrivKey(String bitcoinPrivKey, String label, boolean rescan) throws BitcoinRpcRuntimeException;

    Object importAddress(String address, String label, boolean rescan) throws BitcoinRpcRuntimeException;

    /**
     * listaccounts [minconf=1]
     *
     * @return Map that has account names as keys, account balances as values
     * @throws BitcoinRpcRuntimeException a
     */
    Map<String, Number> listAccounts() throws BitcoinRpcRuntimeException;

    Map<String, Number> listAccounts(int minConf) throws BitcoinRpcRuntimeException;

    static interface ReceivedAddress extends Serializable {

        String address();

        String account();

        double amount();

        int confirmations();
    }

    List<ReceivedAddress> listReceivedByAddress() throws BitcoinRpcRuntimeException;

    List<ReceivedAddress> listReceivedByAddress(int minConf) throws BitcoinRpcRuntimeException;

    List<ReceivedAddress> listReceivedByAddress(int minConf, boolean includeEmpty) throws BitcoinRpcRuntimeException;

    /**
     * returned by listsinceblock and listtransactions
     */
    static interface Transaction extends Serializable {

        String account();

        String address();

        String category();

        double amount();

        double fee();

        int confirmations();

        String blockHash();

        int blockIndex();

        Date blockTime();

        String txId();

        Date time();

        Date timeReceived();

        String comment();

        String commentTo();

        RawTransaction raw();
    }

    static interface TransactionsSinceBlock extends Serializable {

        List<Transaction> transactions();

        String lastBlock();
    }

    TransactionsSinceBlock listSinceBlock() throws BitcoinRpcRuntimeException;

    TransactionsSinceBlock listSinceBlock(String blockHash) throws BitcoinRpcRuntimeException;

    TransactionsSinceBlock listSinceBlock(String blockHash, int targetConfirmations) throws BitcoinRpcRuntimeException;

    List<Transaction> listTransactions() throws BitcoinRpcRuntimeException;

    List<Transaction> listTransactions(String account) throws BitcoinRpcRuntimeException;

    List<Transaction> listTransactions(String account, int count) throws BitcoinRpcRuntimeException;

    List<Transaction> listTransactions(String account, int count, int from) throws BitcoinRpcRuntimeException;

    interface Unspent extends TxInput, TxOutput, Serializable {

        @Override
        String txid();

        @Override
        int vout();

        @Override
        String address();

        String account();

        String scriptPubKey();

        @Override
        double amount();

        int confirmations();
    }

    List<Unspent> listUnspent() throws BitcoinRpcRuntimeException;

    List<Unspent> listUnspent(int minConf) throws BitcoinRpcRuntimeException;

    List<Unspent> listUnspent(int minConf, int maxConf) throws BitcoinRpcRuntimeException;

    List<Unspent> listUnspent(int minConf, int maxConf, String... addresses) throws BitcoinRpcRuntimeException;

    String move(String fromAccount, String toBitcoinAddress, double amount) throws BitcoinRpcRuntimeException;

    String move(String fromAccount, String toBitcoinAddress, double amount, int minConf) throws BitcoinRpcRuntimeException;

    String move(String fromAccount, String toBitcoinAddress, double amount, int minConf, String comment) throws BitcoinRpcRuntimeException;

    String sendFrom(String fromAccount, String toBitcoinAddress, double amount) throws BitcoinRpcRuntimeException;

    String sendFrom(String fromAccount, String toBitcoinAddress, double amount, int minConf) throws BitcoinRpcRuntimeException;

    String sendFrom(String fromAccount, String toBitcoinAddress, double amount, int minConf, String comment) throws BitcoinRpcRuntimeException;

    /**
     * Will send the given amount to the given address, ensuring the account has a
     * valid balance using minConf confirmations.
     *
     * @param fromAccount      s
     * @param toBitcoinAddress s
     * @param amount           is a real and is rounded to 8 decimal places
     * @param minConf          s
     * @param comment          s
     * @param commentTo        sn
     * @return the transaction ID if successful
     * @throws BitcoinRpcRuntimeException a
     */
    String sendFrom(String fromAccount, String toBitcoinAddress, double amount, int minConf, String comment, String commentTo) throws BitcoinRpcRuntimeException;

    String sendRawTransaction(String hex) throws BitcoinRpcRuntimeException;

    String sendToAddress(String toAddress, double amount) throws BitcoinRpcRuntimeException;

    String sendToAddress(String toAddress, double amount, String comment) throws BitcoinRpcRuntimeException;

    /**
     * @param toAddress a
     * @param amount    is a real and is rounded to 8 decimal places
     * @param comment   a
     * @param commentTo a
     * @return the transaction ID &lt;txid&gt; if successful
     * @throws BitcoinRpcRuntimeException a
     */
    String sendToAddress(String toAddress, double amount, String comment, String commentTo) throws BitcoinRpcRuntimeException;

    String signRawTransaction(String hex, List<ExtendedTxInput> inputs, List<String> privateKeys) throws BitcoinRpcRuntimeException;

    static interface AddressValidationResult extends Serializable {

        boolean isValid();

        String address();

        boolean isMine();

        boolean isScript();

        String pubKey();

        boolean isCompressed();

        String account();
    }

    /**
     * @param doGenerate a boolean indicating if blocks must be generated with the
     *                   cpu
     * @throws BitcoinRPCException a
     */
    void setGenerate(boolean doGenerate) throws BitcoinRPCException;

    /**
     * Used in regtest mode to generate an arbitrary number of blocks
     *
     * @param numBlocks a boolean indicating if blocks must be generated with the
     *                  cpu
     * @return the list of hashes of the generated blocks
     * @throws BitcoinRPCException a
     */
    List<String> generate(int numBlocks) throws BitcoinRPCException;

    AddressValidationResult validateAddress(String address) throws BitcoinRpcRuntimeException;

    double getEstimateFee(int nBlocks) throws BitcoinRpcRuntimeException;

    double getEstimatePriority(int nBlocks) throws BitcoinRpcRuntimeException;

    /**
     * In regtest mode, invalidates a block to create an orphan chain
     *
     * @param hash a
     * @throws BitcoinRpcRuntimeException a
     */
    void invalidateBlock(String hash) throws BitcoinRpcRuntimeException;

    /**
     * In regtest mode, undo the invalidation of a block, possibly making it on
     * the top of the chain
     *
     * @param hash a
     * @throws BitcoinRpcRuntimeException a
     */
    void reconsiderBlock(String hash) throws BitcoinRpcRuntimeException;

    static interface PeerInfoResult extends Serializable {

        long getId();

        String getAddr();

        String getAddrLocal();

        String getServices();

        long getLastSend();

        long getLastRecv();

        long getBytesSent();

        long getBytesRecv();

        long getConnTime();

        int getTimeOffset();

        double getPingTime();

        long getVersion();

        String getSubVer();

        boolean isInbound();

        int getStartingHeight();

        long getBanScore();

        int getSyncedHeaders();

        int getSyncedBlocks();

        boolean isWhiteListed();
    }

    List<PeerInfoResult> getPeerInfo();

    void stop();

    String getRawChangeAddress();

    long getConnectionCount();

    double getUnconfirmedBalance();

    double getDifficulty();

    void ping();

    DecodedScript decodeScript(String hex);

    NetTotals getNetTotals();

    boolean getGenerate();

    double getNetworkHashPs();

    boolean setTxFee(BigDecimal amount);

    void addNode(String node, String command);

    void backupWallet(String destination);

    String signMessage(String bitcoinAdress, String message);

    void dumpWallet(String filename);

    void importWallet(String filename);

    void keyPoolRefill();

    BigDecimal getReceivedByAccount(String account);

    void encryptWallet(String passPhrase);

    void walletPassPhrase(String passPhrase, long timeOut);

    boolean verifyMessage(String bitcoinAddress, String signature, String message);

    String addMultiSigAddress(int nRequired, List<String> keyObject);

    String addMultiSigAddress(int nRequired, List<String> keyObject, String account);

    boolean verifyChain();

    List<NodeInfo> getAddedNodeInfo(boolean dummy, String node);

    void submitBlock(String hexData);

    TxOut getTxOut(String txId, long vout);

}
