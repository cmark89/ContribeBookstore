package com.objectivelyradical.contribebookstore;

import java.math.BigDecimal;

public class ExampleBookParser implements BookDataParser{
	public ExampleBookParser() {
	}
	// Return a wrapper with a book, and the number to add
	public Node<Book, Integer> stringToBooks(String s) {
		String[] parts = s.split(";");
		if(parts.length == 4) {
			return new Node<Book, Integer>(new Book(parts[0], parts[1], new BigDecimal(parts[2].replaceAll(",", ""))),
					Integer.parseInt(parts[3]));
		} else {
			return null;
		}
	}
}
