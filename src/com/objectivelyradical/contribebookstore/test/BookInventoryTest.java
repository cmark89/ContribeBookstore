package com.objectivelyradical.contribebookstore.test;

import java.math.BigDecimal;

import org.junit.Test;

import com.objectivelyradical.contribebookstore.BookInventory;
import com.objectivelyradical.contribebookstore.core.Book;
import com.objectivelyradical.contribebookstore.core.PurchaseStatus;

public class BookInventoryTest {
	
	// Create a set of books to use in our tests
	private Book[] testBooks = new Book[] {
		new Book("Unit Tests 101", "Dr. Wiggles", new BigDecimal(19.99)),
		new Book("Amazing Unit Tests", "Dr. Wiggles", new BigDecimal(4.99)),
		new Book("Doctor Unit Test VS The Wolf Man", "Stephen King", new BigDecimal(12.50)),
		new Book("Hark! A Unit Test!", "Saito Iron", new BigDecimal(19.99)),
		new Book("The New Testament", "Mark et al.", new BigDecimal(29.99)),
		new Book("Test Practices Throughout History", "Dr. Wiggles", new BigDecimal(79.90)),
		new Book("Testers in a Test Land", "Billiard Shakespeare", new BigDecimal(15.00)),
		new Book("Testio and Julitest", "Billiard Shakespeare", new BigDecimal(15.00)),
		new Book("500 Detestable Puns", "Corey Mark", new BigDecimal(3.99)),
		new Book("500 Detestable Puns", "Corey Mark", new BigDecimal(299.99)),
	};

	@Test
	public void testAdd() {
		BookInventory inventory = new BookInventory();
		
		assert(inventory.add(testBooks[0], 20));
		assert(inventory.add(testBooks[3], 70));
		assert(!inventory.add(testBooks[6], -100));
		assert(!inventory.add(null, 50));
		assert(!inventory.add(null, 0));
	}
	
	@Test
	public void testList() {
		BookInventory inventory = new BookInventory();
		for(int i = 0; i < testBooks.length; i++) {
			inventory.add(testBooks[i], 5);
		}

		Book[] results = inventory.list("tESt");
		assert(results.length == 10);
		
		results = inventory.list("DR. WIGGLES");
		assert(results.length == 3);

		results = inventory.list("500");
		assert(results.length == 2);
		
		results = inventory.list("mark");
		assert(results.length == 3);
		
		results = inventory.list("Abdul Alhazred");
		assert(results.length == 0);
		
		results = inventory.list("Hark! A Unit Test?");
		assert(results.length == 0);
		
		results = inventory.list("Hark! A Unit Test!");
		assert(results.length == 1);
		
		results = inventory.list("");
		assert(results.length == 10);
	}
	
	@Test
	public void testBuy() {
		BookInventory inventory = new BookInventory();
		for(int i = 0; i < testBooks.length; i++) {
			inventory.add(testBooks[i], 5);
		}
		
		int[] results = new int[]{};
		
		Book[] cart = inventory.list("Mark");
		results = inventory.buy(cart);
		assert(results.length == cart.length);
		assert(results.length == 3);
		for(int i = 0; i < results.length; i++) {
			assert(results[i] == PurchaseStatus.OK);
		}
		
		cart = new Book[] {
			testBooks[0], testBooks[0], testBooks[0], testBooks[0], testBooks[0],
			testBooks[0], testBooks[0], testBooks[0], testBooks[0], testBooks[0],
		};
		results = inventory.buy(cart);
		assert(results.length == cart.length);
		assert(results.length == 10);
		for(int i = 0; i < results.length; i++) {
			assert(results[i] == (i < 5 ? PurchaseStatus.OK : PurchaseStatus.NOT_IN_STOCK));
		}
		
		cart = new Book[] {
			testBooks[1], testBooks[2], testBooks[0], testBooks[0], null,
			new Book("The Wild Adventures of Dr. Testenstein", "Farmer McDonald", new BigDecimal(100.00))
		};
		
		results = inventory.buy(cart);
		assert(results.length == 6);
		int expected = 0;
		for(int i = 0; i < results.length; i++) {
			switch(i) {
				case(0):
				case(1):
					expected = PurchaseStatus.OK;
					break;
				case(2):
				case(3):
					expected = PurchaseStatus.NOT_IN_STOCK;
					break;
				case(4):
				case(5):
					expected = PurchaseStatus.DOES_NOT_EXIST;
					break;
				default:
					break;
			}
			assert(results[i] == expected);
		}
	}
}
