package com.tek.interview.question;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* ****************************************************************************************
 
Please remove all bugs from the code below to get the following output:

<pre>

*******Order 1*******
1 book: 13.74
1 music CD: 16.49
1 chocolate bar: 0.94
Sales Tax: 2.84
Total: 28.33
*******Order 2*******
1 imported box of chocolate: 11.5
1 imported bottle of perfume: 54.62
Sales Tax: 8.62
Total: 57.5
*******Order 3*******
1 Imported bottle of perfume: 32.19
1 bottle of perfume: 20.89
1 packet of headache pills: 10.73
1 box of imported chocolates: 12.94
Sales Tax: 8.77
Total: 67.98
Sum of orders: 153.81
 
</pre>
 
******************************************************************************************** */

/*
 * represents an item, contains a price and a description.
 *
 */
class Item {

	private String description;
	private double price;

	public Item(String description, double price) {
		super();
		this.description = description;
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}
}

/*
 * represents an order line which contains the @link Item and the quantity.
 *
 */
class OrderLine {

	private int quantity;
	private Item item;

	/*
	 * @param item Item of the order
	 * 
	 * @param quantity Quantity of the item
	 */
	public OrderLine(Item item, int quantity) throws Exception {
		if (item == null) {
			System.err.println("ERROR - Item is NULL");
			throw new Exception("Item is NULL");
		}
		assert quantity > 0;
		/*item = item;    //! should add this pointer before item
		quantity = quantity;   //!same as above
*/	
		this.item = item;
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public int getQuantity() {
		return quantity;
	}
}

class Order {

	private List<OrderLine> orderLines;

	public void add(OrderLine o) throws Exception {
		if (o == null) {
			System.err.println("ERROR - Order is NULL");
			throw new IllegalArgumentException("Order is NULL");
		}
		if (orderLines == null){
			orderLines = new ArrayList<OrderLine>();
		}
		orderLines.add(o);   //orderLines the list has not initialized
	}

	public int size() {
		return orderLines.size();
	}

	public OrderLine get(int i) {
		return orderLines.get(i);
	}

	public void clear() {
		this.orderLines.clear();
	}
}

class Calculator {

/*	public static double rounding(double value) {
		return ( (int) (value * 100)) / 100;
	}
	The round should keep two number after point
	*/

	private double grandtotal;
	private double grandtax;
	
	public double getGrandtotal() {
		return grandtotal;
	}
	public double getGrandtax() {
		return grandtax;
	}

	public static double rounding(double value) {
		return ( (double) (value * 100)) / 100;
	}
	
	/**
	 * receives a collection of orders. For each order, iterates on the order lines and calculate the total price which
	 * is the item's price * quantity * taxes.
	 * 
	 * For each order, print the total Sales Tax paid and Total price without taxes for this order
	 */
	public void calculate(Map<String, Order> o) {

		grandtotal = 0;
		grandtax = 0;
		// Iterate through the orders
		for (Map.Entry<String, Order> entry : o.entrySet()) {
			System.out.println("*******" + entry.getKey() + "*******");
//			grandtotal = 0;
	/* !grandtotal is to count total cost of all the order, but it is
	 *  set to 0 in every loop. so it shouldn't be here.  
	 */

			Order r = entry.getValue();

			double totalTax = 0;
			double total = 0;

			// Iterate through the items in the order
//			for (int i = 0; i <= r.size(); i++) {   
			//! shouldn't be <=, should be less, because array start with 0
			for (int i = 0; i < r.size(); i++) {
				// Calculate the taxes
				double tax = 0;

				//Here should multiply by the quantity of items
				if (r.get(i).getItem().getDescription().toLowerCase().contains("imported")) {
					tax = rounding(r.get(i).getItem().getPrice() * r.get(i).getQuantity() * 0.15); // Extra 5% tax on
					// imported items
				} else {
					tax = rounding(r.get(i).getItem().getPrice() * r.get(i).getQuantity() * 0.10);
				}

				// Calculate the total price
//				double totalprice = r.get(i).getItem().getPrice() + Math.floor(tax);
				double totalprice = rounding(r.get(i).getItem().getPrice() + tax);
				
				// Print out the item's total price
//				System.out.println(r.get(i).getItem().getDescription() + ": " + Math.floor(totalprice));
				// if the total is less than 1, than the tax will be 0 which is not
				// reasonable
				System.out.println(r.get(i).getItem().getDescription() + ": " + Math.floor(totalprice * 100) / 100);
				
				// Keep a running total
				totalTax += tax;
//				total += r.get(i).getItem().getPrice();
				// total of this item should add up with tax
				total += totalprice;
				
			}

			// Print out the total taxes
			System.out.println("Sales Tax: " + Math.floor(totalTax * 100) / 100);

			// Print out the total amount
			System.out.println("Total: " + Math.floor(total * 100) / 100 );
			grandtotal += total;
			grandtax += totalTax;
		}

		System.out.println("Sum of orders: " + Math.floor(grandtotal * 100) / 100);
	}
}

public class Foo {

	public static void main(String[] args) throws Exception {

		Map<String, Order> o = new HashMap<String, Order>();

		Order c = new Order();

		double grandTotal = 0;

		c.add(new OrderLine(new Item("book", (double) 12.49), 1));
		c.add(new OrderLine(new Item("music CD", (double) 14.99), 1));
		c.add(new OrderLine(new Item("chocolate bar", (double) 0.85), 1));

		o.put("Order 1", c);

		// Reuse cart for an other order
//		c.clear();
		// !We cannot reuse the Order reference c as this, it will change the
		// order we store in the map every time. We should give allocate memory
		// location to c every time we set new order
		c = new Order();

		c.add(new OrderLine(new Item("imported box of chocolate", (double) 10), 1));
		c.add(new OrderLine(new Item("imported bottle of perfume", (double) 47.50), 1));

		o.put("Order 2", c);

		// Reuse cart for an other order
//		c.clear();
		// !same mistake as above
		c = new Order();
		
		c.add(new OrderLine(new Item("Imported bottle of perfume", (double) 27.99), 1));
		c.add(new OrderLine(new Item("bottle of perfume", (double) 18.99), 1));
		c.add(new OrderLine(new Item("packet of headache pills", (double) 9.75), 1));
		c.add(new OrderLine(new Item("box of imported chocolates", (double) 11.25), 1));

		o.put("Order 3", c);

		new Calculator().calculate(o);

	}
}
