package com.objectivelyradical.contribebookstore;

import java.math.BigDecimal;

import com.objectivelyradical.contribebookstore.core.Book;
import com.objectivelyradical.contribebookstore.core.BookDataParser;
import com.objectivelyradical.contribebookstore.utility.Node;

public class ExampleBookParser implements BookDataParser{
	// This class takes a string and parses it into books. 
	// Since we're implementing BookDataParser, it's easy to repurpose for new projects.
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
