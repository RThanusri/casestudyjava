package Com.java.Exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException() {
        super("ProductId not found");
    }
}