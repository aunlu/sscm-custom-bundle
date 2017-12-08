package com.ericsson.sscm.esb.custom.outbound.impl;

import com.ericsson.sscm.esb.custom.outbound.CustomKorekTestOutboundService;
import com.ericsson.sscm.esb.model.data.NodeResponse;
import com.ericsson.sscm.esb.model.data.StatusValue;
import com.ericsson.test.ws.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomKorekTestOutboundServiceImpl implements CustomKorekTestOutboundService {

    private static final int SUCCESS_STATUS = 0;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private com.ericsson.test.ws.TestWs testServiceProxy;

    private NodeResponse prepareResponse(TestResponse response) {
        NodeResponse nodeResponse = new NodeResponse();

        if (response.getStatus() != null && response.getStatus() == SUCCESS_STATUS) {
            nodeResponse.setStatus(StatusValue.SUCCESS);
        } else {
            nodeResponse.setStatus(StatusValue.ERROR);
        }
        nodeResponse.setResponseCode(response.getResponseCode() + "");
        nodeResponse.setResponseDescription(response.getDescription());
        return nodeResponse;
    }


    public TestWs getTestServiceProxy() {
        return testServiceProxy;
    }

    public void setTestServiceProxy(TestWs testServiceProxy) {
        this.testServiceProxy = testServiceProxy;
    }

    @Override
    public NodeResponse setSubscriberData(String msisdn, String transactionId, String param1, String param2) {
        if (msisdn == null || transactionId == null || param1 == null) {
            logger.error("Parameter cannot be null. " + msisdn + "|" + transactionId + "|" + param1);
            throw new UnsupportedOperationException("Parameters cannot be null. " + msisdn + "|" + transactionId + "|" + param1);
        }
        SetSubscriberData request = new SetSubscriberData();
        request.setMsisdn(msisdn);
        request.setTransactionId(transactionId);
        request.setParam1(param1);
        request.setParam2(param2);

        try {
            SetSubscriberDataResponse response = testServiceProxy.setSubscriberData(request);
            NodeResponse nodeResponse = prepareResponse(response.getTestResponse());
            return nodeResponse;
        } catch (Exception e) {
            logger.error("Exception occured during setSubscriberData call" + e);
            throw new RuntimeException("Exception occured during setSubscriberData call", e);
        }

    }

    @Override
    public NodeResponse readSubscriberData(String msisdn, String transactionId) {
        if (msisdn == null || transactionId == null) {
            logger.error("Parameter cannot be null. " + msisdn + "|" + transactionId);
            throw new UnsupportedOperationException("Parameters cannot be null. " + msisdn + "|" + transactionId);
        }

        ReadSubscriberData request = new ReadSubscriberData();
        request.setMsisdn(msisdn);
        request.setTransactionId(transactionId);

        try {
            ReadSubscriberDataResponse response = testServiceProxy.readSubscriberData(request);
            NodeResponse nodeResponse = prepareResponse(response);
            return nodeResponse;
        } catch (Exception e) {
            logger.error("Exception occured during setSubscriberData call" + e);
            throw new RuntimeException("Exception occured during setSubscriberData call", e);
        }

    }
}
