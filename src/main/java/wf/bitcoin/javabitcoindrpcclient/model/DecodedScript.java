package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;
import java.util.List;

public interface DecodedScript extends Serializable {

    String asm();

    String hex();

    String type();

    int reqSigs();

    List<String> addresses();

    String p2sh();
}
