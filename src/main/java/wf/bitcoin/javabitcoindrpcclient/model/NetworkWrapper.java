package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.util.Map;

public class NetworkWrapper extends MapWrapper implements Network, Serializable {

    public NetworkWrapper(Map m) {
        super(m);
    }

    @Override
    public String name() {
        return mapStr("name");
    }

    @Override
    public boolean limited() {
        return mapBool("limited");
    }

    @Override
    public boolean reachable() {
        return mapBool("reachable");
    }

    @Override
    public String proxy() {
        return mapStr("proxy");
    }

    @Override
    public boolean proxyRandomizeCredentials() {
        return mapBool("proxy_randomize_credentials");
    }
}
