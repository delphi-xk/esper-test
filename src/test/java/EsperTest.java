import com.espertech.esper.client.*;
import org.hua_cloud.esper.testBeans.OrderEvent;
import org.junit.Test;



class OrderListener implements UpdateListener{


    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        System.out.println("add new order...");
        String name = (String)newEvents[0].get("itemName");
        Double price = (Double)newEvents[0].get("price");
        System.out.println(String.format("Name: %s, Price: %.2f",name, price));
        //noinspection MalformedFormatString
        System.out.println(String.format("Avg price: %.2f",newEvents[0].get("avg(price)")));
    }
}

public class EsperTest {
    OrderEvent order1 = new OrderEvent("t-shirt1", 75);
    OrderEvent order2 = new OrderEvent("t-shirt2", 85);
    OrderEvent order3 = new OrderEvent("t-shirt3", 100);
    OrderEvent order4 = new OrderEvent("t-shirt4", 120);
    OrderEvent order5 = new OrderEvent("t-shirt5", 180);
    OrderEvent order6 = new OrderEvent("t-shirt6", 60);

    @Test
    public void test1(){
        Configuration config = new Configuration();
        config.addEventTypeAutoName("org.hua_cloud.esper.testBeans");

        EPServiceProvider epServiceProvider = EPServiceProviderManager.getDefaultProvider(config);

//        String product = OrderEvent.class.getName();
//        String ep1 = "select avg(price) from "+ product + ".win:time(30 sec)";
        String ep1 = "select avg(price) from OrderEvent.win:time(30 sec)";
        System.out.println(ep1);
        EPStatement statement = epServiceProvider.getEPAdministrator().createEPL(ep1);
        statement.addListener(new OrderListener());

        EPRuntime runtime = epServiceProvider.getEPRuntime();
        
        runtime.sendEvent(order1);
        runtime.sendEvent(order2);
        runtime.sendEvent(order3);
    }

    @Test
    public void test2(){
        EPServiceProvider serviceProvider = EPServiceProviderManager.getDefaultProvider();
        serviceProvider.getEPAdministrator().getConfiguration().addEventType(OrderEvent.class);
        String epl1 = "select itemName,price, avg(price) from OrderEvent";
        EPStatement statement = serviceProvider.getEPAdministrator().createEPL(epl1);
        statement.addListener(new OrderListener());

        EPRuntime runtime = serviceProvider.getEPRuntime();

        runtime.sendEvent(order2);
        runtime.sendEvent(order4);

    }

    @Test
    public void test3(){
        EPServiceProvider serviceProvider = EPServiceProviderManager.getDefaultProvider();
        serviceProvider.getEPAdministrator().getConfiguration().addEventType(OrderEvent.class);
        String epl1 = "select itemName,price, avg(price) from OrderEvent#length(5)";
        EPStatement statement = serviceProvider.getEPAdministrator().createEPL(epl1);
        statement.addListener( (newEvent,oldEvent) -> {
            String name = (String)newEvent[0].get("itemName");
            Double price = (Double)newEvent[0].get("price");
            System.out.println(String.format("Name: %s, Price: %.2f",name, price));
            //noinspection MalformedFormatString
            System.out.println(String.format("Avg price: %.2f",newEvent[0].get("avg(price)")));
        });

        EPRuntime runtime = serviceProvider.getEPRuntime();

        runtime.sendEvent(order1);
        runtime.sendEvent(order2);
        runtime.sendEvent(order3);
        runtime.sendEvent(order4);
        runtime.sendEvent(order5);
        runtime.sendEvent(order6);

    }




}
