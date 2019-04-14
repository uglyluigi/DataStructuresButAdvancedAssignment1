package ug;

public class RedBlackTree<T extends Comparable<T>> extends BinaryTree<T> {

	@Override
	public void insert(T data) {
		RBNode<T> newNode = new RBNode<>(data);

		if (this.getRoot() == null) {
			newNode.setBlack();
		} //New nodes are red by default

		super.insert_r(newNode);
	}



	@Override
	public RBNode<T> getRoot() {
		return (RBNode<T>) super.getRoot();
	}

	@Override
	public RBNode<T> getCurrentNode() {
		return (RBNode<T>) super.getCurrentNode();
	}
}
