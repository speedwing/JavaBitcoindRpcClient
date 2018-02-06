package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.util.Map;

public class PeerInfoWrapper extends MapWrapper implements PeerInfoResult, Serializable {

    public PeerInfoWrapper(Map m) {
        super(m);
    }

    @Override
    public long getId() {
        return mapLong("id");
    }

    @Override
    public String getAddr() {
        return mapStr("addr");
    }

    @Override
    public String getAddrLocal() {
        return mapStr("addrlocal");
    }

    @Override
    public String getServices() {
        return mapStr("services");
    }

    @Override
    public long getLastSend() {
        return mapLong("lastsend");
    }

    @Override
    public long getLastRecv() {
        return mapLong("lastrecv");
    }

    @Override
    public long getBytesSent() {
        return mapLong("bytessent");
    }

    @Override
    public long getBytesRecv() {
        return mapLong("bytesrecv");
    }

    @Override
    public long getConnTime() {
        return mapLong("conntime");
    }

    @Override
    public int getTimeOffset() {
        return mapInt("timeoffset");
    }

    @Override
    public double getPingTime() {
        return mapDouble("pingtime");
    }

    @Override
    public long getVersion() {
        return mapLong("version");
    }

    @Override
    public String getSubVer() {
        return mapStr("subver");
    }

    @Override
    public boolean isInbound() {
        return mapBool("inbound");
    }

    @Override
    public int getStartingHeight() {
        return mapInt("startingheight");
    }

    @Override
    public long getBanScore() {
        return mapLong("banscore");
    }

    @Override
    public int getSyncedHeaders() {
        return mapInt("synced_headers");
    }

    @Override
    public int getSyncedBlocks() {
        return mapInt("synced_blocks");
    }

    @Override
    public boolean isWhiteListed() {
        return mapBool("whitelisted");
    }

}
