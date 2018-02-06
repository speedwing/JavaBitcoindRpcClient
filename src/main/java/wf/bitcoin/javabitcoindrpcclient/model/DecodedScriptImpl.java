package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DecodedScriptImpl extends MapWrapper implements DecodedScript, Serializable {

    public DecodedScriptImpl(Map m) {
        super(m);
    }


    @Override
    public String asm() {
        return mapStr("asm");
    }

    @Override
    public String hex() {
        return mapStr("hex");
    }

    @Override
    public String type() {
        return mapStr("type");
    }

    @Override
    public int reqSigs() {
        return mapInt("reqSigs");
    }

    @Override
    public List<String> addresses() {
        return (List) m.get("addresses");
    }

    @Override
    public String p2sh() {
        return mapStr("p2sh");
    }
}
