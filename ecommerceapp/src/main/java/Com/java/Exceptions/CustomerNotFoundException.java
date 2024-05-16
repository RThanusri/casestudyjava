package Com.java.Exceptions;

public class CustomerNotFoundException extends Exception {
	public CustomerNotFoundException() {
        super("Customer ID not found:");
    }
}
