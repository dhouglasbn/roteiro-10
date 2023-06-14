package adt.avltree;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import adt.bst.BST;
import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bst.BSTVerifier;
import adt.bst.BSTVerifierImpl;

public class StudentAVLTest {

	private AVLTree<Integer> avl;
	private BSTNode<Integer> NIL = new BSTNode<Integer>();

	@Before
	public void setUp() {
		avl = new AVLTreeImpl<Integer>();
	}

	@Test
	public void testInit() {
		assertTrue(avl.isEmpty());
		assertEquals(0, avl.size());
		assertEquals(-1, avl.height());
		assertEquals(NIL, avl.getRoot());
	}

	@Test
	public void testInsert() {
		avl.insert(-10);
		assertEquals(1, avl.size());
		assertEquals(0, avl.height());
		assertArrayEquals(new Integer[] { -10 }, avl.preOrder());

		assertFalse(avl.isEmpty());
		assertEquals(new Integer(-10), avl.getRoot().getData());

		avl.insert(-15);
		assertEquals(2, avl.size());
		assertEquals(1, avl.height());
		assertArrayEquals(new Integer[] { -10, -15 }, avl.preOrder());

		avl.insert(20);
		assertEquals(3, avl.size());
		assertEquals(1, avl.height());
		assertArrayEquals(new Integer[] { -10, -15, 20 }, avl.preOrder());
	}

	@Test
	public void testRemoveRebalanceLR() { // árvore pesa mais à esquerda
		avl.insert(55);
		avl.insert(9);
		avl.insert(91);
		avl.insert(12);

		avl.remove(-1);
		assertEquals(4, avl.size());

		avl.remove(91);
		assertEquals(3, avl.size());
		assertArrayEquals(new Integer[] { 12, 9, 55 }, avl.preOrder());

		avl.remove(12);
		assertEquals(2, avl.size());
		assertArrayEquals(new Integer[] { 55, 9 }, avl.preOrder());

		avl.remove(9);
		avl.remove(55);
		assertEquals(NIL, avl.getRoot());
		assertTrue(avl.isEmpty());
	}
	
	@Test
	public void testRemoveRebalanceRL() { // árvore pesa mais à esquerda
		avl.insert(55);
		avl.insert(9);
		avl.insert(91);
		avl.insert(62);

		avl.remove(9);
		assertEquals(3, avl.size());
		assertArrayEquals(new Integer[] { 62, 55, 91 }, avl.preOrder());

		avl.remove(62);
		assertEquals(2, avl.size());
		assertArrayEquals(new Integer[] { 91, 55 }, avl.preOrder());

		avl.remove(91);
		avl.remove(55);
		assertEquals(NIL, avl.getRoot());
		assertTrue(avl.isEmpty());
	}
	
	@Test
	public void testRemoveRebalanceLL() { // árvore pesa mais à esquerda
		avl.insert(55);
		avl.insert(9);
		avl.insert(91);
		avl.insert(7);
		avl.insert(10);

		avl.remove(91);
		assertEquals(4, avl.size());
		assertArrayEquals(new Integer[] { 9, 7, 55, 10 }, avl.preOrder());

		avl.remove(10);
		assertEquals(3, avl.size());
		assertArrayEquals(new Integer[] { 9, 7, 55 }, avl.preOrder());
		
		avl.remove(55);
		avl.remove(9);
		avl.remove(7);
		assertEquals(NIL, avl.getRoot());
		assertTrue(avl.isEmpty());
	}
	
	@Test
	public void testRemoveRebalanceRR() { // árvore pesa mais à esquerda
		avl.insert(55);
		avl.insert(9);
		avl.insert(91);
		avl.insert(95);
		avl.insert(90);

		avl.remove(9);
		assertEquals(4, avl.size());
		assertArrayEquals(new Integer[] { 91, 55, 90, 95 }, avl.preOrder());

		avl.remove(90);
		assertEquals(3, avl.size());
		assertArrayEquals(new Integer[] { 91, 55, 95 }, avl.preOrder());
		
		avl.remove(55);
		avl.remove(95);
		avl.remove(91);
		assertEquals(NIL, avl.getRoot());
		assertTrue(avl.isEmpty());
	}
	
	@Test
	public void testInsertRebalanceAtRoot() { // árvore pesa mais à esquerda
		avl.insert(9);
		avl.insert(7);
		avl.insert(55);
		avl.insert(10);
		avl.insert(60);

		avl.insert(65);
		assertEquals(6, avl.size());
		assertEquals(2, avl.height());
		assertArrayEquals(new Integer[] { 55, 9, 7, 10, 60, 65 }, avl.preOrder());
		assertEquals(new Integer(55), avl.getRoot().getData());
		assertFalse(avl.isEmpty());
	}
	
