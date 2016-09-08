package com.objectivelyradical.contribebookstore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Main {
	final static String DEFAULT_BOOK_URL = "http://www.contribe.se/bookstoredata/bookstoredata.txt";
	static BookStore bookStore;
	static HashMap<Book, Integer> cart = new HashMap<Book, Integer>();
	static Scanner s;
	
	// The main class, which operates as a rudimentary front-end. It interacts only with the middleware-like BookStore class.
	public static void main(String[] args) {
		bookStore = new BookStore(DEFAULT_BOOK_URL, new ExampleBookParser());	
		
		// Initialize the main user input loop.
		s = new Scanner(System.in);
		mainLoop();
	}
	
	private static void mainLoop() {
		while(true) {
			switch(topScreen()) {
			case(1):
				searchScreen();
				break;
			case(2):
				addBookScreen();
				break;
			case(3):
				cartScreen();
				break;
			case(4):
				checkout();
				break;
			case(5):
				System.exit(0);
			default:
				break;
			}
		}
	}
	
	private static void clear() {
		for(int i = 0; i < 100; i++) {
			System.out.println("");
		}
	}
	private static int topScreen() {
		clear();
		System.out.println("+++++++++++++++++++++++");
		System.out.println("+ CONTRIBE BOOK STORE +");
		System.out.println("+      Main Menu      +");
		System.out.println("+++++++++++++++++++++++");
		System.out.println("");
		System.out.println("");
		System.out.println("1\tSearch for a book");
		System.out.println("2\tAdd a book");
		System.out.println("3\tCheck Your Cart");
		System.out.println("4\tCheck Out");
		System.out.println("5\tExit");
		System.out.println("");
		System.out.println("");
		System.out.print("Please enter a command: ");
		
		return s.nextInt();
	}
	
	private static void searchScreen() {
		boolean emptyResults = false;
		String searchString = "";
		while(true) {
			clear();
			System.out.println("+++++++++++++++++++++++++");
			System.out.println("+ SEARCH THE BOOK STORE +");
			System.out.println("+++++++++++++++++++++++++");
			System.out.println("");
			if(emptyResults) {
				System.out.println("No results found for \"" + searchString + "\".");
			}
			System.out.println("Enter your search term. Press ENTER when finished. Enter \"<\" to return to the main menu.");
			System.out.println("");
			System.out.println("Search: ");
			
			// This looks pointless, but it prevents an overread so we can detect an empty search
			s = new Scanner(System.in);
			searchString = s.nextLine();
			if(searchString.equals("<")) {
				return;
			}
			Book[] books = bookStore.search(searchString);
			emptyResults = books.length == 0;
			if(emptyResults) {
				continue;
			}
			boolean onResults = true;
			while(onResults) {
				clear();
				System.out.println("++++++++++++++++++++++++");
				System.out.println("+    SEARCH RESULTS    +");
				System.out.println("++++++++++++++++++++++++");
				if(!searchString.isEmpty()) {
					System.out.println("Results for '" + searchString + "':");
				}
				System.out.println("");
				for(int i = 0; i < Math.min(10, books.length); i++) {
					System.out.println((i + 1) + ") " + books[i].getTitle() + ", " + books[i].getAuthor() + ", " + books[i].getPrice());
				}
				System.out.println("");
				System.out.println("B) Back to search, <) Main menu");
				System.out.println("");
				if(s.hasNextInt()) {
					int next = s.nextInt();
					if(next - 1 < books.length) {
						//System.out.println("SELECTED " + books[next - 1].getTitle());
						bookScreen(books[next - 1]);
					}
				} else if(s.hasNext()) {
					String command = s.next().trim().toUpperCase();
					if(command.equals("B")) {
						onResults = false;
					} else if (command.equals("<")){
						return;
					}
				}
			}
		}
	}
	
	private static void bookScreen(Book book) {
		while(true) {
			clear();
			System.out.println("++++++++++++++++++++++++");
			System.out.println("+   BOOK INFORMATION   +");
			System.out.println("++++++++++++++++++++++++");
			System.out.println("");
			System.out.println("Title: " + book.getTitle());
			System.out.println("Author: " + book.getAuthor());
			System.out.println("Price: " + book.getPrice());
			System.out.println("");
			System.out.println("Add this book to your cart? (Y/N)");
			
			String next = s.next().trim().toUpperCase();
			if(next.equals("Y")) {
				// Put the book in our cart and then return to the results
				if(cart.containsKey(book)) {
					cart.put(book, cart.get(book) + 1);
				} else {
					cart.put(book, 1);
				}
				return;
			} else if(next.equals("N")) {
				// Return to the search results here
				return;
			}
		}
	}
	
	private static void cartScreen() {
		while(true) {
			clear();
			System.out.println("+++++++++++++++++++++++++");
			System.out.println("+     SHOPPING CART     +");
			System.out.println("+++++++++++++++++++++++++");
			System.out.println("");
			Book b;
			BigDecimal total = new BigDecimal(0);
			int i = 0;
			if(cart.keySet().size() == 0) {
				System.out.println("");
				System.out.println("No items in your cart (yet).");
			} else {
				for (Book key : cart.keySet()) {
					b = key;
					System.out.println((i + 1) + ") " + b.getTitle() + ", " + b.getAuthor() + ", " + b.getPrice() + "     x" + cart.get(b));
					total = total.add((b.getPrice().multiply(new BigDecimal(cart.get(b)))));
					
					i++;
				}
				System.out.println("");
				System.out.println("Total: " + total);
				System.out.println("");
			}
			
			System.out.println("E) Empty cart, <) Back to main menu");
			
			String next = s.next().trim().toUpperCase();
			if(next.equals("E")) {
				// Clear the cart, and head back to the main menu
				cart = new HashMap<Book, Integer>();
				return;
			} else if(next.equals("<")) {
				// Just go back to the main menu
				return;
			}
		}
	}
	
	private static void checkout() {
		clear();
		s = new Scanner(System.in);
		
		// Prep the array we're going to purchase
		ArrayList<Book> bookList = new ArrayList<Book>();
		for(Book key : cart.keySet()) {
			for(int i = 0; i < cart.get(key); i++) {
				bookList.add(key);				
			}
		}
		Book[] books = bookList.toArray(new Book[bookList.size()]);
		int length = bookList.size();
		if(length > 0) {
			int[] results = bookStore.buy(books);
			cart.clear();
			int errorCount = 0;
			for(int i = 0; i < length; i++) {
				if(results[i] == PurchaseStatus.OK) {
					continue;
				} else {
					errorCount++;
					clear();
					String error;
					if(results[i] == PurchaseStatus.DOES_NOT_EXIST) {
						error = "Unable to find book: " + books[i].getTitle();
					} else if(results[i] == PurchaseStatus.NOT_IN_STOCK) {
						error = "The following book is out of stock: " + books[i].getTitle();
					} else {
						error = "An unforseen error has occurred. Please contact the programmer for a full refund.";
					}
					System.out.println("++++++++++++++++++++++++++");
					System.out.println("+        CHECKOUT        +");
					System.out.println("++++++++++++++++++++++++++");
					System.out.println("");
					System.out.println(error);
					System.out.println("");
					System.out.println("Press ENTER to continue");
					s = new Scanner(System.in);
					s.nextLine();
				}				
				System.out.println("");
			}
			if(errorCount < length) {

				clear();
				System.out.println("++++++++++++++++++++++++++");
				System.out.println("+        CHECKOUT        +");
				System.out.println("++++++++++++++++++++++++++");
				System.out.println("");
				System.out.println("Successfully purchased " + (length - errorCount) + " of " + length + " books.");
				System.out.println("Please have a nice day.");
				System.out.println("");
				System.out.println("Press ENTER to return to the main menu");
				if(s.hasNextLine()) {
					return;
				}
			}
			return;
		} else {

			clear();
			System.out.println("++++++++++++++++++++++++++");
			System.out.println("+        CHECKOUT        +");
			System.out.println("++++++++++++++++++++++++++");
			System.out.println("");
			System.out.println("Your cart is currently empty. Please add some books before checkout out.");
			System.out.println("");
			System.out.println("Press ENTER to return to the main menu");
			if(s.hasNextLine()) {
				return;
			}
		}
	}
	
	
	
	// This is the worst method I've ever written.
	private static void addBookScreen() {
		while(true) {
			String title = "";
			String author = "";
			BigDecimal price;
			int quantity = 0;
			
			clear();
			System.out.println("++++++++++++++++++++++++");
			System.out.println("+     ADD NEW BOOK     +");
			System.out.println("++++++++++++++++++++++++");
			System.out.println("");
			System.out.println("Enter book title, or \"<\" to return to the main menu.");
			System.out.println("");
			System.out.print("Title: ");
			s = new Scanner(System.in);
			String next = s.nextLine().trim();
			if(next.equals("<")) {
				// Back to the main menu
				return;
			} else {
				title = next;
				while(true) {
					clear();
					System.out.println("++++++++++++++++++++++++");
					System.out.println("+     ADD NEW BOOK     +");
					System.out.println("++++++++++++++++++++++++");
					System.out.println("");
					System.out.println("Enter book author, \",\" to go back, or \"<\" to return to the main menu.");
					System.out.println("");
					System.out.print("Author: ");
					next = s.nextLine().trim();
					if(next.equals("<")) {
						// Back to the main menu
						return;
					} else if(next.toUpperCase().equals(",")){
						break;
					} else {
						author = next;
						while(true) {
							clear();
							System.out.println("++++++++++++++++++++++++");
							System.out.println("+     ADD NEW BOOK     +");
							System.out.println("++++++++++++++++++++++++");
							System.out.println("");
							System.out.println("Enter book price, \",\" to go back, or \"<\" to return to the main menu.");
							System.out.println("");
							System.out.print("Price: ");
							boolean isBigDecimal = s.hasNextBigDecimal();
							next = s.nextLine().trim();
							if(next.equals("<")) {
								// Back to the main menu
								return;
							} else if(next.equals(",")){
								break;
							} else if(isBigDecimal){
								price = new BigDecimal(next);
								while(true) {
									clear();
									System.out.println("++++++++++++++++++++++++");
									System.out.println("+     ADD NEW BOOK     +");
									System.out.println("++++++++++++++++++++++++");
									System.out.println("");
									System.out.println("Enter quantity, \",\" to go back, or \"<\" to return to the main menu.");
									System.out.println("");
									System.out.print("Quantity: ");
									boolean isInt = s.hasNextInt();
									next = s.nextLine().trim();
									if(next.equals("<")) {
										// Back to the main menu
										return;
									} else if(next.toUpperCase().equals(",")){
										break;
									} else if(isInt){
										quantity = Integer.parseInt(next);
										while(true) {
											clear();
											System.out.println("++++++++++++++++++++++++");
											System.out.println("+     ADD NEW BOOK     +");
											System.out.println("++++++++++++++++++++++++");
											System.out.println("");
											System.out.println("Title: " + title);
											System.out.println("Author: " + author);
											System.out.println("Price: " + price);
											System.out.println("Quantity: " + quantity);
											System.out.println("");
											System.out.println("Add this book (Y/N)? Or enter \",\" to go back, or \"<\" to return to the main menu.");
											System.out.println("");
											next = s.nextLine().trim().toUpperCase();
											if(next.equals("<") || next.toUpperCase().equals("N")) {
												return;
											} else if (next.toUpperCase().equals(",")){
												break;
											} else if (next.toUpperCase().equals("Y")) {
												bookStore.addNewBook(title,  author,  price.toString(), quantity);
												return;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
