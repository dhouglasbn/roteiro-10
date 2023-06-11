package adt.avltree;

import adt.bst.BSTNode;
import adt.bst.BSTVerifierImpl;

/**
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeVerifierImpl<T extends Comparable<T>> extends BSTVerifierImpl<T> implements AVLTreeVerifier<T> {

	private AVLTreeImpl<T> avlTree;

	public AVLTreeVerifierImpl(AVLTree<T> avlTree) {
		super(avlTree);
		this.avlTree = (AVLTreeImpl<T>) avlTree;
	}

	private AVLTreeImpl<T> getAVLTree() {
		return avlTree;
	}

	@Override
	public boolean isAVLTree() {

		boolean isAVL = this.isAVLTree(this.avlTree.getRoot());
		
		return this.isBST() && isAVL;
	}

	private boolean isAVLTree(BSTNode<T> node) {
		boolean result = true;
		int balance = this.avlTree.calculateBalance(node);
		if (Math.abs(balance) > 1) {
			result = false;
		} else {
			if (!node.getLeft().isEmpty()) {
				isAVLTree((BSTNode<T>) node.getLeft());
			}
			if (!node.getRight().isEmpty()) {
				isAVLTree((BSTNode<T>) node.getRight());
			}
		}
		return result;
	}

}
