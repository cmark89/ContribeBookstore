package com.objectivelyradical.contribebookstore.utility;

// Extremely simple, generic wrapper to act as a node for any two classes we need.
// In this case we just want an easy way to associate an arbitrary piece of data
// with a stock quantity.
public class Node<T,U> {
	T t;
	U u;
	public Node(T t, U u) {
		this.t = t;
		this.u = u;
	}
	public T getFirstElement() {
		return t;
	}
	public U getSecondElement() {
		return u;
	}
}
