package com.objectivelyradical.contribebookstore;

public interface BookDataParser {
	Node<Book, Integer> stringToBooks(String s);
}