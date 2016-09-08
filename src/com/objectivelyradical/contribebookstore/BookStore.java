package com.objectivelyradical.contribebookstore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;

import com.objectivelyradical.contribebookstore.core.Book;
import com.objectivelyradical.contribebookstore.core.BookDataParser;
import com.objectivelyradical.contribebookstore.core.BookList;
import com.objectivelyradical.contribebookstore.utility.Node;

// This is a simple implementation of our actual bookstore, and handles stock initialization and user interaction.
public class BookStore {
	BookList inventory;
	
	public BookStore() {
		this(null, null);
	}
	public BookStore(String url, BookDataParser parser) {
		inventory = new BookInventory();
		if(!url.isEmpty() && parser != null) {
			loadInitialBooks(url, parser);
		}
	}
	
	// In this case, we want the option to change our initial stock, so we hand the method a URL resource and a 
	// parser that knows what to do with the data.
	private void loadInitialBooks(String url, BookDataParser parser) {
		try {
			InputStreamReader isr = new InputStreamReader(new URL(url).openStream());
			BufferedReader reader = new BufferedReader(isr);
			
			String line = "";
			while((line = reader.readLine()) != null) {
				Node<Book, Integer> node = parser.stringToBooks(line);
				if(node != null) {
					inventory.add(node.getFirstElement(), node.getSecondElement());
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Book[] search(String searchString) {
		return inventory.list(searchString);
	}
	
	// Because our bookstore is going to be freely used by both customers and employees,
	// we're exposing a method to add new books to the store
	public boolean addNewBook(String title, String author, String price, int quantity) {
		if(title == null || title.isEmpty() || author == null || author.isEmpty() || 
				price == null || price.isEmpty() || quantity <= 0) {
			return false;
		}
		try {
			Book book = new Book(title, author, new BigDecimal(price));
			inventory.add(book, quantity);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	// This method just wraps the inventory's method
	public int[] buy(Book... books) {
		return inventory.buy(books);
	}
}
