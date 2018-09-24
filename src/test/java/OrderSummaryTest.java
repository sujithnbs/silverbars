import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;


public class OrderSummaryTest {

    @Test
    public void addSellOrder() throws Exception {
        OrderSummary expected = new OrderSummary(new BigDecimal(306), OrderType.SELL, new BigDecimal(5.5));
        OrderSummary sum1 = new OrderSummary(new BigDecimal(306), OrderType.SELL, new BigDecimal(3.5));
        sum1.merge(OrderType.SELL, new BigDecimal(2.0));
       assertEquals("failure - should be equal", sum1, expected);
    }

    @Test
    public void addBuyOrder() throws Exception {
        OrderSummary expected = new OrderSummary(new BigDecimal(306), OrderType.BUY, new BigDecimal(5.5));
        OrderSummary sum1 = new OrderSummary(new BigDecimal(306), OrderType.BUY, new BigDecimal(3.5));
        sum1.merge(OrderType.BUY, new BigDecimal(2.0));
        assertEquals("failure - should be equal", sum1, expected);
    }

    @Test
    public void addSelltoBuyOrder1() throws Exception {
        OrderSummary expected = new OrderSummary(new BigDecimal(306), OrderType.BUY, new BigDecimal(1));
        OrderSummary sum1 = new OrderSummary(new BigDecimal(306), OrderType.BUY, new BigDecimal(3.5));
        sum1.merge(OrderType.SELL, new BigDecimal(2.5));
        assertEquals("failure - should be equal", sum1, expected);
    }

    @Test
    public void addSelltoBuyOrder2() throws Exception {
        OrderSummary expected = new OrderSummary(new BigDecimal(306), OrderType.SELL, new BigDecimal(1));
        OrderSummary sum1 = new OrderSummary(new BigDecimal(306), OrderType.BUY, new BigDecimal(3.5));
        sum1.merge(OrderType.SELL, new BigDecimal(4.5));
        assertEquals("failure - should be equal", sum1, expected);
    }
}