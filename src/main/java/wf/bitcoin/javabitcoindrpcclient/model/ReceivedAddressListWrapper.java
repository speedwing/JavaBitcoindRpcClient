package wf.bitcoin.javabitcoindrpcclient.model;

import java.util.AbstractList;
import java.util.List;
import java.util.Map;

public class ReceivedAddressListWrapper extends AbstractList<ReceivedAddress> {

    private final List<Map<String, Object>> wrappedList;

    public ReceivedAddressListWrapper(List<Map<String, Object>> wrappedList) {
        this.wrappedList = wrappedList;
    }

    @Override
    public ReceivedAddress get(int index) {
        final Map<String, Object> e = wrappedList.get(index);
        return new ReceivedAddress() {

            @Override
            public String address() {
                return (String) e.get("address");
            }

            @Override
            public String account() {
                return (String) e.get("account");
            }

            @Override
            public double amount() {
                return ((Number) e.get("amount")).doubleValue();
            }

            @Override
            public int confirmations() {
                return ((Number) e.get("confirmations")).intValue();
            }

            @Override
            public String toString() {
                return e.toString();
            }

        };
    }

    @Override
    public int size() {
        return wrappedList.size();
    }
}
