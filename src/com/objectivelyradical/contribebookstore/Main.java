package com.objectivelyradical.contribebookstore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	final static String DEFAULT_BOOK_URL = "http://www.contribe.se/bookstoredata/bookstoredata.txt";
	static BookStore bookStore;
	static ArrayList<Book> cart = new ArrayList<Book>();
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
				break;
			case(3):
				cartScreen();
				break;
			case(4):
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
		String rawSearch = "";
		while(true) {
			clear();
			System.out.println("+++++++++++++++++++++++++");
			System.out.println("+ SEARCH THE BOOK STORE +");
			System.out.println("+++++++++++++++++++++++++");
			System.out.println("");
			if(emptyResults) {
				System.out.println("No results found for \"" + rawSearch + "\".");
			}
			System.out.println("Enter your search term. Press ENTER when finished. Enter \"<\" to return to the main menu.");
			System.out.println("");
			System.out.println("Search: ");
			
			rawSearch = s.next();
			String searchString = s.next().trim().toUpperCase();
			if(searchString.equals("<")) {
				return;
			}
			if(searchString.equals(System.lineSeparator())) {
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
				System.out.println("Results for '" + searchString + "':");
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
			System.out.println("Buy this book? (Y/N)");
			
			String next = s.next().trim().toUpperCase();
			if(next.equals("Y")) {
				// Put the book in our cart and then return to the results
				cart.add(book);
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
			for(int i = 0; i < cart.size(); i++) {
				b = cart.get(i);
				System.out.println((i + 1) + ") " + b.getTitle() + ", " + b.getAuthor() + ", " + b.getPrice());
				total = total.add(b.getPrice());
			}
			System.out.println("");
			System.out.println("Total: " + total);
			System.out.println("");
			System.out.println("E) Empty cart, <) Back to main menu");
			
			String next = s.next().trim().toUpperCase();
			if(next.equals("E")) {
				// Clear the cart, and head back to the main menu
				cart = new ArrayList<Book>();
				return;
			} else if(next.equals("<")) {
				// Just go back to the main menu
				return;
			}
		}
		
	}
}
