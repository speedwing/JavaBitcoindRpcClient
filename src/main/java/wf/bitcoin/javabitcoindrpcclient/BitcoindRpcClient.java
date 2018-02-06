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

import wf.bitcoin.javabitcoindrpcclient.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BitcoindRpcClient {

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

    TxOutSetInfo getTxOutSetInfo();

    WalletInfo getWalletInfo();

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

    List<ReceivedAddress> listReceivedByAddress() throws BitcoinRpcRuntimeException;

    List<ReceivedAddress> listReceivedByAddress(int minConf) throws BitcoinRpcRuntimeException;

    List<ReceivedAddress> listReceivedByAddress(int minConf, boolean includeEmpty) throws BitcoinRpcRuntimeException;

    TransactionsSinceBlock listSinceBlock() throws BitcoinRpcRuntimeException;

    TransactionsSinceBlock listSinceBlock(String blockHash) throws BitcoinRpcRuntimeException;

    TransactionsSinceBlock listSinceBlock(String blockHash, int targetConfirmations) throws BitcoinRpcRuntimeException;

    List<Transaction> listTransactions() throws BitcoinRpcRuntimeException;

    List<Transaction> listTransactions(String account) throws BitcoinRpcRuntimeException;

    List<Transaction> listTransactions(String account, int count) throws BitcoinRpcRuntimeException;

    List<Transaction> listTransactions(String account, int count, int from) throws BitcoinRpcRuntimeException;

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
