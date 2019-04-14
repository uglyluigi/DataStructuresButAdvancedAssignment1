package ug;

public class AVLNode<T extends Comparable<T>> extends BTNode<T> {
	private int height;

	public AVLNode(T data, AVLNode<T> left, AVLNode<T> right) {
		super(data, left, right);
		this.height = 0;
	}

	public AVLNode(T data) {
		this(data, null, null);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public AVLNode<T> getLeft() {
		return (AVLNode<T>) super.getLeft();
	}

	@Override
	public AVLNode<T> getRight() {
		return (AVLNode<T>) super.getRight();
	}
}
