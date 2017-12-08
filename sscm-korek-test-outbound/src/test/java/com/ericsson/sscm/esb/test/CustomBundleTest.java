package com.ericsson.sscm.esb.test;

import com.ericsson.sscm.esb.constants.CommonConstants;
import com.ericsson.sscm.esb.custom.outbound.CustomKorekTestOutboundService;
import com.ericsson.sscm.esb.model.data.Attribute;
import com.ericsson.sscm.esb.model.data.Request;
import com.ericsson.test.ws.ReadSubscriberData;
import com.ericsson.test.ws.ReadSubscriberDataResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Predicate;
import org.apache.camel.component.mock.AssertionClause;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.cxf.message.MessageContentsList;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by eilkakk on 2/20/14.
 */
public class CustomBundleTest extends CamelBlueprintTestSupport {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // override this method, and return the location of our Blueprint XML file to be used for testing
    @Override
    protected String getBlueprintDescriptor() {
        return "test-blueprint.xml";
    }


    @Override
    protected String getBundleFilter() {
        // Dont load self bundle, dont want to use original bluepint.xml
        return "(!(Bundle-SymbolicName=sscm-korek-test-outbound))" ;
    }

    @Test
    public void testRead() throws Exception {

        // set mock expectations
        MockEndpoint m = getMockEndpoint("mock:testServer");
        m.expectedMessageCount(1);
        m.returnReplyBody(new Expression() {
            @Override
            public <T> T evaluate(Exchange exchange, Class<T> type) {
                ReadSubscriberDataResponse response = new ReadSubscriberDataResponse();
                return (T) response;
            }
        });
        m.expectedMessagesMatches(new Predicate() {

            @Override
            public boolean matches(Exchange exchange) {
                MessageContentsList m = (MessageContentsList) exchange.getIn().getBody();
                ReadSubscriberData d = (ReadSubscriberData) m.get(0);
                assertEquals("123456789", d.getMsisdn());
                assertEquals("98765", d.getTransactionId());
                return true;
            }
        });

        Request request = new Request();
        request.setFlowName("subscriberCreate");
        request.getNodeRequest().add("msisdn", "123456789");
        request.getNodeRequest().add("transactionId", "98765");
        // send a message
        template.sendBody("direct:start", request);

        assertMockEndpointsSatisfied();
    }
}