	@Test
	public void testIsBST() {
		BST<Integer> bst = new BSTImpl<Integer>();
		bst.insert(5);
		bst.insert(2);
		bst.insert(8);
		bst.insert(3);
		bst.insert(7);
		bst.insert(1);
		bst.insert(9);
		
		BSTVerifier<Integer> bstVerifier = new BSTVerifierImpl<Integer>(bst);
		
		assertTrue(bstVerifier.isBST());
	}
	
	@Test
	public void testIsEmptyBST() {
		BST<Integer> bst = new BSTImpl<Integer>();
		
		BSTVerifier<Integer> bstVerifier = new BSTVerifierImpl<Integer>(bst);
		
		assertTrue(bstVerifier.isBST());
	}
	
	@Test
	public void testIsNotBSTWithNullElement() {
		BST<Integer> bst = new BSTImpl<Integer>();
		bst.insert(5);
		bst.insert(2);
		bst.insert(8);
		bst.insert(3);
		bst.insert(7);
		bst.insert(1);
		bst.insert(9);
		
		BSTNode<Integer> node = (BSTNode<Integer>) bst.search(8);
		
		node.setData(null);
		
		BSTVerifier<Integer> bstVerifier = new BSTVerifierImpl<Integer>(bst);
		
		assertFalse(bstVerifier.isBST());
	}

	@Test
	public void testIsNotBSTWithHigher() {
		BST<Integer> bst = new BSTImpl<Integer>();
		bst.insert(5);
		bst.insert(2);
		bst.insert(8);
		bst.insert(3);
		bst.insert(7);
		bst.insert(1);
		bst.insert(9);
		
		BSTNode<Integer> node = (BSTNode<Integer>) bst.search(9);
		
		node.setData(-2);
		
		BSTVerifier<Integer> bstVerifier = new BSTVerifierImpl<Integer>(bst);
		
		assertFalse(bstVerifier.isBST());
	}
	
	@Test
	public void testIsNotBSTWithLower() {
		BST<Integer> bst = new BSTImpl<Integer>();
		bst.insert(5);
		bst.insert(2);
		bst.insert(8);
		bst.insert(3);
		bst.insert(7);
		bst.insert(1);
		bst.insert(9);
		
		BSTNode<Integer> node = (BSTNode<Integer>) bst.search(2);
		
		node.setData(11);
		
		BSTVerifier<Integer> bstVerifier = new BSTVerifierImpl<Integer>(bst);
		
		assertFalse(bstVerifier.isBST());
	}
	
	@Test
	public void testIsCompletedBalancedAVLTree() {
		AVLTree<Integer> avlTree = new AVLTreeImpl<Integer>();
		avlTree.insert(5);
		avlTree.insert(2);
		avlTree.insert(8);
		avlTree.insert(3);
		avlTree.insert(7);
		avlTree.insert(1);
		avlTree.insert(9);
		
		AVLTreeVerifier<Integer> avlVerifier = new AVLTreeVerifierImpl<Integer>(avlTree);
		
		assertTrue(avlVerifier.isAVLTree());
	}
	
	@Test
	public void testIsAlmostCompletedBalancedAVLTree() {
		AVLTree<Integer> avlTree = new AVLTreeImpl<Integer>();
		avlTree.insert(5);
		avlTree.insert(2);
		avlTree.insert(8);
		avlTree.insert(7);
		avlTree.insert(1); 
		
		AVLTreeVerifier<Integer> avlVerifier = new AVLTreeVerifierImpl<Integer>(avlTree);
		
		assertTrue(avlVerifier.isAVLTree());
	}
	
	@Test
	public void testIsEmptyAVLTree() {
		AVLTree<Integer> avlTree = new AVLTreeImpl<Integer>();
		
		AVLTreeVerifier<Integer> avlVerifier = new AVLTreeVerifierImpl<Integer>(avlTree);
		
		assertTrue(avlVerifier.isAVLTree());
	}
	
	@Test
	public void testIsNotAVLTreeWithNullElement() {
		AVLTree<Integer> avlTree = new AVLTreeImpl<Integer>();
		avlTree.insert(5);
		avlTree.insert(2);
		avlTree.insert(8);
		avlTree.insert(3);
		avlTree.insert(7);
		avlTree.insert(1);
		avlTree.insert(9);
		
		BSTNode<Integer> node = (BSTNode<Integer>) avlTree.search(8);
		
		node.setData(null);
		
		AVLTreeVerifier<Integer> avlVerifier = new AVLTreeVerifierImpl<Integer>(avlTree);
		
		assertFalse(avlVerifier.isAVLTree());
	}

