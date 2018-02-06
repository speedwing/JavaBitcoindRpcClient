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

import wf.bitcoin.javabitcoindrpcclient.json.*;
import wf.bitcoin.javabitcoindrpcclient.model.*;
import wf.bitcoin.krotjson.Base64Coder;
import wf.bitcoin.krotjson.JSON;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mikhail Yevchenko m.ṥῥẚɱ.ѓѐḿởύḙ at azazar.com Small modifications by
 *         Alessandro Polverini polverini at gmail.com
 */
public class BitcoinJSONRPCClient implements BitcoindRpcClient {

    private static final Logger logger = Logger.getLogger(BitcoinJSONRPCClient.class.getCanonicalName());

    public static final Charset QUERY_CHARSET = Charset.forName("ISO8859-1");

    private static final URL DEFAULT_JSONRPC_URL;
    private static final URL DEFAULT_JSONRPC_TESTNET_URL;

    static {
        String user = "user";
        String password = "pass";
        String host = "localhost";
        String port = null;

        try {
            File f;
            File home = new File(System.getProperty("user.home"));

            if ((f = new File(home, ".bitcoin" + File.separatorChar + "bitcoin.conf")).exists()) {
            } else if ((f = new File(home, "AppData" + File.separatorChar + "Roaming" + File.separatorChar + "Bitcoin" + File.separatorChar + "bitcoin.conf")).exists()) {
            } else {
                f = null;
            }

            if (f != null) {
                logger.fine("Bitcoin configuration file found");

                Properties p = new Properties();
                try (FileInputStream i = new FileInputStream(f)) {
                    p.load(i);
                }

                user = p.getProperty("rpcuser", user);
                password = p.getProperty("rpcpassword", password);
                host = p.getProperty("rpcconnect", host);
                port = p.getProperty("rpcport", port);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        try {
            DEFAULT_JSONRPC_URL = new URL("http://" + user + ':' + password + "@" + host + ":" + (port == null ? "8332" : port) + "/");
            DEFAULT_JSONRPC_TESTNET_URL = new URL("http://" + user + ':' + password + "@" + host + ":" + (port == null ? "18332" : port) + "/");
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private final URL rpcURL;

    private URL noAuthURL;
    private String authStr;

    private WalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();

    public BitcoinJSONRPCClient(String rpcUrl) throws MalformedURLException {
        this(new URL(rpcUrl));
    }

    public BitcoinJSONRPCClient(URL rpc) {
        this.rpcURL = rpc;
        try {
            noAuthURL = new URI(rpc.getProtocol(), null, rpc.getHost(), rpc.getPort(), rpc.getPath(), rpc.getQuery(), null).toURL();
        } catch (MalformedURLException | URISyntaxException ex) {
            throw new IllegalArgumentException(rpc.toString(), ex);
        }
        authStr = rpc.getUserInfo() == null ? null : String.valueOf(Base64Coder.encode(rpc.getUserInfo().getBytes(Charset.forName("ISO8859-1"))));
    }

    public BitcoinJSONRPCClient(boolean testNet) {
        this(testNet ? DEFAULT_JSONRPC_TESTNET_URL : DEFAULT_JSONRPC_URL);
    }

    public BitcoinJSONRPCClient() {
        this(DEFAULT_JSONRPC_TESTNET_URL);
    }

    private HostnameVerifier hostnameVerifier = null;

    private SSLSocketFactory sslSocketFactory = null;

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    private static byte[] loadStream(InputStream in) throws IOException {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (; ; ) {
            int nr = in.read(buffer);

            if (nr == -1)
                break;
            if (nr == 0)
                throw new IOException("Read timed out");

            o.write(buffer, 0, nr);
        }
        return o.toByteArray();
    }

    public Object loadResponse(InputStream in, Object expectedID) throws IOException, BitcoinRpcRuntimeException {
        String r = new String(loadStream(in), QUERY_CHARSET);
        logger.log(Level.FINE, "Bitcoin JSON-RPC response:\n{0}", r);
        try {
            Map response = (Map) JSON.parse(r);

            if (!expectedID.equals(response.get("id")))
                throw new BitcoinRPCException("Wrong response ID (expected: " + String.valueOf(expectedID) + ", response: " + response.get("id") + ")");

            if (response.get("error") != null)
                throw new BitcoinRpcRuntimeException(JSON.stringify(response.get("error")));

            return response.get("result");
        } catch (ClassCastException ex) {
            throw new BitcoinRPCException("Invalid server response format (data: \"" + r + "\")");
        }
    }

    public Object query(String method, Object... o) throws BitcoinRpcRuntimeException {
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) noAuthURL.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (conn instanceof HttpsURLConnection) {
                if (hostnameVerifier != null)
                    ((HttpsURLConnection) conn).setHostnameVerifier(hostnameVerifier);
                if (sslSocketFactory != null)
                    ((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
            }

            conn.setRequestProperty("Authorization", "Basic " + authStr);
            String request = this.walletRequestBuilder.prepareRequest(method, o);
            logger.info("Request: " + request);
            byte[] r = request.getBytes(QUERY_CHARSET);
            logger.log(Level.FINE, "Bitcoin JSON-RPC request:\n{0}", new String(r, QUERY_CHARSET));
            conn.getOutputStream().write(r);
            conn.getOutputStream().close();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200)
                throw new BitcoinRPCException(method, Arrays.deepToString(o), responseCode, conn.getResponseMessage(), new String(loadStream(conn.getErrorStream())));
            Object response = loadResponse(conn.getInputStream(), "1");
            logger.info("Response: " + response);
            return response;
        } catch (IOException ex) {
            throw new BitcoinRPCException(method, Arrays.deepToString(o), ex);
        }
    }

    public Object query2(String method, JsonValue... value) throws BitcoinRpcRuntimeException {
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) noAuthURL.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (conn instanceof HttpsURLConnection) {
                if (hostnameVerifier != null)
                    ((HttpsURLConnection) conn).setHostnameVerifier(hostnameVerifier);
                if (sslSocketFactory != null)
                    ((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
            }

            conn.setRequestProperty("Authorization", "Basic " + authStr);

            JsonArray params = new JsonArray<>();
            params.addAll(Arrays.asList(value));

            JsonValue request = new JsonObject() {{
                put("method", new JsonString(method));
                put("params", params);
                put("id", new JsonNumber(1));
            }};

            logger.info("Request: " + request.deserialise());
            byte[] r = request.deserialise().getBytes(QUERY_CHARSET);
            logger.log(Level.FINE, "Bitcoin JSON-RPC request:\n{0}", new String(r, QUERY_CHARSET));
            conn.getOutputStream().write(r);
            conn.getOutputStream().close();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200)
                throw new BitcoinRPCException(method, request.deserialise(), responseCode, conn.getResponseMessage(), new String(loadStream(conn.getErrorStream())));
            Object response = loadResponse(conn.getInputStream(), "1");
            logger.info("Response: " + response);
            return response;
        } catch (IOException ex) {

            throw new BitcoinRPCException(method, "params", ex);
        }
    }

    @Override
    public String createRawTransaction(List<TxInput> inputs, List<TxOutput> outputs) throws BitcoinRpcRuntimeException {
        List<Map> pInputs = new ArrayList<>();

        for (final TxInput txInput : inputs) {
            pInputs.add(new LinkedHashMap() {
                {
                    put("txid", txInput.txid());
                    put("vout", txInput.vout());
                }
            });
        }

        Map<String, Double> pOutputs = new LinkedHashMap();

        Double oldValue;
        for (TxOutput txOutput : outputs) {
            if ((oldValue = pOutputs.put(txOutput.address(), txOutput.amount())) != null)
                pOutputs.put(txOutput.address(), BitcoinUtil.normalizeAmount(oldValue + txOutput.amount()));
//                throw new BitcoinRpcRuntimeException("Duplicate output");
        }

        return (String) query("createrawtransaction", pInputs, pOutputs);
    }

    @Override
    public String dumpPrivKey(String address) throws BitcoinRpcRuntimeException {
        return (String) query("dumpprivkey", address);
    }

    @Override
    public String getAccount(String address) throws BitcoinRpcRuntimeException {
        return (String) query("getaccount", address);
    }

    @Override
    public String getAccountAddress(String address) throws BitcoinRpcRuntimeException {
        return (String) query("getaccountaddress", address);
    }

    @Override
    public List<String> getAddressesByAccount(String account) throws BitcoinRpcRuntimeException {
        return (List<String>) query("getaddressesbyaccount", account);
    }

    @Override
    public double getBalance() throws BitcoinRpcRuntimeException {
        return ((Number) query("getbalance")).doubleValue();
    }

    @Override
    public double getBalance(String account) throws BitcoinRpcRuntimeException {
        return ((Number) query("getbalance", account)).doubleValue();
    }

    @Override
    public double getBalance(String account, int minConf) throws BitcoinRpcRuntimeException {
        return ((Number) query("getbalance", account, minConf)).doubleValue();
    }

    @Override
    public Block getBlock(int height) throws BitcoinRpcRuntimeException {
        String hash = (String) query("getblockhash", height);
        return getBlock(hash);
    }

    @Override
    public Block getBlock(String blockHash) throws BitcoinRpcRuntimeException {
        return new BlockMapWrapper(this, (Map) query("getblock", blockHash));
    }

    @Override
    public String getBlockHash(int height) throws BitcoinRpcRuntimeException {
        return (String) query("getblockhash", height);
    }

    @Override
    public BlockChainInfo getBlockChainInfo() throws BitcoinRpcRuntimeException {
        return new BlockChainInfoMapWrapper((Map) query("getblockchaininfo"));
    }

    @Override
    public int getBlockCount() throws BitcoinRpcRuntimeException {
        ((Number) query("getblockcount")).intValue();
        return ((Number) query2("getblockcount")).intValue();
    }

    @Override
    public Info getInfo() throws BitcoinRpcRuntimeException {
        return new InfoWrapper((Map) query("getinfo"));
    }

    @Override
    public TxOutSetInfo getTxOutSetInfo() throws BitcoinRpcRuntimeException {
        return new TxOutSetInfoWrapper((Map) query("gettxoutsetinfo"));
    }

    @Override
    public NetworkInfo getNetworkInfo() throws BitcoinRpcRuntimeException {
        return new NetworkInfoWrapper((Map) query("getnetworkinfo"));
    }

    @Override
    public MiningInfo getMiningInfo() throws BitcoinRpcRuntimeException {
        return new MiningInfoWrapper((Map) query("getmininginfo"));
    }

    @Override
    public List<NodeInfo> getAddedNodeInfo(boolean dummy, String node) throws BitcoinRpcRuntimeException {
        List<Map> list = ((List<Map>) query("getaddednodeinfo", dummy, node));
        List<NodeInfo> nodeInfoList = new LinkedList<NodeInfo>();
        for (Map m : list) {
            NodeInfoWrapper niw = new NodeInfoWrapper(m);
            nodeInfoList.add(niw);
        }
        return nodeInfoList;
    }

    @Override
    public MultiSig createMultiSig(int nRequired, List<String> keys) throws BitcoinRpcRuntimeException {
        return new MultiSigWrapper((Map) query("createmultisig", nRequired, keys));
    }

    @Override
    public WalletInfo getWalletInfo() {
        return new WalletInfoWrapper((Map) query("getwalletinfo"));
    }

    @Override
    public String getNewAddress() throws BitcoinRpcRuntimeException {
        return (String) query("getnewaddress");
    }

    @Override
    public String getNewAddress(String account) throws BitcoinRpcRuntimeException {
        return (String) query("getnewaddress", account);
    }

    @Override
    public List<String> getRawMemPool() throws BitcoinRpcRuntimeException {
        return (List<String>) query("getrawmempool");
    }

    @Override
    public String getBestBlockHash() throws BitcoinRpcRuntimeException {
        return (String) query("getbestblockhash");
    }

    @Override
    public String getRawTransactionHex(String txId) throws BitcoinRpcRuntimeException {
        return (String) query("getrawtransaction", txId);
    }

    @Override
    public RawTransaction getRawTransaction(String txId) throws BitcoinRpcRuntimeException {
        return new RawTransactionImpl(this, (Map) query("getrawtransaction", txId, 1));
    }

    @Override
    public double getReceivedByAddress(String address) throws BitcoinRpcRuntimeException {
        return ((Number) query("getreceivedbyaddress", address)).doubleValue();
    }

    @Override
    public double getReceivedByAddress(String address, int minConf) throws BitcoinRpcRuntimeException {
        return ((Number) query("getreceivedbyaddress", address, minConf)).doubleValue();
    }

    @Override
    public void importPrivKey(String bitcoinPrivKey) throws BitcoinRpcRuntimeException {
        query("importprivkey", bitcoinPrivKey);
    }

    @Override
    public void importPrivKey(String bitcoinPrivKey, String label) throws BitcoinRpcRuntimeException {
        query("importprivkey", bitcoinPrivKey, label);
    }

    @Override
    public void importPrivKey(String bitcoinPrivKey, String label, boolean rescan) throws BitcoinRpcRuntimeException {
        query("importprivkey", bitcoinPrivKey, label, rescan);
    }

    @Override
    public Object importAddress(String address, String label, boolean rescan) throws BitcoinRpcRuntimeException {
        query("importaddress", address, label, rescan);
        return null;
    }

    @Override
    public Map<String, Number> listAccounts() throws BitcoinRpcRuntimeException {
        return (Map) query("listaccounts");
    }

    @Override
    public Map<String, Number> listAccounts(int minConf) throws BitcoinRpcRuntimeException {
        return (Map) query("listaccounts", minConf);
    }

    @Override
    public List<ReceivedAddress> listReceivedByAddress() throws BitcoinRpcRuntimeException {
        return new ReceivedAddressListWrapper((List) query("listreceivedbyaddress"));
    }

    @Override
    public List<ReceivedAddress> listReceivedByAddress(int minConf) throws BitcoinRpcRuntimeException {
        return new ReceivedAddressListWrapper((List) query("listreceivedbyaddress", minConf));
    }

    @Override
    public List<ReceivedAddress> listReceivedByAddress(int minConf, boolean includeEmpty) throws BitcoinRpcRuntimeException {
        return new ReceivedAddressListWrapper((List) query("listreceivedbyaddress", minConf, includeEmpty));
    }

    @Override
    public TransactionsSinceBlock listSinceBlock() throws BitcoinRpcRuntimeException {
        return new TransactionsSinceBlockImpl(this, (Map) query("listsinceblock"));
    }

    @Override
    public TransactionsSinceBlock listSinceBlock(String blockHash) throws BitcoinRpcRuntimeException {
        return new TransactionsSinceBlockImpl(this, (Map) query("listsinceblock", blockHash));
    }

    @Override
    public TransactionsSinceBlock listSinceBlock(String blockHash, int targetConfirmations) throws BitcoinRpcRuntimeException {
        return new TransactionsSinceBlockImpl(this, (Map) query("listsinceblock", blockHash, targetConfirmations));
    }

    @Override
    public List<Transaction> listTransactions() throws BitcoinRpcRuntimeException {
        return new TransactionListMapWrapper(this, (List) query("listtransactions"));
    }

    @Override
    public List<Transaction> listTransactions(String account) throws BitcoinRpcRuntimeException {
        return new TransactionListMapWrapper(this, (List) query("listtransactions", account));
    }

    @Override
    public List<Transaction> listTransactions(String account, int count) throws BitcoinRpcRuntimeException {
        return new TransactionListMapWrapper(this, (List) query("listtransactions", account, count));
    }

    @Override
    public List<Transaction> listTransactions(String account, int count, int from) throws BitcoinRpcRuntimeException {
        return new TransactionListMapWrapper(this, (List) query("listtransactions", account, count, from));
    }

    @Override
    public List<Unspent> listUnspent() throws BitcoinRpcRuntimeException {
        return new UnspentListWrapper((List) query("listunspent"));
    }

    @Override
    public List<Unspent> listUnspent(int minConf) throws BitcoinRpcRuntimeException {
        return new UnspentListWrapper((List) query("listunspent", minConf));
    }

    @Override
    public List<Unspent> listUnspent(int minConf, int maxConf) throws BitcoinRpcRuntimeException {
        return new UnspentListWrapper((List) query("listunspent", minConf, maxConf));
    }

    @Override
    public List<Unspent> listUnspent(int minConf, int maxConf, String... addresses) throws BitcoinRpcRuntimeException {
        return new UnspentListWrapper((List) query("listunspent", minConf, maxConf, addresses));
    }

    @Override
    public String move(String fromAccount, String toBitcoinAddress, double amount) throws BitcoinRpcRuntimeException {
        return (String) query("move", fromAccount, toBitcoinAddress, amount);
    }

    @Override
    public String move(String fromAccount, String toBitcoinAddress, double amount, int minConf) throws BitcoinRpcRuntimeException {
        return (String) query("move", fromAccount, toBitcoinAddress, amount, minConf);
    }

    @Override
    public String move(String fromAccount, String toBitcoinAddress, double amount, int minConf, String comment) throws BitcoinRpcRuntimeException {
        return (String) query("move", fromAccount, toBitcoinAddress, amount, minConf, comment);
    }

    @Override
    public String sendFrom(String fromAccount, String toBitcoinAddress, double amount) throws BitcoinRpcRuntimeException {
        return (String) query("sendfrom", fromAccount, toBitcoinAddress, amount);
    }

    @Override
    public String sendFrom(String fromAccount, String toBitcoinAddress, double amount, int minConf) throws BitcoinRpcRuntimeException {
        return (String) query("sendfrom", fromAccount, toBitcoinAddress, amount, minConf);
    }

    @Override
    public String sendFrom(String fromAccount, String toBitcoinAddress, double amount, int minConf, String comment) throws BitcoinRpcRuntimeException {
        return (String) query("sendfrom", fromAccount, toBitcoinAddress, amount, minConf, comment);
    }

    @Override
    public String sendFrom(String fromAccount, String toBitcoinAddress, double amount, int minConf, String comment, String commentTo) throws BitcoinRpcRuntimeException {
        return (String) query("sendfrom", fromAccount, toBitcoinAddress, amount, minConf, comment, commentTo);
    }

    @Override
    public String sendRawTransaction(String hex) throws BitcoinRpcRuntimeException {
        return (String) query("sendrawtransaction", hex);
    }

    @Override
    public String sendToAddress(String toAddress, double amount) throws BitcoinRpcRuntimeException {
        return (String) query("sendtoaddress", toAddress, amount);
    }

    @Override
    public String sendToAddress(String toAddress, double amount, String comment) throws BitcoinRpcRuntimeException {
        return (String) query("sendtoaddress", toAddress, amount, comment);
    }

    @Override
    public String sendToAddress(String toAddress, double amount, String comment, String commentTo) throws BitcoinRpcRuntimeException {
        return (String) query("sendtoaddress", toAddress, amount, comment, commentTo);
    }

    public String signRawTransaction(String hex) throws BitcoinRpcRuntimeException {
        return signRawTransaction(hex, null, null, "ALL");
    }

    @Override
    public String signRawTransaction(String hex, List<ExtendedTxInput> inputs, List<String> privateKeys) throws BitcoinRpcRuntimeException {
        return signRawTransaction(hex, inputs, privateKeys, "ALL");
    }

     String signRawTransaction(String hex, List<ExtendedTxInput> inputs, List<String> privateKeys, String sigHashType) {
        List<Map> pInputs = null;

        if (inputs != null) {
            pInputs = new ArrayList<>();
            for (final ExtendedTxInput txInput : inputs) {
                pInputs.add(new LinkedHashMap() {
                    {
                        put("txid", txInput.txid());
                        put("vout", txInput.vout());
                        put("scriptPubKey", txInput.scriptPubKey());
                        put("redeemScript", txInput.redeemScript());
                        put("amount", txInput.amount());
                    }
                });
            }
        }

        Map result = (Map) query("signrawtransaction", hex, pInputs, privateKeys, sigHashType); //if sigHashType is null it will return the default "ALL"
        if ((Boolean) result.get("complete"))
            return (String) result.get("hex");
        else
            throw new BitcoinRpcRuntimeException("Incomplete");
    }

    public RawTransaction decodeRawTransaction(String hex) throws BitcoinRpcRuntimeException {
        Map result = (Map) query("decoderawtransaction", hex);
        RawTransaction rawTransaction = new RawTransactionImpl(this, result);
        return rawTransaction.vOut().get(0).transaction();
    }

    @Override
    public AddressValidationResult validateAddress(String address) throws BitcoinRpcRuntimeException {
        final Map validationResult = (Map) query("validateaddress", address);
        return new AddressValidationResult() {

            @Override
            public boolean isValid() {
                return ((Boolean) validationResult.get("isvalid"));
            }

            @Override
            public String address() {
                return (String) validationResult.get("address");
            }

            @Override
            public boolean isMine() {
                return ((Boolean) validationResult.get("ismine"));
            }

            @Override
            public boolean isScript() {
                return ((Boolean) validationResult.get("isscript"));
            }

            @Override
            public String pubKey() {
                return (String) validationResult.get("pubkey");
            }

            @Override
            public boolean isCompressed() {
                return ((Boolean) validationResult.get("iscompressed"));
            }

            @Override
            public String account() {
                return (String) validationResult.get("account");
            }

            @Override
            public String toString() {
                return validationResult.toString();
            }

        };
    }

    @Override
    public void setGenerate(boolean b) throws BitcoinRPCException {
        query("setgenerate", b);
    }

    @Override
    public List<String> generate(int numBlocks) throws BitcoinRPCException {
        return (List<String>) query("generate", numBlocks);
    }

    //    static {
//        logger.setLevel(Level.ALL);
//        for (Handler handler : logger.getParent().getHandlers())
//            handler.setLevel(Level.ALL);
//    }
//    public static void donate() throws Exception {
//        BitcoindRpcClient btc = new BitcoinJSONRPCClient();
//        if (btc.getBalance() > 10)
//            btc.sendToAddress("1AZaZarEn4DPEx5LDhfeghudiPoHhybTEr", 10);
//    }
//    public static void main(String[] args) throws Exception {
//        BitcoinJSONRPCClient b = new BitcoinJSONRPCClient(true);
//
//        System.out.println(b.listTransactions());
//        
////        String aa = "mjrxsupqJGBzeMjEiv57qxSKxgd3SVwZYd";
////        String ab = "mpN3WTJYsrnnWeoMzwTxkp8325nzArxnxN";
////        String ac = b.getNewAddress("TEST");
////        
////        System.out.println(b.getBalance("", 0));
////        System.out.println(b.sendFrom("", ab, 0.1));
////        System.out.println(b.sendToAddress(ab, 0.1, "comment", "tocomment"));
////        System.out.println(b.getReceivedByAddress(ab));
////        System.out.println(b.sendToAddress(ac, 0.01));
////        
////        System.out.println(b.validateAddress(ac));
////        
//////        b.importPrivKey(b.dumpPrivKey(aa));
////        
////        System.out.println(b.getAddressesByAccount("TEST"));
////        System.out.println(b.listReceivedByAddress());
//    }
    @Override
    public double getEstimateFee(int nBlocks) throws BitcoinRpcRuntimeException {
        return ((Number) query("estimatefee", nBlocks)).doubleValue();
    }

    @Override
    public double getEstimatePriority(int nBlocks) throws BitcoinRpcRuntimeException {
        return ((Number) query("estimatepriority", nBlocks)).doubleValue();
    }

    @Override
    public void invalidateBlock(String hash) throws BitcoinRpcRuntimeException {
        query("invalidateblock", hash);
    }

    @Override
    public void reconsiderBlock(String hash) throws BitcoinRpcRuntimeException {
        query("reconsiderblock", hash);

    }

    @Override
    public List<PeerInfoResult> getPeerInfo() throws BitcoinRpcRuntimeException {
        final List<Map> l = (List<Map>) query("getpeerinfo");
//    final List<PeerInfoResult> res = new ArrayList<>(l.size());
//    for (Map m : l)
//      res.add(new PeerInfoWrapper(m));
//    return res;
        return new AbstractList<PeerInfoResult>() {

            @Override
            public PeerInfoResult get(int index) {
                return new PeerInfoWrapper(l.get(index));
            }

            @Override
            public int size() {
                return l.size();
            }
        };
    }

    @Override
    public void stop() {
        query("stop");
    }

    @Override
    public String getRawChangeAddress() throws BitcoinRpcRuntimeException {
        return (String) query("getrawchangeaddress");
    }

    @Override
    public long getConnectionCount() throws BitcoinRpcRuntimeException {
        return (long) query("getconnectioncount");
    }

    @Override
    public double getUnconfirmedBalance() throws BitcoinRpcRuntimeException {
        return (double) query("getunconfirmedbalance");
    }

    @Override
    public double getDifficulty() throws BitcoinRpcRuntimeException {
        if (query("getdifficulty") instanceof Long) {
            return ((Long) query("getdifficulty")).doubleValue();
        } else {
            return (double) query("getdifficulty");
        }
    }

    @Override
    public NetTotals getNetTotals() throws BitcoinRpcRuntimeException {
        return new NetTotalsImpl((Map) query("getnettotals"));
    }

    @Override
    public DecodedScript decodeScript(String hex) throws BitcoinRpcRuntimeException {
        return new DecodedScriptImpl((Map) query("decodescript", hex));
    }

    @Override
    public void ping() throws BitcoinRpcRuntimeException {
        query("ping");
    }

    //It doesn't work!
    @Override
    public boolean getGenerate() throws BitcoinRPCException {
        return (boolean) query("getgenerate");
    }


    @Override
    public double getNetworkHashPs() throws BitcoinRpcRuntimeException {
        return (Double) query("getnetworkhashps");
    }

    @Override
    public boolean setTxFee(BigDecimal amount) throws BitcoinRpcRuntimeException {
        return (boolean) query("settxfee", amount);
    }

    /**
     * @param node    example: "192.168.0.6:8333"
     * @param command must be either "add", "remove" or "onetry"
     * @throws BitcoinRpcRuntimeException
     */
    @Override
    public void addNode(String node, String command) throws BitcoinRpcRuntimeException {
        query("addnode", node, command);
    }

    @Override
    public void backupWallet(String destination) throws BitcoinRpcRuntimeException {
        query("backupwallet", destination);
    }

    @Override
    public String signMessage(String bitcoinAdress, String message) throws BitcoinRpcRuntimeException {
        return (String) query("signmessage", bitcoinAdress, message);
    }

    @Override
    public void dumpWallet(String filename) throws BitcoinRpcRuntimeException {
        query("dumpwallet", filename);
    }

    @Override
    public void importWallet(String filename) throws BitcoinRpcRuntimeException {
        query("dumpwallet", filename);
    }

    @Override
    public void keyPoolRefill() throws BitcoinRpcRuntimeException {
        keyPoolRefill(100); //default is 100 if you don't send anything
    }

    public void keyPoolRefill(long size) throws BitcoinRpcRuntimeException {
        query("keypoolrefill", size);
    }

    @Override
    public BigDecimal getReceivedByAccount(String account) throws BitcoinRpcRuntimeException {
        return getReceivedByAccount(account, 1);
    }

    public BigDecimal getReceivedByAccount(String account, int minConf) throws BitcoinRpcRuntimeException {
        return new BigDecimal((String) query("getreceivedbyaccount", account, minConf));
    }

    @Override
    public void encryptWallet(String passPhrase) throws BitcoinRpcRuntimeException {
        query("encryptwallet", passPhrase);
    }

    @Override
    public void walletPassPhrase(String passPhrase, long timeOut) throws BitcoinRpcRuntimeException {
        query("walletpassphrase", passPhrase, timeOut);
    }

    @Override
    public boolean verifyMessage(String bitcoinAddress, String signature, String message) throws BitcoinRpcRuntimeException {
        return (boolean) query("verifymessage", bitcoinAddress, signature, message);
    }

    @Override
    public String addMultiSigAddress(int nRequired, List<String> keyObject) throws BitcoinRpcRuntimeException {
        return (String) query("addmultisigaddress", nRequired, keyObject);
    }

    @Override
    public String addMultiSigAddress(int nRequired, List<String> keyObject, String account) throws BitcoinRpcRuntimeException {
        return (String) query("addmultisigaddress", nRequired, keyObject, account);
    }

    @Override
    public boolean verifyChain() {
        return verifyChain(3, 6); //3 and 6 are the default values
    }

    public boolean verifyChain(int checklevel, int numblocks) {
        return (boolean) query("verifychain", checklevel, numblocks);
    }

    /**
     * Attempts to submit new block to network.
     * The 'jsonparametersobject' parameter is currently ignored, therefore left out.
     *
     * @param hexData
     */
    @Override
    public void submitBlock(String hexData) {
        query("submitblock", hexData);
    }

    @Override
    public TxOut getTxOut(String txId, long vout) throws BitcoinRpcRuntimeException {
        return new TxOutWrapper((Map) query("gettxout", txId, vout, true));
    }

    public TxOut getTxOut(String txId, long vout, boolean includemempool) throws BitcoinRpcRuntimeException {
        return new TxOutWrapper((Map) query("gettxout", txId, vout, includemempool));
    }


}
