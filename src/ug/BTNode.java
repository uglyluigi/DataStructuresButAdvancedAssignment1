package ug;

public class BTNode<T extends Comparable<T>> {
	private BTNode<T> left, right, parent;
	private T data;

	public BTNode(T data, BTNode<T> left, BTNode<T> right) {
		this.left = left;
		this.right = right;
		this.data = data;
	}

	public BTNode(T data) {
		this(data, null, null);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public BTNode<T> getParent() {
		return parent;
	}

	public void setParent(BTNode<T> parent) {
		this.parent = parent;
	}

	public BTNode<T> getLeft() {
		return left;
	}

	public void setLeft(BTNode<T> BTNode) {
		this.left = BTNode;
	}

	public BTNode<T> getRight() {
		return right;
	}

	public void setRight(BTNode<T> right) {
		this.right = right;
	}

	public boolean isToTheLeftOf(BTNode<T> node) {
		return node.getData().compareTo(this.getData()) > 0;
	}

	public boolean isToTheRightOf(BTNode<T> node) {
		return node.getData().compareTo(this.getData()) < 0;
	}

	public boolean isLeaf() {
		return this.getLeft() == null && this.getRight() == null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BTNode) {
			return ((BTNode<T>) obj).getData().compareTo(this.getData()) == 0;
		}

		return false;
	}

	@Override
	public String toString() {
		return this.data.toString();
	}
}