	@Test
	public void testIsNotAVLTreeWithHigher() {
		AVLTree<Integer> avlTree = new AVLTreeImpl<Integer>();
		avlTree.insert(5);
		avlTree.insert(2);
		avlTree.insert(8);
		avlTree.insert(3);
		avlTree.insert(7);
		avlTree.insert(1);
		avlTree.insert(9);
		
		BSTNode<Integer> node = (BSTNode<Integer>) avlTree.search(9);
		
		node.setData(-2);
		
		AVLTreeVerifier<Integer> avlVerifier = new AVLTreeVerifierImpl<Integer>(avlTree);
		
		assertFalse(avlVerifier.isAVLTree());
	}
	
	@Test
	public void testIsNotAVLTreeWithLower() {
		AVLTree<Integer> avlTree = new AVLTreeImpl<Integer>();
		avlTree.insert(5);
		avlTree.insert(2);
		avlTree.insert(8);
		avlTree.insert(3);
		avlTree.insert(7);
		avlTree.insert(1);
		avlTree.insert(9);
		
		BSTNode<Integer> node = (BSTNode<Integer>) avlTree.search(2);
		
		node.setData(11);
		
		AVLTreeVerifier<Integer> avlVerifier = new AVLTreeVerifierImpl<Integer>(avlTree);
		
		assertFalse(avlVerifier.isAVLTree());
	}
	
	@Test
	public void testIsUnbalancedLeftAVLTree() {
		AVLTree<Integer> avlTree = new AVLTreeImpl<Integer>();
		avlTree.insert(5);
		avlTree.insert(2);
		
		BSTNode<Integer> node = (BSTNode<Integer>) avlTree.search(2);
		BSTNode<Integer> newNode = new BSTNode<Integer>();
		newNode.setData(1);
		newNode.setParent(node);
		newNode.setLeft(new BSTNode<Integer>());
		newNode.setRight(new BSTNode<Integer>());
		
		node.setLeft(newNode);
		
		AVLTreeVerifier<Integer> avlVerifier = new AVLTreeVerifierImpl<Integer>(avlTree);
		
		assertFalse(avlVerifier.isAVLTree());
	}
	
	@Test
	public void testIsUnbalancedRightAVLTree() {
		AVLTree<Integer> avlTree = new AVLTreeImpl<Integer>();
		avlTree.insert(5);
		avlTree.insert(8);
		
		BSTNode<Integer> node = (BSTNode<Integer>) avlTree.search(8);
		BSTNode<Integer> newNode = new BSTNode<Integer>();
		newNode.setData(10);
		newNode.setParent(node);
		newNode.setLeft(new BSTNode<Integer>());
		newNode.setRight(new BSTNode<Integer>());
		
		node.setRight(newNode);
		
		AVLTreeVerifier<Integer> avlVerifier = new AVLTreeVerifierImpl<Integer>(avlTree);
		
		assertFalse(avlVerifier.isAVLTree());
	}
	
	@Test
	public void testIsUnbalancedLeftRightAVLTree() {
		AVLTree<Integer> avlTree = new AVLTreeImpl<Integer>();
		avlTree.insert(5);
		avlTree.insert(2);
		
		BSTNode<Integer> node = (BSTNode<Integer>) avlTree.search(2);
		BSTNode<Integer> newNode = new BSTNode<Integer>();
		newNode.setData(3);
		newNode.setParent(node);
		newNode.setLeft(new BSTNode<Integer>());
		newNode.setRight(new BSTNode<Integer>());
		
		node.setRight(newNode);
		
		AVLTreeVerifier<Integer> avlVerifier = new AVLTreeVerifierImpl<Integer>(avlTree);
		
		assertFalse(avlVerifier.isAVLTree());
	}
	
	@Test
	public void testIsUnbalancedRightLeftAVLTree() {
		AVLTree<Integer> avlTree = new AVLTreeImpl<Integer>();
		avlTree.insert(5);
		avlTree.insert(8);
		
		BSTNode<Integer> node = (BSTNode<Integer>) avlTree.search(8);
		BSTNode<Integer> newNode = new BSTNode<Integer>();
		newNode.setData(6);
		newNode.setParent(node);
		newNode.setLeft(new BSTNode<Integer>());
		newNode.setRight(new BSTNode<Integer>());
		
		node.setLeft(newNode);
		
		AVLTreeVerifier<Integer> avlVerifier = new AVLTreeVerifierImpl<Integer>(avlTree);
		
		assertFalse(avlVerifier.isAVLTree());
	}
}
