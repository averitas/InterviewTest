package com.tek.interview.question;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

public class FooTestCase extends TestCase {
	
	Map<String, Order> o = new HashMap<String, Order>();

	public static double rounding(double value) {
		return ( (double) (value * 100)) / 100;
	}
	
	@Override
	protected void setUp() throws Exception {
		Order c = new Order();

		c.add(new OrderLine(new Item("Imported bottle of perfume", (double) 27.99), 1));
		c.add(new OrderLine(new Item("bottle of perfume", (double) 18.99), 1));
		c.add(new OrderLine(new Item("packet of headache pills", (double) 9.75), 1));
		c.add(new OrderLine(new Item("box of imported chocolates", (double) 11.25), 1));

		o.put("Order 3", c);

	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	/*this test case uses the original implementation in class calculator which calculate
	 *the sum of orders. But we can see that the total price and the total price that
	 *this order should be is not equal. So there are some thing wrong in the calculation
	 *of the total price. 
	 * */
	@Test
	public void testTotalPrice(){
		
		Calculator c = new Calculator();
		
		c.calculate(o);
		
		Order r = o.get("Order 3");
		
		double total=0;
		
		for (int i = 0; i < r.size(); i++) {
			// Calculate the taxes
			double tax = 0;

			if (r.get(i).getItem().getDescription().toLowerCase().contains("imported")) {
				tax = rounding(r.get(i).getItem().getPrice() * 0.15); // Extra 5% tax on
				// imported items
			} else {
				tax = rounding(r.get(i).getItem().getPrice() * 0.10);
			}

			double totalprice = rounding(r.get(i).getItem().getPrice() + tax);

			total += totalprice;
			
		}
			
		System.out.println(rounding(13.85));
		
		assertEquals(c.getGrandtotal(), total);
	}
	
	@Test
	public void testTotalTax(){
		Calculator c = new Calculator();
		
		Order r = o.get("Order 3");
		
		c.doCalculate(r);
		
		double totalTax=0;
		
		for (int i = 0; i < r.size(); i++) {
			// Calculate the taxes
			double tax = 0;

			if (r.get(i).getItem().getDescription().toLowerCase().contains("imported")) {
				tax = rounding(r.get(i).getItem().getPrice() * 0.15); // Extra 5% tax on
				// imported items
			} else {
				tax = rounding(r.get(i).getItem().getPrice() * 0.10);
			}

			totalTax += tax;
			
		}
		
		assertEquals(c.getCurTax(), totalTax);
	}
	
	@Test
	public void testOrderInitialization() {
		Order odr = new Order();
		try {
			odr.add(new OrderLine(new Item("test",(double)1.1), 1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(odr.size(),1);
	}
	
	@Test
	public void testOrderLine(){
		OrderLine ol = o.get("Order 3").get(0);
		assertEquals(ol.getQuantity(),1);
		assertEquals(ol.getItem().getPrice(),(double) 27.99);
	}
	
}
