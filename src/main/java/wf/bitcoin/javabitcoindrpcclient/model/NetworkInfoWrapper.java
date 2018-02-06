package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NetworkInfoWrapper extends MapWrapper implements NetworkInfo, Serializable {

    public NetworkInfoWrapper(Map m) {
        super(m);
    }

    @Override
    public long version() {
        return mapLong("version");
    }

    @Override
    public String subversion() {
        return mapStr("subversion");
    }

    @Override
    public long protocolVersion() {
        return mapLong("protocolversion");
    }

    @Override
    public String localServices() {
        return mapStr("localservices");
    }

    @Override
    public boolean localRelay() {
        return mapBool("localrelay");
    }

    @Override
    public long timeOffset() {
        return mapLong("timeoffset");
    }

    @Override
    public long connections() {
        return mapLong("connections");
    }

    @Override
    public List<Network> networks() {
        List<Map> maps = (List<Map>) m.get("networks");
        List<Network> networks = new LinkedList<Network>();
        for (Map m : maps) {
            Network net = new NetworkWrapper(m);
            networks.add(net);
        }
        return networks;
    }

    @Override
    public BigDecimal relayFee() {
        return mapBigDecimal("relayfee");
    }

    @Override
    public List<String> localAddresses() {
        return (List<String>) m.get("localaddresses");
    }

    @Override
    public String warnings() {
        return mapStr("warnings");
    }
}
