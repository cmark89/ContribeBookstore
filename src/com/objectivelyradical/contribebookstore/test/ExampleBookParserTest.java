package com.objectivelyradical.contribebookstore.test;

import org.junit.Test;

import com.objectivelyradical.contribebookstore.ExampleBookParser;
import com.objectivelyradical.contribebookstore.core.Book;
import com.objectivelyradical.contribebookstore.utility.Node;

public class ExampleBookParserTest {
	@Test
	public void testStringToBooks() {
		ExampleBookParser parser = new ExampleBookParser();
		String s = "The Big Book of Testing Puns;Corey Mark;199.00;1000";
		Node<Book, Integer> resultNode = parser.stringToBooks(s);
		Book b = resultNode.getFirstElement();
		
		assert(b.getTitle().equals("The Big Book of Testing Puns"));
		assert(resultNode.getSecondElement().equals(1000));
	}
}
