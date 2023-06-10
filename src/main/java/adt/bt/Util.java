package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * @param node
	 * @return - noh que se tornou a nova raiz
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> pivot = (BSTNode<T>) node.getRight();
		BSTNode<T> pivotLeft = (BSTNode<T>) pivot.getLeft();
		
		// definindo o filho do parent de node
		if (!node.getParent().isEmpty()) {
			if (node.getParent().getLeft().equals(node)) {
				node.getParent().setLeft(pivot);
			} else {
				node.getParent().setRight(pivot);
			}
		}
				
		pivot.setLeft(node);
		pivot.setParent(node.getParent());
		node.setParent(pivot);
		node.setRight(pivotLeft);
		return pivot;
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * @param node
	 * @return noh que se tornou a nova raiz
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> pivot = (BSTNode<T>) node.getLeft();
		BSTNode<T> pivotRight = (BSTNode<T>) pivot.getRight();
		
		// definindo o filho do parent de node
		if (!node.getParent().isEmpty()) {
			if (node.getParent().getLeft().equals(node)) {
				node.getParent().setLeft(pivot);
			} else {
				node.getParent().setRight(pivot);
			}
		}
		
		
		pivot.setRight(node);
		pivot.setParent(node.getParent());
		node.setParent(pivot);
		node.setLeft(pivotRight);
		return pivot;
	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
