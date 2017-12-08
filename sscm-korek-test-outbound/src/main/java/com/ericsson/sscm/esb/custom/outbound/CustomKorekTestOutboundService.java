package com.ericsson.sscm.esb.custom.outbound;

import com.ericsson.sscm.esb.model.data.NodeResponse;

public interface CustomKorekTestOutboundService {

    NodeResponse setSubscriberData(String msisdn,
                                          String transactionId,
                                          String param1,
                                          String param2);

    NodeResponse readSubscriberData(String msisdn,
                                          String transactionId);

}
