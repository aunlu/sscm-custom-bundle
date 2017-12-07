package com.ericsson.sscm.esb.custom.outbound;

import com.ericsson.sscm.esb.model.annotations.In;
import com.ericsson.sscm.esb.model.annotations.NodeIdentifier;
import com.ericsson.sscm.esb.model.annotations.Operation;
import com.ericsson.sscm.esb.model.annotations.ReadOnly;
import com.ericsson.sscm.esb.model.data.NodeResponse;
import com.ericsson.sscm.esb.outbound.SscmOutboundService;

@NodeIdentifier(value = "CustomKorekTestOutboundService")
public interface CustomKorekTestOutboundService extends SscmOutboundService {

    String ATTRIBUTE_CAMPAIGN_ID = "CAMPAIGN_ID";
    String ATTRIBUTE_OFFER_ID = "OFFER_ID";

    @Operation(value = "setSubscriberData")
    public NodeResponse setSubscriberData(@In(param = "msisdn") String msisdn,
                                          @In(param = "transactionId") String transactionId,
                                          @In(param = "param1") String param1,
                                          @In(param = "param2") String param2);

    @ReadOnly
    @Operation(value = "readSubscriberData")
    public NodeResponse readSubscriberData(@In(param = "msisdn") String msisdn,
                                          @In(param = "transactionId") String transactionId);

}
