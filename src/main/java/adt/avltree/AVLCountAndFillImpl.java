package adt.avltree;

import adt.bst.BSTNode;
import adt.bt.Util;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends
		AVLTreeImpl<T> implements AVLCountAndFill<T> {

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;

	public AVLCountAndFillImpl() {
		
	}

	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	@Override
	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}

	@Override
	public void fillWithoutRebalance(T[] array) {
		for (T element: array) {
			this.insertWithoutRebalance(element);
		}
	}
	
	@Override
	protected void rebalance(BSTNode<T> node) {
		int balance = this.calculateBalance(node);
		
		if (Math.abs(balance) > 1) {
			if (balance > 0 && this.calculateBalance((BSTNode<T>) node.getLeft()) >= 0) { // LL
				Util.rightRotation(node);
				this.LLcounter++;
			} else if (balance < 0 && this.calculateBalance((BSTNode<T>) node.getRight()) <= 0) { // RR
				Util.leftRotation(node);
				this.RRcounter++;
			} else if (balance > 0 && this.calculateBalance((BSTNode<T>) node.getLeft()) < 0) { // LR
				Util.leftRotation((BSTNode<T>) node.getLeft());
				Util.rightRotation(node);
				this.LRcounter++;
			} else if (balance < 0 && this.calculateBalance((BSTNode<T>) node.getRight()) > 0) { // RL
				Util.rightRotation((BSTNode<T>) node.getRight());
				Util.leftRotation(node);
				this.RLcounter++;
			}
			if (!this.root.getParent().isEmpty()) {
				this.root = (BSTNode<T>) this.root.getParent();
			}
		}
	}
	
	private void insertWithoutRebalance(T element) {
		this.insertWithoutRebalance(element, this.root, new BSTNode<T>());
	}

	private void insertWithoutRebalance(T element, BSTNode<T> node, BSTNode<T> parent) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parent);
		} else {
			if (element.compareTo(node.getData()) == -1) {
				insertWithoutRebalance(element, (BSTNode<T>) node.getLeft(), node);
			} else if (element.compareTo(node.getData()) == 1) {
				insertWithoutRebalance(element, (BSTNode<T>) node.getRight(), node);
			}
		}
	}

}
