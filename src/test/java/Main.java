import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.model.Block;
import wf.bitcoin.krotjson.JSON;

import java.net.MalformedURLException;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        String user = "user";
        String password = "pass";
        String host = "localhost";
        Integer port = 5788;

        try {
            BitcoinJSONRPCClient electraClient = new BitcoinJSONRPCClient("http://" + user + ":" + password + "@" + host + ":" + port + "/");
            int blockCount = electraClient.getBlockCount();
            System.out.println("Block count: " + blockCount);

            final Block block = electraClient.getBlock(blockCount);
            System.out.println(block);

            Date time = block.time();
            System.out.println("Time: " + time);

//            System.out.println("\n\n-----\n\n");
//
//            BitcoinPaymentListener bitcoinPaymentListener = new BitcoinPaymentListener() {
//                public void block(String blockHash) {
//                    System.out.println("new block?? " + block);
//                }
//
//                public void transaction(BitcoindRpcClient.Transaction transaction) {
//                    System.out.println("new tx?? " + transaction);
//
//                }
//            };
//
//            BitcoinAcceptor bitcoinAcceptor = new BitcoinAcceptor(electraClient, bitcoinPaymentListener);
//            bitcoinAcceptor.run();
//
//
//            Thread.sleep(600000);
//
//            System.out.println("terminating");
//
//            bitcoinAcceptor.stopAccepting();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

}
