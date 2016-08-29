package com.objectivelyradical.contribebookstore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class BookInventory implements BookList{
	private HashMap<Book, Integer> inventory = new HashMap<Book, Integer>();

	@Override
	public Book[] list(String searchString) {
		Set<Book> books = inventory.keySet();
		books.removeIf(b -> !b.getTitle().contains(searchString) 
				&& !b.getAuthor().contains(searchString));
		return (Book[])books.toArray();
	}

	@Override
	public boolean add(Book book, int quantity) {
		
		// TODO: What do these return values represent?
		
		// Also, based on the status codes we should probably figure out if we are removing 0
		// count books or not (looks like we're not, so it's not quite this simple)
		if(inventory.containsKey(book)) {
			inventory.put(book, inventory.get(book) + quantity);
			return true;
		} else {
			inventory.put(book, quantity);
			return false;
		}
	}

	@Override
	public int[] buy(Book... books) {
		// TODO foreach 
		int[] results = new int[books.length];
		for(int i = 0; i < books.length; i++) {
			
		}		
		
		return results;
	}

}
