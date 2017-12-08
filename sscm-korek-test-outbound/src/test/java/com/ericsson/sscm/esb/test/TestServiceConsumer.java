package com.ericsson.sscm.esb.test;

import com.ericsson.sscm.esb.custom.outbound.CustomKorekTestOutboundService;
import com.ericsson.sscm.esb.model.data.Request;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestServiceConsumer {
    private final static Logger log = LoggerFactory.getLogger(TestServiceConsumer.class);
    private CustomKorekTestOutboundService service;

    public CustomKorekTestOutboundService getService() {
        return service;
    }

    public void setService(CustomKorekTestOutboundService service) {
        this.service = service;
    }

    public void readSubscriberData(Exchange e) {
        Request body = (Request) e.getIn().getBody();
        service.readSubscriberData((String)(body.getParam("msisdn")), (String)(body.getParam("transactionId")));
    }
}
