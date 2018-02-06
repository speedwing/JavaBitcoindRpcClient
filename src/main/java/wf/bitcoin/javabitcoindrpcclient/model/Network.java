package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;

public interface Network extends Serializable {

    String name();

    boolean limited();

    boolean reachable();

    String proxy();

    boolean proxyRandomizeCredentials();
}
