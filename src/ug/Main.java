package ug;

import static ug.BinaryTree.Order.DF_IN_ORDER;

public class Main {

	public static void main(String[] args) {
		AVLTree<String> tree = new AVLTree<>();
		tree.insert("B");
		tree.insert("12");
		tree.insert("42069");
		tree.insert("D");
		tree.traverse(DF_IN_ORDER, System.out::println);
		tree.remove("D");
		tree.traverse(DF_IN_ORDER, System.out::println);

	}
}
