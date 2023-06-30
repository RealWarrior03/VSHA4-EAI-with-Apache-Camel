import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class WebOrderSystem {
    private static final String SOURCE_FOLDER = "src/main/inputfiles/webordersysteminput";
    private static final String DESTINATION_FOLDER = "src/main/outputfiles/webordersystemoutput";


    public static void main(String[] args) throws Exception {
        String filename = args[1];
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file://" + SOURCE_FOLDER + "?noop=true");
            }
        });
        camelContext.start();
        camelContext.stop();
    }
}
