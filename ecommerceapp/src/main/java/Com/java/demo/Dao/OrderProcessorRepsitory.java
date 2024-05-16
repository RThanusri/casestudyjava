package Com.java.demo.Dao;

import java.util.List;
import java.util.Map;

import Com.java.Model.Cart;
import Com.java.Model.Customers;
import Com.java.Model.Orders;
import Com.java.Model.Products;

public interface OrderProcessorRepsitory {
	
	abstract boolean createProduct(Products product);
	abstract boolean createCustomer(Customers customer);
	abstract boolean deleteProduct(int product_id);
	abstract boolean deleteCustomer(int customer_id);
	abstract boolean addToCart(Customers customer, Products product ,Cart cart);
	abstract boolean removeFromCart(Customers customer, Products product );
	abstract void getAllFromCart(Customers customer);
	

	
	List<Map<Products, Integer>> getOrdersByCustomer(int customer_id);
	
	
	
	String placeOrder(Customers customer, List<Map<Products, Integer>> productsQuantities, String shippingAddress,
			String order_date,double total_price);
	
	}