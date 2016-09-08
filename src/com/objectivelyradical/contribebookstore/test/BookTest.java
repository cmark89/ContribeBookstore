package com.objectivelyradical.contribebookstore.test;

import java.math.BigDecimal;

import org.junit.Test;

import com.objectivelyradical.contribebookstore.core.Book;
public class BookTest {

	@Test
	public void testConstructor() {
		String title = "The Big Book of Testing";
		String author = "Baron von Unit Test";
		BigDecimal price = new BigDecimal(29.95);
		Book book = new Book(title, author, price);
		
		assert(book.getTitle().equals(title));
		assert(book.getAuthor().equals(author));
		assert(book.getPrice().equals(price));
	}
	
	@Test
	public void testEquality() {
		String title = "Unit Tests and You";
		String author = "Dr. Wiggles";
		BigDecimal price = new BigDecimal(75.00);
		Book bookA = new Book(title, author, price);
		Book bookB = new Book(title, author, price);
		
		assert(bookA.equals(bookB));
		assert(bookB.equals(bookA));
	}
	
	@Test
	public void testNonequality() {
		String title = "Automated Testing and A.I.";
		String author = "Corey Mark";
		BigDecimal price = new BigDecimal(19.99);
		Book mainBook = new Book(title, author, price);
		
		assert(!mainBook.equals(title));
		assert(!mainBook.equals(author));
		assert(!mainBook.equals(price));
		assert(!mainBook.equals(null));
		
		String otherTitle = "A.I. and Automated Testing";
		String otherAuthor = "Morey Cark";
		BigDecimal otherPrice = new BigDecimal(99.91);
		Book bookA = new Book(otherTitle, author, price);
		Book bookB = new Book(title, otherAuthor, price);
		Book bookC = new Book(title, author, otherPrice);
		
		assert(!mainBook.equals(bookA));
		assert(!mainBook.equals(bookB));
		assert(!mainBook.equals(bookC));
		
		assert(!bookA.equals(bookB));
		assert(!bookA.equals(bookC));
		assert(!bookB.equals(bookC));
	}

}
