package Com.java.Client;

import java.util.Scanner;

import Com.java.Exceptions.OrderNotFoundException;

import Com.java.demo.Service.EcommerceService;

public class Main {
	static Scanner sc=new Scanner(System.in);

	public static void main(String[] args) throws OrderNotFoundException  {
		EcommerceService er =new EcommerceService();
		
		
		
		while(true)
		{
			
		System.out.println("1..Register customer");
		System.out.println("2..Create product");
		System.out.println("3..Delete product");
		System.out.println("4..Delete customer");
		System.out.println("5..Add to the cart");
		System.out.println("6..Remove from cart");
	
		
		System.out.println("7..View Cart");
		System.out.println("8..Place order");
		System.out.println("9..View customer orders");
		
		
		System.out.println("Choose your option");
		int ch=sc.nextInt();
		
		switch(ch) {
		case 1:
			er.addCustomers();
			break;
			
		
		case 2:
			er.addProducts();
			break;
		
		case 3:
		
			er.removeProduct();
			break;
		
		case 4:
			er.removeCustomer();
			break;
		
		case 5:
			er.addtocart();
			break;
			
		case 6:
			er.removeCart();
			break;
			
		case 7:
			er.viewcart();
			break;
			
		case 8:
			er.placeOrder();
			break;
			
		case 9:
			er.getordersbycustomer(); 
			break;
			
		
		
		case 10:
		    System.out.println("Exiting");
			System.exit(0);
			break;
		
			
		
		default:
			System.out.println("Invalid choice");
			break;

		}
		}
	}
}
