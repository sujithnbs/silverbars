import java.math.BigDecimal;

public class Order {
    private BigDecimal price;
    private OrderType type;
    private BigDecimal quantity;
    private String userId;

    public Order(String userId, BigDecimal price, OrderType type, BigDecimal quantity) {
        this.userId = userId;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderType getType() {
        return type;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (price != null ? !(price.compareTo(order.price)==0) : order.price != null) return false;
        if (type != order.type) return false;
        if (quantity != null ? !(quantity.compareTo(order.quantity)==0) : order.quantity != null) return false;
        return userId != null ? userId.equals(order.userId) : order.userId == null;
    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }


}
