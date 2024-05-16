package Com.java.demo.Dao;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Com.java.Connect.DataConnect;
import Com.java.Exceptions.ProductNotFoundException;
import Com.java.Model.Cart;
import Com.java.Model.Customers;
import Com.java.Model.Order_items;
import Com.java.Model.Orders;
import Com.java.Model.Products;
import Com.java.demo.Dao.OrderProcessorRepsitory;

public class OrderProcessorRepositoryImpl implements OrderProcessorRepsitory {

	
	List<Products> products =new ArrayList();
	List<Customers> customers =new ArrayList();
	List<Integer> cart =new ArrayList();
	List<Integer> orders =new ArrayList();
	List<Integer> order_items=new ArrayList();

	private Connection con;
	PreparedStatement stat;
	
	public OrderProcessorRepositoryImpl ()
	{
		con=DataConnect.getConnect();
	}
	
	
	
	@Override
	public boolean createProduct(Products product) {
		
		
		try
		{
			 stat=con.prepareStatement("insert into Products values(?,?,?,?,?)");
	  stat.setInt(1, product.getProduct_id());
	  stat.setString(2,product.getName());
	  stat.setDouble(3, product.getPrice());
	  stat.setString(4,product.getDescription());
	  stat.setInt(5,product.getStockQuantity());
	  
	  stat.executeUpdate();

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean createCustomer(Customers customer) {
		
		try {
		stat =con.prepareStatement("insert into Customers values(?,?,?,?)");
		stat.setInt(1,customer.getCustomer_id());
		stat.setString(2, customer.getName());
		stat.setString(3, customer.getEmail());
		stat.setString(4, customer.getPassword());
		stat.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return false;
		
		
	}
	public boolean isCustomerExists(int customerId) {
	    try {
	        PreparedStatement stat = con.prepareStatement("SELECT * FROM Customers WHERE customer_id = ?");
	        stat.setInt(1, customerId);
	        ResultSet rs = stat.executeQuery();
	        return rs.next(); 
	    } catch (Exception e) {
	        System.out.println( e.getMessage());
	        return false; 
	    }
	}
	public boolean isProductExists(int productId) {
	    try {
	        PreparedStatement stat = con.prepareStatement("SELECT * FROM Products WHERE product_id = ?");
	        stat.setInt(1, productId);
	        ResultSet rs = stat.executeQuery();
	        return rs.next(); 
	    } catch (Exception e) {
	        System.out.println( e.getMessage());
	        return false; 
	    }
	}

	@Override
	public boolean deleteProduct(int product_id) {
		try {
			stat=con.prepareStatement("Delete from Products where product_id =?");
			stat.setInt(1,product_id);
			stat.executeUpdate();
		
			
	}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
			return false;
	}

	@Override
	public boolean deleteCustomer(int customer_id) {
		try {
			stat=con.prepareStatement("Delete from Customers where customer_id=?");
			stat.setInt(1, customer_id);
			stat.executeUpdate();
			
		
		return true;
		}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
		
		
	
		return false;
	}
	 public boolean isProductInCart(int customer_id, int product_id) {
	
	       
	        String sql = "SELECT COUNT(*) FROM Cart WHERE customer_id = ? AND product_id = ?";
	        
	        try (
	            
	            PreparedStatement statement = con.prepareStatement(sql);
	        		) {
	           
	            statement.setInt(1, customer_id);
	            statement.setInt(2, product_id);
	            
	      
	            try (ResultSet resultSet = statement.executeQuery()) {
	          
	                if (resultSet.next()) {
	                    int count = resultSet.getInt(1);
	                    return count > 0; 
	                }
	            }
	        } catch (Exception e) {
	            System.out.println( e.getMessage());
	        }
	        
	  
	        return false;
	    }
	    

	@Override
	public boolean addToCart(Customers customer,Products product,Cart cart) {
		try {
			stat=con.prepareStatement("insert into Cart values(?,?,?,?)");
			
            stat.setInt(1, cart.getCart_id());
			stat.setInt(2,customer.getCustomer_id() );
			stat.setInt(3, product.getProduct_id());
			stat.setInt(4, cart.getQuantity());
			stat.executeUpdate();
			System.out.println("Added to the cart");
			
			return true;
			}
			catch(Exception e)
			{
					System.out.println(e.getMessage());
			}
				
			
		
		
		 return false;
	}

	@Override
	public boolean removeFromCart(Customers customer, Products product) {
		try {
			stat=con.prepareStatement("delete from cart where customer_id=? and product_id=?");
			stat.setInt(1, customer.getCustomer_id());
			stat.setInt(2, product.getProduct_id());
			int rowsaffected=stat.executeUpdate();
			if(rowsaffected>0)
			{
				System.out.println("Successfully removed from Cart");
				return true;
			}
			else
			{
				System.out.println("Customer Id or Product Id not found");
				return false;
			}
		}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				return false;
				
			}
	}

	public void  getAllFromCart(Customers customer) {
	    try {
	        PreparedStatement stat = con.prepareStatement("SELECT * FROM Cart WHERE customer_id=?");
	        stat.setInt(1, customer.getCustomer_id());
	        ResultSet rs = stat.executeQuery();

	        boolean customerFound = false;  

	        while (rs.next()) {
	            customerFound = true;  

	            System.out.println("Cart Details:");
	            System.out.println("CartId: " + rs.getInt("cart_id"));
	            System.out.println("CustomerId: " + rs.getInt("customer_id"));
	            System.out.println("ProductId: " + rs.getInt("product_id"));
	            System.out.println("Quantity: " + rs.getInt("quantity"));
	            System.out.println(); 
	        }

	        if (!customerFound) {
	            System.out.println("Customer not found in the cart.");
	        }
	    } catch (Exception e) {
	        System.out.println( e.getMessage());
	    }
		
	}
	public double calculateTotalPrice(int productId, int quantity) throws ProductNotFoundException {
	    double total_price = 0.0;

	    try {
	        PreparedStatement stat = con.prepareStatement("SELECT price FROM Products WHERE product_id = ?");
	        stat.setInt(1, productId);
	        ResultSet rs = stat.executeQuery();
	        
	        if (rs.next()) {
	            double price = rs.getDouble("price");
	            total_price = price * quantity;
	        } else {
	            throw new ProductNotFoundException();
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }

	    return total_price;
	}
	
	@Override
	public String placeOrder(Customers customer, List<Map<Products, Integer>> productsQuantities, String shippingAddress, String order_date, double total_price) {
	    try {
	        stat = con.prepareStatement("insert into Orders (customer_id, order_date,total_price, shipping_address) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
	        stat.setInt(1, customer.getCustomer_id());
	        stat.setString(2, order_date);
	        stat.setDouble(3, total_price);
	        stat.setString(4, shippingAddress);
	        stat.executeUpdate();

	        ResultSet generatedKeys = stat.getGeneratedKeys();
	        int orderId = -1;
	        if (generatedKeys.next()) {
	            orderId = generatedKeys.getInt(1);
	        }

	        stat = con.prepareStatement("insert into orders_items (order_id, product_id, quantity) values (?,?,?)");
	        for (Map<Products, Integer> entry : productsQuantities) {
	            for (Map.Entry<Products, Integer> pair : entry.entrySet()) {
	                Products product = pair.getKey();
	                int quantity = pair.getValue();
	                stat.setInt(1, orderId);  
	                stat.setInt(2, product.getProduct_id());
	                stat.setInt(3, quantity);
	                stat.executeUpdate();
	            }
	        }
	        return "Order placed Successfully";
	    } catch (Exception e) {
	        System.out.println("Error placing order: " + e.getMessage());
	        return "Order placement failed";
	    }
	}
	@Override
	public List<Map<Products, Integer>> getOrdersByCustomer(int customer_id) {
		try {
			stat=con.prepareStatement("Select * from Orders where customer_id=?");
			stat.setInt(1, customer_id);
			ResultSet rs=stat.executeQuery();
			if(!rs.next())
			{
				System.out.println("Customer Not Found");
			}
			else{
			     do {
			    	 
			        System.out.println("Order Details....");
			        System.out.println("Order Id  : " + rs.getInt("order_id"));
			        System.out.println("Customer Id  : " + rs.getInt("customer_id"));
			        System.out.println("Order date  : " + rs.getString("order_date"));
			        System.out.println("Price   :" + rs.getDouble("total_price"));
			        System.out.println("Shipping Adrress  " + rs.getString("shipping_address"));
			        
			    	
			    	} while(rs.next()) ;
		}
		}   	
			    
			 catch (Exception e) {
				System.out.println(e.getMessage());
			
			    
	        } 
		
		
		return null;
	}
}