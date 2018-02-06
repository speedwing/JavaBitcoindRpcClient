package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

import java.io.Serializable;

public interface NetTotals extends Serializable {

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
