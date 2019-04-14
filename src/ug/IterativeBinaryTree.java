package ug;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;

import static ug.BinaryTree.Direction.LEFT;
import static ug.BinaryTree.Direction.RIGHT;

public class IterativeBinaryTree<T extends Comparable<T>> extends BinaryTree<T> {

	public IterativeBinaryTree() {

	}

	/**
	 * a
	 * Iterative version of insert.
	 * I'm actually proud of how cool my implementation
	 * of this ended up being.
	 *
	 * @param data the data to insert into the binary tree.
	 */
	@Override
	public void insert(T data) {
		BTNode<T> newNode = new BTNode<>(data);

		if (this.getRoot() == null) {
			this.setRoot(newNode);
			this.toRoot();
		} else {
			Direction d;

			do {
				if (newNode.isToTheLeftOf(this.getCurrentNode())) {
					d = LEFT;
				} else { //This also covers the case in which a node that contains the same data as another node is inserted.
					d = RIGHT; //In this case, as with the recursive impl of this function, it defaults to the right.
				}
			} while (this.next(d));

			switch (d) {
				case RIGHT:
					this.getCurrentNode().setRight(newNode);
					break;
				case LEFT:
					this.getCurrentNode().setLeft(newNode);
					break;
			}

			newNode.setParent(this.getCurrentNode());
			this.next(d);
		}
	}

	@Override
	public boolean remove(T data) {
		BTNode<T> searchNode = searchFromRoot(data);

		if (searchNode != null) {
			if (this.getRoot().equals(searchNode) && this.getRoot().isLeaf()) {
				this.setRoot(null);
				this.updateCurrNode(null);
				return true;
			}

			do {
				this.updateCurrNode(searchNode);

				while (this.getCurrentNode().getLeft() != null) {
					T dataToDelete = this.getCurrentNode().getData();
					T leftData = this.getCurrentNode().getLeft().getData();
					this.getCurrentNode().getLeft().setData(dataToDelete);
					this.getCurrentNode().setData(leftData);
					this.next(LEFT);
				}

				while (this.getCurrentNode().getRight() != null) {
					T dataToDelete = this.getCurrentNode().getData();
					T rightData = this.getCurrentNode().getRight().getData();
					this.getCurrentNode().getRight().setData(dataToDelete);
					this.getCurrentNode().setData(rightData);

					if (this.next(LEFT)) {
						break;
					}

					this.next(RIGHT);
				}
			} while (!this.getCurrentNode().isLeaf());

			BTNode<T> parentNode = this.getCurrentNode().getParent();

			if (this.getCurrentNode().equals(parentNode.getLeft())) {
				this.getCurrentNode().getParent().setLeft(null);
			} else if (this.getCurrentNode().equals(parentNode.getRight())) {
				this.getCurrentNode().getParent().setRight(null);
			}

			return true;
		}

		return false;
	}

	/* Thanks G4G for the following 2 functions */

	public void traverseInOrder(Consumer<BTNode<T>> consumer) {
		if (this.getRoot() == null) {
			return;
		}

		Stack<BTNode<T>> nodeStack = new Stack<>();
		BTNode<T> currentNode = this.getRoot();

		while (this.getCurrentNode() != null || nodeStack.size() > 0) {
			while (currentNode != null) {
				nodeStack.push(this.getCurrentNode());
				currentNode = currentNode.getLeft();
			}

			currentNode = nodeStack.pop();
			consumer.accept(currentNode);
			currentNode = currentNode.getRight();
		}
	}

	public void traverseBreadthFirst(Consumer<BTNode<T>> consumer) {
		Queue<BTNode<T>> nodeQueue = new LinkedList<>();

		if (this.getRoot() == null) {
			return;
		}

		nodeQueue.add(this.getRoot());

		while (!nodeQueue.isEmpty()) {
			BTNode<T> node = nodeQueue.remove();
			consumer.accept(node);
			if (node.getLeft() != null) {
				nodeQueue.add(node.getLeft());
			}

			if (node.getRight() != null) {
				nodeQueue.add(node.getRight());
			}
		}
	}
}
