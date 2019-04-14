package ug;

import static java.lang.Math.max;

public class AVLTree<T extends Comparable<T>> extends BinaryTree<T> {

	public AVLTree() {
	}

	@Override
	public void insert(T data) {
		AVLNode<T> node = new AVLNode<>(data);
		node.setHeight(1 + max(height(node.getLeft()), height(node.getRight())));
		super.insert_r(node);
		int balance = getBalance(node);

		if (node.getLeft() != null) {
			if (balance > 1) {
				int res = data.compareTo(node.getLeft().getData());

				if (res < 0) {
					this.setRoot(rotateRight(node));
				} else if (res > 0) {
					node.setLeft(rotateLeft(node));
					this.setRoot(rotateRight(node));
				}
			}
		}

		if (node.getRight() != null) {
			int res = data.compareTo(node.getRight().getData());

			if (balance < -1) {
				if (res < 0) {
					this.setRoot(rotateLeft(node));
				} else if (res > 0) {
					node.setRight(rotateRight(node));
					this.setRoot(rotateLeft(node));
				}
			}
		}
	}

	@Override
	public boolean remove(T data) {
		if (super.remove(data)) {
			if (this.getRoot() == null) {
				return true;
			}

			this.getRoot().setHeight(max(height(this.getRoot().getLeft()), height(this.getRoot().getRight())));

			int balance = getBalance(this.getRoot());

			if (balance > 1 && getBalance(this.getRoot().getLeft()) >= 0) {
				this.setRoot(rotateRight(this.getRoot()));
				return true;
			}

			if (balance > 1 && getBalance(this.getRoot().getLeft()) < 0) {
				this.getRoot().setLeft(rotateLeft(this.getRoot().getLeft()));
				this.setRoot(rotateRight(this.getRoot()));
				return true;
			}

			if (balance < -1 && getBalance(this.getRoot().getRight()) <= 0) {
				this.setRoot(rotateLeft(this.getRoot()));
				return true;
			}

			if (balance < -1 && getBalance(this.getRoot().getRight()) > 0) {
				this.getRoot().setRight(rotateRight(this.getRoot().getRight()));
				this.setRoot(rotateLeft(this.getRoot()));
				return true;
			}
		}

		return false;
	}

	private int height(AVLNode<T> node) {
		return node.getHeight();
	}

	private AVLNode<T> rotateRight(AVLNode<T> node) {
		AVLNode<T> x = node.getLeft();
		AVLNode<T> T2 = x.getRight();

		x.setRight(node);
		node.setLeft(T2);

		return getTbtNode(node, x);
	}

	private AVLNode<T> rotateLeft(AVLNode<T> node) {
		AVLNode<T> y = node.getRight();
		AVLNode<T> T2 = y.getLeft();

		y.setLeft(node);
		node.setRight(T2);

		return getTbtNode(node, y);
	}

	private AVLNode<T> getTbtNode(AVLNode<T> node, AVLNode<T> x) {
		node.setHeight(max(height(node.getLeft()), height(node.getRight())) + 1);
		x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);
		return x;
	}

	public int getBalance(AVLNode<T> node) {
		if (node == null) {
			return 0;
		}

		return height(node.getLeft()) - height(node.getRight());
	}

	@Override
	public AVLNode<T> getRoot() {
		return (AVLNode<T>) super.getRoot();
	}
}
