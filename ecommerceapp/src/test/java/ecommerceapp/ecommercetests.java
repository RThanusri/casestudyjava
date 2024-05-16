package ecommerceapp;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Com.java.Exceptions.CustomerNotFoundException;
import Com.java.Exceptions.ProductNotFoundException;
import Com.java.Model.Cart;
import Com.java.Model.Customers;
import Com.java.Model.Products;
import Com.java.demo.Dao.OrderProcessorRepositoryImpl;
import Com.java.demo.Service.EcommerceService;

class ecommercetests {

	EcommerceService service = new EcommerceService();
    Customers customer = new Customers();
    Products product = new Products();
    Cart cartItem = new Cart();
    
    private OrderProcessorRepositoryImpl orderProcessorRepository=new OrderProcessorRepositoryImpl();

	
    public void setUp() {
        orderProcessorRepository = new OrderProcessorRepositoryImpl();
    }

    @Test
    public void testCustomerNotFound() throws CustomerNotFoundException {
        int customerId = 999; 
        boolean exists = orderProcessorRepository.isCustomerExists(customerId);
        assertFalse(exists);
        orderProcessorRepository.isCustomerExists(customerId); 
    }

    @Test
    public void testProductNotFound() throws ProductNotFoundException {
        int productId = 999; 
        boolean exists = orderProcessorRepository.isProductExists(productId);
        assertFalse(exists); 
        orderProcessorRepository.isProductExists(productId); 
    }

	 @Test
	    public void testPlaceOrder() {
	       
	    	  
	    	
	    	        String result = service.placeOrder();

	    	       
	    	        assertEquals("Placed", result);
	    	    }
		@Test
	    public void testProductCreation() {
	        
	        
	      
	        product.setProduct_id(60);
	        product.setName("earings"); 
	        product.setPrice(10.99); 
	        product.setDescription("Jhumka"); 
	        product.setStockQuantity(100); 
	        
	     
	        service.addProducts();
	        
	      
	        assertTrue(service.opr.isProductExists(product.getProduct_id()));
	    
	}
		@Test
	    public void testProductAddedToCart() {
	        
	        
	   
	        customer.setCustomer_id(809); 
	        
	        product.setProduct_id(70); 
	        
	        cartItem.setQuantity(2); 
	        
	     
	        service.addtocart();
	        
	        assertTrue(service.opr.isProductInCart(customer.getCustomer_id(), product.getProduct_id()));
	    }
		    

}