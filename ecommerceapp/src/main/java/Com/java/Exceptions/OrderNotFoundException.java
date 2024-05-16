package Com.java.Exceptions;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException() {
        super("Order not found");
    }
}