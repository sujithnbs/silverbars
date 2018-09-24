import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class OrderManagerTest {

    OrderManager mgr;
    OrderManager mgr2;

    @Before
    public void setUp() throws Exception {
        mgr = new OrderManager();
        mgr2 = new OrderManager();
    }

    @Test
    public void testRegisterOrder() throws Exception {
        String userId = "user1";
        BigDecimal orderQuant = BigDecimal.ONE;
        BigDecimal price = new BigDecimal("306");
        Order expected = new Order(userId, price, OrderType.SELL, orderQuant);

        assertTrue("Should be true ", mgr.registerOrder(userId, price, OrderType.SELL, orderQuant));
        assertThat(mgr.orders, hasItems(expected));
    }


    @Test
    public void testCancelOrder() throws Exception {
        String userId = "user1";
        BigDecimal orderQuant = BigDecimal.ONE;
        BigDecimal price = new BigDecimal("306");
        Order expected = new Order(userId, price, OrderType.SELL, orderQuant);
        mgr.registerOrder(userId, price, OrderType.SELL, orderQuant);
        mgr.registerOrder(userId, new BigDecimal("307"), OrderType.SELL, new BigDecimal("2.5"));

        assertThat(mgr.orders, hasItems(expected));

        assertTrue("Should be true ", mgr.cancelOrder(userId, price, OrderType.SELL, orderQuant));
        assertThat(mgr.orders, not(hasItem(expected)));
    }

    @Test
    public void testCancelOrderWhichDoesntExist() throws Exception {
        Order test = new Order("user2", new BigDecimal("306"), OrderType.SELL, new BigDecimal("2.5"));
        mgr.registerOrder("user2",  new BigDecimal("308"), OrderType.SELL, new BigDecimal("4.5"));
        mgr.registerOrder("user2", new BigDecimal("307"), OrderType.SELL, new BigDecimal("2.5"));

        assertThat(mgr.orders, not(hasItems(test)));
        assertFalse("Should be false ", mgr.cancelOrder("user2", new BigDecimal("306"), OrderType.SELL, new BigDecimal("2.5")));
    }

    @Test
    public void testGetOrderSummary() throws Exception {
        mgr2.registerOrder("user1", new BigDecimal("306"), OrderType.SELL, new BigDecimal("3.5"));
        mgr2.registerOrder("user2", new BigDecimal("310"), OrderType.SELL, new BigDecimal("1.2"));
        mgr2.registerOrder("user3", new BigDecimal("307"), OrderType.SELL, new BigDecimal("1.5"));
        mgr2.registerOrder("user4", new BigDecimal("306"), OrderType.SELL, new BigDecimal("2.0"));

        List<OrderSummary> expect = new ArrayList<>();
        expect.add(new OrderSummary(new BigDecimal("306"), OrderType.SELL, new BigDecimal("5.5")));
        expect.add(new OrderSummary(new BigDecimal("307"), OrderType.SELL, new BigDecimal("1.5")));
        expect.add(new OrderSummary(new BigDecimal("310"), OrderType.SELL, new BigDecimal("1.2")));

        List<OrderSummary> ordSumList = mgr2.getOrderSummary();
        Collections.sort(ordSumList);
        for (OrderSummary ord : ordSumList) {
            System.out.println(ord.getQuantity() + "kg for £" + ord.getPrice());
        }

        assertThat(ordSumList, is(expect));
    }


    @Test
    public void testGetOrderSummaryBUY() throws Exception {
        OrderManager mgr3 = new OrderManager();
        mgr3.registerOrder("user1", new BigDecimal("306"), OrderType.BUY, new BigDecimal("3.5"));
        mgr3.registerOrder("user2", new BigDecimal("310"), OrderType.BUY, new BigDecimal("1.2"));
        mgr3.registerOrder("user3", new BigDecimal("307"), OrderType.BUY, new BigDecimal("1.5"));
        mgr3.registerOrder("user4", new BigDecimal("306"), OrderType.BUY, new BigDecimal("2.0"));

        List<OrderSummary> expect = new ArrayList<>();
        expect.add(new OrderSummary(new BigDecimal("310"), OrderType.BUY, new BigDecimal("1.2")));
        expect.add(new OrderSummary(new BigDecimal("307"), OrderType.BUY, new BigDecimal("1.5")));
        expect.add(new OrderSummary(new BigDecimal("306"), OrderType.BUY, new BigDecimal("5.5")));

        List<OrderSummary> ordSumList = mgr3.getOrderSummary();
        Collections.sort(ordSumList);
        for (OrderSummary ord : ordSumList) {
            System.out.println(ord.getQuantity() + "kg for £" + ord.getPrice());
        }

        assertThat(ordSumList, is(expect));
    }

    @Test
    public void testOrderSummaryMixedOrders() throws Exception {
        OrderManager mgr3 = new OrderManager();
        mgr3.registerOrder("user1", new BigDecimal("306"), OrderType.BUY, new BigDecimal("3.5"));
        mgr3.registerOrder("user2", new BigDecimal("310"), OrderType.BUY, new BigDecimal("1.3"));
        mgr3.registerOrder("user3", new BigDecimal("307"), OrderType.BUY, new BigDecimal("1.5"));
        mgr3.registerOrder("user4", new BigDecimal("306"), OrderType.BUY, new BigDecimal("2.5"));

        mgr3.registerOrder("user1", new BigDecimal("315"), OrderType.SELL, new BigDecimal("1.9"));
        mgr3.registerOrder("user2", new BigDecimal("309"), OrderType.SELL, new BigDecimal("1.2"));
        mgr3.registerOrder("user3", new BigDecimal("315"), OrderType.SELL, new BigDecimal("3.6"));
        mgr3.registerOrder("user4", new BigDecimal("307"), OrderType.SELL, new BigDecimal("2.0"));

        List<OrderSummary> expect = new ArrayList<>();

        expect.add(new OrderSummary(new BigDecimal("310"), OrderType.BUY, new BigDecimal("1.3")));
        expect.add(new OrderSummary(new BigDecimal("306"), OrderType.BUY, new BigDecimal("6.0")));

        expect.add(new OrderSummary(new BigDecimal("307"), OrderType.SELL, new BigDecimal("0.5")));
        expect.add(new OrderSummary(new BigDecimal("309"), OrderType.SELL, new BigDecimal("1.2")));
        expect.add(new OrderSummary(new BigDecimal("315"), OrderType.SELL, new BigDecimal("5.5")));


        List<OrderSummary> ordSumList = mgr3.getOrderSummary();
        Collections.sort(ordSumList);
        for (OrderSummary ord : ordSumList) {
            System.out.println(ord.getQuantity() + "kg for £" + ord.getPrice());
        }

        assertThat(ordSumList, is(expect));
    }

}