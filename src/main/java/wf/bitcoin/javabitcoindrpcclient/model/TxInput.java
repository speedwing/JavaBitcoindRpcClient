package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;

/* Missing methods:
 getblocktemplate ( "jsonrequestobject" )
 *getgenerate
 *gethashespersec
 *getwork ( "data" )
 help ( "command" )
 *listaddressgroupings
 *listlockunspent
 (DEPRECATED) listreceivedbyaccount ( minconf includeempty )
 lockunspent unlock [{"txid":"txid","vout":n},...]
 sendmany "fromaccount" {"address":amount,...} ( minconf "comment" )
 (DEPRECATED) setaccount "bitcoinaddress" "account"
 */
public interface TxInput extends Serializable {

    String txid();

    int vout();

    String scriptPubKey();
}
