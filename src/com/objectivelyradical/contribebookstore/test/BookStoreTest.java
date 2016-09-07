package com.objectivelyradical.contribebookstore.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.objectivelyradical.contribebookstore.BookStore;
import com.objectivelyradical.contribebookstore.ExampleBookParser;

public class BookStoreTest {
	
	@Test
	public void testInitialize() {
		try {
			new BookStore("http://www.contribe.se/bookstoredata/bookstoredata.txt", new ExampleBookParser());	
		} catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void testAddBook() {
		try {
			BookStore bookStore = new BookStore("http://www.contribe.se/bookstoredata/bookstoredata.txt", new ExampleBookParser());			
			assert(bookStore.addNewBook("The Newest Book So Far", "Corey Mark", "22.22", 1));
			assert(!bookStore.addNewBook(null, null, null, -100));
		} catch(Exception e) {
			fail();
		}	
	}
	
	@Test
	public void testSearch() {
		try {
			BookStore bookStore = new BookStore("http://www.contribe.se/bookstoredata/bookstoredata.txt", new ExampleBookParser());
			bookStore.addNewBook("The Newest Book So Far", "Corey Mark", "22.22", 1);
			assert(bookStore.search("The Newest Book So Far").length > 0);
			assert(bookStore.search("Rich Bloke").length > 0);
			assert(bookStore.search("Poor Bloke").length == 0);
		} catch(Exception e) {
			fail();
		}
	}
}
