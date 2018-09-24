import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderManager {

    List<Order> orders = new ArrayList();


    public boolean registerOrder(String userId, BigDecimal price, OrderType type, BigDecimal orderQuant) {
        Order temp = new Order(userId, price, type, orderQuant);
        return orders.add(temp);
    }

    public boolean cancelOrder(String userId, BigDecimal price, OrderType type, BigDecimal orderQuant) {
        Order temp = new Order(userId, price, type, orderQuant);
        return orders.remove(temp);
    }

    public List<OrderSummary> getOrderSummary() {
        Map<BigDecimal, OrderSummary> ordSummary = new HashMap<>();
        for (Order order : orders) {
            OrderSummary temp = new OrderSummary(order.getPrice(), order.getType(), order.getQuantity());
            ordSummary.merge(temp.getPrice(), temp, OrderSummary::merge);
        }
        return  new ArrayList<OrderSummary>(ordSummary.values());
    }


}
