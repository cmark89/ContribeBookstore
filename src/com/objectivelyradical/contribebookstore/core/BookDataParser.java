package com.objectivelyradical.contribebookstore.core;

import com.objectivelyradical.contribebookstore.utility.Node;

// This interface gives us a generic way to turn strings into books and quantities
public interface BookDataParser {
	Node<Book, Integer> stringToBooks(String s);
}