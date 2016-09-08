package com.objectivelyradical.contribebookstore;

import java.util.HashMap;
import java.util.HashSet;

import com.objectivelyradical.contribebookstore.core.Book;
import com.objectivelyradical.contribebookstore.core.BookList;
import com.objectivelyradical.contribebookstore.core.PurchaseStatus;

public class BookInventory implements BookList{
	private HashMap<Book, Integer> inventory = new HashMap<Book, Integer>();

	@Override
	public Book[] list(String searchString) {
		HashSet<Book> books = new HashSet<Book>(inventory.keySet());
		
		// Because we need to be able to display all the books, but our interface does not allow us a separate method,
		// we will instead say that a null search is for everything.
		if(searchString.length() > 0) {
			books.removeIf(b -> !b.getTitle().toUpperCase().contains(searchString.trim().toUpperCase()) 
					&& !b.getAuthor().toUpperCase().contains(searchString.trim().toUpperCase()));
		}
		
		return books.toArray(new Book[books.size()]);
		
	}

	@Override
	public boolean add(Book book, int quantity) {
		// This method returns true if we successfully add the entry, false otherwise
		if(book == null || quantity < 0) {
			return false;
		}
		if(inventory.containsKey(book)) {
			// Sanity check to ensure we are not setting an item with a negative stock count
			int currentCount = Math.max(0, inventory.get(book));
			inventory.put(book, currentCount + quantity);
			return true;
		} else {
			inventory.put(book, quantity);
			return true;
		}
	}
	
	public int getStock(Book book) {
		return inventory.containsKey(book) ? inventory.get(book) : -1;
	}

	@Override
	public int[] buy(Book... books) { 
		int[] results = new int[books.length];
		for(int i = 0; i < books.length; i++) {
			results[i] = buy(books[i]);
		}		
		return results;
	}
	
	private int buy(Book book) {
		if(!inventory.containsKey(book)) {
			return PurchaseStatus.DOES_NOT_EXIST;
		} else {
			int count = inventory.get(book);
			if(count > 0) {
				// Quick sanity check to make sure we cannot ever go below 0 stock
				int newCount = Math.max(inventory.get(book) -1, 0);
				inventory.put(book,  newCount);
				return PurchaseStatus.OK;
			} else {
				return PurchaseStatus.NOT_IN_STOCK;
			}
		}
	}

}
