package adt.bst;

/**
 * 
 * Performs consistency validations within a BST instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class BSTVerifierImpl<T extends Comparable<T>> implements BSTVerifier<T> {
	
	private BSTImpl<T> bst;

	public BSTVerifierImpl(BST<T> bst) {
		this.bst = (BSTImpl<T>) bst;
	}
	
	private BSTImpl<T> getBSt() {
		return bst;
	}

	@Override
	public boolean isBST() {
		return this.isBST(this.bst.root);
	}
	
	private boolean isBST(BSTNode<T> node) {
		boolean isBST = true;
		if (!node.getLeft().isEmpty()) {
			if (node.getLeft().getData().compareTo(node.getData()) == 1) {
				isBST = false;
			} else {
				isBST = isBST((BSTNode<T>) node.getLeft());
			}
		}
		if (isBST == true) {
			if (!node.getRight().isEmpty()) {
				if (node.getRight().getData().compareTo(node.getData()) == -1) {
					isBST = false;
				} else {
					isBST = isBST((BSTNode<T>) node.getRight());
				}
			}
		}
		return isBST;
	}
}
