import java.math.BigDecimal;

public class OrderSummary implements Comparable<OrderSummary> {
    private BigDecimal price;
    private OrderType type;
    private BigDecimal quantity;

    public OrderSummary(BigDecimal price) {
        this.price = price;
        this.type = OrderType.SELL;
        this.quantity = BigDecimal.ZERO;
    }

    public OrderSummary(BigDecimal price, OrderType type, BigDecimal quantity) {
        this.price = price;
        this.type = type;
        this.quantity = quantity;
    }

    public OrderSummary merge(OrderSummary ordSum) {
        return merge(ordSum.getType(), ordSum.getQuantity());
    }

    public OrderSummary merge(OrderType type, BigDecimal quantity) {
        if (type == this.type) {
            this.quantity = this.quantity.add(quantity);
        } else {
            BigDecimal temp = this.quantity.subtract(quantity);
            if (temp.compareTo(BigDecimal.ZERO) < 0) {
                if (OrderType.BUY == this.type)
                    this.type = OrderType.SELL;
                else
                    this.type = OrderType.BUY;
                this.quantity = temp.abs();
            } else
                this.quantity = temp;
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderSummary that = (OrderSummary) o;

        if (price != null ? !(price.compareTo(that.price) == 0) : that.price != null) return false;
        if (type != that.type) return false;
        return quantity != null ? quantity.compareTo(that.quantity) == 0 : that.quantity == null;
    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return quantity + "kg for £" + price;
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

    @Override
    public int compareTo(OrderSummary o) {
        if (this.type != o.getType())
            return this.type.compareTo(o.getType());

        if (this.type == OrderType.SELL)
            return this.getPrice().compareTo(o.getPrice());
        else
            return o.getPrice().compareTo(this.getPrice());
    }




}
