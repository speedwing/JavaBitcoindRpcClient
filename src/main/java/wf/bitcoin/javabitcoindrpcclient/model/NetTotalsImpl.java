package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.util.Map;

public class NetTotalsImpl extends MapWrapper implements NetTotals, Serializable {

    public NetTotalsImpl(Map m) {
        super(m);
    }

    @Override
    public long totalBytesRecv() {
        return mapLong("totalbytesrecv");
    }

    @Override
    public long totalBytesSent() {
        return mapLong("totalbytessent");
    }

    @Override
    public long timeMillis() {
        return mapLong("timemillis");
    }

    public class uploadTargetImpl extends MapWrapper implements uploadTarget, Serializable {

        public uploadTargetImpl(Map m) {
            super(m);
        }


        @Override
        public long timeFrame() {
            return mapLong("timeframe");
        }

        @Override
        public int target() {
            return mapInt("target");
        }

        @Override
        public boolean targetReached() {
            return mapBool("targetreached");
        }

        @Override
        public boolean serveHistoricalBlocks() {
            return mapBool("servehistoricalblocks");
        }

        @Override
        public long bytesLeftInCycle() {
            return mapLong("bytesleftincycle");
        }

        @Override
        public long timeLeftInCycle() {
            return mapLong("timeleftincycle");
        }
    }

    @Override
    public uploadTarget uploadTarget() {
        return new uploadTargetImpl((Map) m.get("uploadtarget"));
    }
}
