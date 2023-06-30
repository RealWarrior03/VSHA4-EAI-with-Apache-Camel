import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class WebOrderSystem {
    private static final String SOURCE_FOLDER = "src/main/inputfiles/webordersysteminput";
    private static final String DESTINATION_FOLDER = "src/main/outputfiles/webordersystemoutput";


    /*
    INPUT:
    a file containing multiple orders seperated by \n
    order format:
    FirstName, LastName, Number of ordered surfboards, Number of ordered diving suits, Customer-ID

     OUTPUT:
     none
     */

    public static void main(String[] args) throws Exception {
        String filename = args[1];

        //TODO add activemq stuff

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file://" + SOURCE_FOLDER + "?noop=true")
                    .split(body().tokenize("\n"))
                    .process(new WOSInputTransformer()) //transformWOS
                    .enrich() //enrich Message
                    .to();  //pubsub channel
            }
        });
        camelContext.start();
        camelContext.stop();
    }
}

/*
o CustomerID
o FirstName
o LastName
o OverallItems (Number of all items in order)
o NumberOfDivingSuits
o NumberOfSurfboards o OrderID
o Valid
o validationResult
 */
