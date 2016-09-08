package com.objectivelyradical.contribebookstore.core;

import java.math.BigDecimal;
import java.util.Objects;

// The main book class is little more than getters and setters.
public class Book {
	private String title;
	private String author;
	private BigDecimal price;
	
	public Book(String title, String author, BigDecimal price) {
		this.title = title;
		this.author = author;
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) {
			return false;
		}
		
		if(!(other instanceof Book)) {
			return false;
		}
		Book otherBook = (Book)other;
		return title.equals(otherBook.getTitle()) &&
				author.equals(otherBook.getAuthor()) &&
				price.equals(otherBook.getPrice());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(title, author, price);
	}
}
