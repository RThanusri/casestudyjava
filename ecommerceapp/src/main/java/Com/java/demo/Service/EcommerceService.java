package Com.java.demo.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Com.java.Exceptions.CustomerNotFoundException;
import Com.java.Exceptions.OrderNotFoundException;
import Com.java.Exceptions.ProductNotFoundException;
import Com.java.Model.*;
import Com.java.demo.Dao.OrderProcessorRepositoryImpl;

public class EcommerceService {
    Scanner sc;
    public OrderProcessorRepositoryImpl opr;
    String orderStatus;

    public EcommerceService() {
        sc = new Scanner(System.in);
        opr = new OrderProcessorRepositoryImpl();
    }

    public void addProducts() {
        Products p = new Products();

        
            System.out.println("Enter product ID:");
            p.setProduct_id(sc.nextInt());
            sc.nextLine();

            System.out.println("Enter name:");
            p.setName(sc.nextLine());

            System.out.println("Enter price:");
            p.setPrice(sc.nextDouble());
            sc.nextLine();

            System.out.println("Enter description:");
            p.setDescription(sc.nextLine());

            System.out.println("Enter stock quantity:");
            p.setStockQuantity(sc.nextInt());

            opr.createProduct(p);
            System.out.println("Product added successfully.");
    }
      

    public void addCustomers() {
        Customers c = new Customers();

    
        
            System.out.println("Enter customer ID:");
            c.setCustomer_id(sc.nextInt());
            sc.nextLine();

            System.out.println("Enter name:");
            c.setName(sc.nextLine());

            System.out.println("Enter email:");
            c.setEmail(sc.nextLine());

            System.out.println("Enter password:");
            c.setPassword(sc.nextLine());

            opr.createCustomer(c);
            System.out.println("Customer added successfully.");
       
    }

    public void removeProduct() {
        try {
            System.out.println("Enter product ID:");
            int productId = sc.nextInt();
            sc.nextLine();

            if (!opr.isProductExists(productId)) {
                throw new ProductNotFoundException();
            }

            opr.deleteProduct(productId);
            System.out.println("Product removed successfully.");
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        } 
    }

    public void removeCustomer() {
        try {
            System.out.println("Enter customer ID:");
            int customerId = sc.nextInt();
            sc.nextLine();

            if (!opr.isCustomerExists(customerId)) {
                throw new CustomerNotFoundException();
            }

            opr.deleteCustomer(customerId);
            System.out.println("Customer removed successfully.");
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        } 
    }

	 public void addtocart()
	 {
		 Cart ct=new Cart();
		 Customers c=new Customers();
		 Products p=new Products();
		 
		 System.out.println("Enter customer id");
		 
		 c.setCustomer_id(sc.nextInt());
		 
		 System.out.println("Enter product id");
		 
		 p.setProduct_id(sc.nextInt());
		 System.out.println("Enter quantity");
		 
		 int q=sc.nextInt();
		 ct.setQuantity(q);
		 opr.addToCart(c, p, ct);
		 }
	
	 
	 public void removeCart()
	 {
		
		 Customers c=new Customers();
		 Products p=new Products();
		 
		 System.out.println("Enter customer id");
		 
		 c.setCustomer_id(sc.nextInt());
		 
		 System.out.println("Enter product id");
		 
		 p.setProduct_id(sc.nextInt());
		 opr.removeFromCart(c, p);
		 }
		 
	 
	 public void viewcart()
	 {
		
		
		 Customers c=new Customers();
		 System.out.println("Enter customer id");
		 
		 c.setCustomer_id(sc.nextInt());
		 opr.getAllFromCart(c);
		 
	 }
	 public void getordersbycustomer() throws OrderNotFoundException {
		    try {
		        Customers c = new Customers();
		        System.out.println("Enter customer id:");
		        int customerId = sc.nextInt();
		        
		        
		        opr.getOrdersByCustomer(customerId);
		    } catch (Exception e) {
		        System.out.println( e.getMessage());
		    }
		}
	 public String placeOrder() {
		    try {
		        Customers customer = new Customers();

		        System.out.println("Enter customer ID:");
		        int customerId = sc.nextInt();
		        if (!opr.isCustomerExists(customerId)) {
		            throw new CustomerNotFoundException();
		        }
		        customer.setCustomer_id(customerId);
		        sc.nextLine();

		        System.out.println("Enter shipping address:");
		        String shippingAddress = sc.nextLine();
		        System.out.println("Enter order date:");
		        String order_date = sc.nextLine();

		        List<Map<Products, Integer>> productsQuantities = new ArrayList<>();

		        double total_price = 0.0; 

		        while (true) {
		            Map<Products, Integer> productQuantity = new HashMap<>(); 
		            Products product = new Products();
		            System.out.println("Enter product ID:");
		            int productId = sc.nextInt();

		            System.out.println("Enter quantity:");
		            int quantity = sc.nextInt();

		            if (!opr.isProductExists(productId)) {
		                throw new ProductNotFoundException();
		            }

		            double productTotalPrice = opr.calculateTotalPrice(productId, quantity); 
		            total_price += productTotalPrice; 

		            product.setProduct_id(productId);
		            productQuantity.put(product, quantity);  
		            productsQuantities.add(productQuantity); 

		            System.out.println("Do you want to add more products to the order? (yes/no)");
		            String choice = sc.next();
		            if (!choice.equalsIgnoreCase("yes")) {
		                break;
		            }
		        }

		        String status = opr.placeOrder(customer, productsQuantities, shippingAddress, order_date, total_price);
		        return status="Placed";
		    } catch (Exception e) {
		        System.out.println(e.getMessage());
		        return "Order placement failed";
		    }
		}
	 
		 
	 
	 
}

	

