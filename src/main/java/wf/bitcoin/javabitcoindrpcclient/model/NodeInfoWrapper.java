package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NodeInfoWrapper extends MapWrapper implements NodeInfo, Serializable {

    public NodeInfoWrapper(Map m) {
        super(m);
    }

    @Override
    public String addedNode() {
        return mapStr("addednode");
    }

    @Override
    public boolean connected() {
        return mapBool("connected");
    }

    @Override
    public List<Address> addresses() {
        List<Map> maps = (List<Map>) m.get("addresses");
        List<Address> addresses = new LinkedList<Address>();
        for (Map m : maps) {
            Address add = new AddressWrapper(m);
            addresses.add(add);
        }
        return addresses;
    }
}
