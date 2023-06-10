package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Implementacao de uma arvore AVL
 * A CLASSE AVLTree herda de BSTImpl. VOCE PRECISA SOBRESCREVER A IMPLEMENTACAO
 * DE BSTIMPL RECEBIDA COM SUA IMPLEMENTACAO "OU ENTAO" IMPLEMENTAR OS SEGUITNES
 * METODOS QUE SERAO TESTADOS NA CLASSE AVLTREE:
 *  - insert
 *  - preOrder
 *  - postOrder
 *  - remove
 *  - height
 *  - size
 *
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements
		AVLTree<T> {

	@Override
	public void insert(T element) {
		this.recursiveInsert(element, this.root, new BSTNode<T>());
	}
	
	@Override
	public void remove(T element) {
		BSTNode<T> found = this.search(element);
		this.recursiveRemove(found);
	}

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		int result = 0;
		if (!node.isEmpty()) {
			result = this.height((BSTNode<T>) node.getLeft()) - this.height((BSTNode<T>) node.getRight());
		}
		return result;
	}
	
	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		int balance = this.calculateBalance(node);
		
		if (Math.abs(balance) > 1) {
			if (balance > 0 && this.calculateBalance((BSTNode<T>) node.getLeft()) >= 0) { // LL
				Util.rightRotation(node);
			} else if (balance < 0 && this.calculateBalance((BSTNode<T>) node.getRight()) <= 0) { // RR
				Util.leftRotation(node);
			} else if (balance > 0 && this.calculateBalance((BSTNode<T>) node.getLeft()) < 0) { // LR
				Util.leftRotation((BSTNode<T>) node.getLeft());
				Util.rightRotation(node);
			} else if (balance < 0 && this.calculateBalance((BSTNode<T>) node.getRight()) > 0) { // RL
				Util.rightRotation((BSTNode<T>) node.getRight());
				Util.leftRotation(node);
			}
		}
	}
	
	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		
		while (parent != null) {
			this.rebalance(parent);
			parent = (BSTNode<T>) parent.getParent();
		}
	}
	
	private void recursiveInsert(T element, BSTNode<T> node, BSTNode<T> parent) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parent);
		} else {
			if (element.compareTo(node.getData()) == -1) {
				recursiveInsert(element, (BSTNode<T>) node.getLeft(), node);
			} else if (element.compareTo(node.getData()) == 1) {
				recursiveInsert(element, (BSTNode<T>) node.getRight(), node);
			}
			this.rebalance(node);
		}
	}
	
	private void recursiveRemove(BSTNode<T> node) {
		if (!node.isEmpty()) {
			if (node.isLeaf()) { // leaf
				node = new BSTNode<T>();
				this.rebalanceUp(node);
			} else if (
					(!node.getLeft().isEmpty() && node.getRight().isEmpty()) ||
					(node.getLeft().isEmpty() && !node.getRight().isEmpty())
					) { // has one child
				if(!node.equals(this.root)) {
					if (node.getParent().getLeft().equals(node)) { // node is left child
						if (!node.getLeft().isEmpty()) { // node has left child
							node.getParent().setLeft((BSTNode<T>) node.getLeft());
						} else { // node has right child
							node.getParent().setLeft((BSTNode<T>) node.getRight());
						}
					} else { // node is right child
						if (!node.getLeft().isEmpty()) { // node has left child
							node.getParent().setRight((BSTNode<T>) node.getLeft());
						} else { // node has right child
							node.getParent().setRight((BSTNode<T>) node.getRight());
						}
					}
				} else {
					if (!node.getLeft().isEmpty()) {
						this.root = (BSTNode<T>) node.getLeft();
					} else {
						this.root = (BSTNode<T>) node.getRight();
					}
				}
				this.rebalanceUp(node);
			} else {
				BSTNode<T> sucessor = this.sucessor(node.getData());
				node.setData(sucessor.getData());
				this.recursiveRemove(sucessor);
			}
		}
	}
}
