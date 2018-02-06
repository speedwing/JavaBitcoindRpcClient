package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;
import java.util.List;

public interface NodeInfo extends Serializable {

    String addedNode();

    boolean connected();

    List<Address> addresses();

}
