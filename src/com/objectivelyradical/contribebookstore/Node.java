package com.objectivelyradical.contribebookstore;

// Extremely simple, generic wrapper to act as a node for any two classes we need.
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
