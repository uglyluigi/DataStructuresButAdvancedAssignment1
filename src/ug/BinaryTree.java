package ug;

import java.util.function.Consumer;

import static ug.BinaryTree.Direction.LEFT;
import static ug.BinaryTree.Direction.RIGHT;

public class BinaryTree<T extends Comparable<T>> {
	private BTNode<T> root;
	private BTNode<T> currentNode;

	public BinaryTree() {
	}

	public T getValue() {
		return currentNode.getData();
	}

	public void toRoot() {
		this.currentNode = this.root;
	}

	public void prev() {
		this.currentNode = this.currentNode.getParent();
	}

	public BTNode<T> getCurrentNode() {
		return this.currentNode;
	}

	public boolean next(Direction direction) {
		BTNode<T> nextNode = this.currentNode.getLeft();

		switch (direction) {
			case LEFT:
				nextNode = this.currentNode.getLeft();
				break;
			case RIGHT:
				nextNode = this.currentNode.getRight();
				break;
		}

		if (nextNode != null) {
			this.updateCurrNode(nextNode);
			return true;
		} else {
			return false;
		}
	}

	public void insert(T data) {
		this.toRoot();
		this.insert_r(new BTNode<>(data));
	}

	/**
	 * Inserts the given data into the tree recursively.
	 * This function will begin its insertion traversal
	 * at whatever currentNode is. If you want it to properly
	 * insert from the root of the tree, use insertFromRoot.
	 *
	 * @param node the node to insert.
	 */
	public void insert_r(BTNode<T> node) {
		boolean insertionOccurred = false;

		if (this.root == null) {
			this.root = node;
			this.toRoot();
			return;
		} else {
			if (node.isToTheLeftOf(this.currentNode)) {
				if (this.currentNode.getLeft() == null) {
					this.currentNode.setLeft(node);
					insertionOccurred = true;
				}

				node.setParent(this.currentNode);
				this.next(LEFT);
			} else {
				if (this.currentNode.getRight() == null) {
					this.currentNode.setRight(node);
					insertionOccurred = true;
				}

				node.setParent(this.currentNode);
				this.next(RIGHT);
			}
		}

		if (!insertionOccurred) {
			this.insert_r(node);
		}
	}

	public BTNode<T> searchFromRoot(T data) {
		return search(this.root, data);
	}

	public BTNode<T> search(BTNode<T> start, T data) {
		assert start != null;
		BTNode<T> nextNode = null;

		if (start.getData().equals(data)) {
			return start;
		} else if (start.getData().compareTo(data) > 0) {
			nextNode = start.getLeft();
		} else if (start.getData().compareTo(data) < 0) {
			nextNode = start.getRight();
		}

		if (nextNode == null) {
			return null;
		}

		return search(nextNode, data);
	}

	public boolean remove(T data) {
		BTNode<T> searchNode = searchFromRoot(data);

		if (searchNode != null) {
			return remove_r(searchNode);
		}

		return false;
	}

	private boolean remove_r(BTNode<T> start) {
		this.updateCurrNode(start);

		if (this.currentNode.isLeaf()) {
			if (this.currentNode.equals(this.root)) {
				this.root = null;
				this.currentNode = null;
				return true;
			}

			if (this.currentNode.equals(this.currentNode.getParent().getRight())) {
				this.currentNode.getParent().setRight(null);
			} else if (this.currentNode.equals(this.currentNode.getParent().getLeft())) {
				this.currentNode.getParent().setLeft(null);
			}

			this.updateCurrNode(this.currentNode.getParent());
			this.currentNode.setParent(null);
			return true;
		}

		if (this.currentNode.getLeft() != null) {
			T dataToDelete = this.currentNode.getData();
			T dataToKeep = this.currentNode.getLeft().getData();
			this.currentNode.setData(dataToKeep);
			this.currentNode.getLeft().setData(dataToDelete);

			return this.remove_r(this.currentNode.getLeft());
		} else if (this.currentNode.getRight() != null) {
			T dataToDelete = this.currentNode.getData();
			T dataToKeep = this.currentNode.getRight().getData();
			this.currentNode.setData(dataToKeep);
			this.currentNode.getRight().setData(dataToDelete);

			return this.remove_r(this.currentNode.getRight());
		}

		return false;
	}

	public void traverse(Order order, Consumer<BTNode<T>> consumer) {
		this.traverse_r(this.root, order, consumer);
	}

	private void traverse_r(BTNode<T> start, Order order, Consumer<BTNode<T>> consumer) {
		if (start == null) {
			return;
		}

		switch (order) {
			case LEVEL_ORDER: case BREADTH_FIRST:
				this.consumeLevelOrder(consumer);
				break;
			case DF_PRE_ORDER:
				consumer.accept(start);
				this.traverse_r(start.getLeft(), order, consumer);
				this.traverse_r(start.getRight(), order, consumer);
				break;
			case DF_POST_ORDER:
				this.traverse_r(start.getLeft(), order, consumer);
				this.traverse_r(start.getRight(), order, consumer);
				consumer.accept(start);
				break;
			case DF_IN_ORDER:
				this.traverse_r(start.getLeft(), order, consumer);
				consumer.accept(start);
				this.traverse_r(start.getRight(), order, consumer);
				break;
		}
	}

	/**
	 * Yoinked from G4G
	 * <p>
	 * Computes the length of the largest path
	 * from the root node to the farthest
	 * leaf node.
	 *
	 * @param start where to begin computing the height
	 * @return the height
	 */
	public int getHeight(BTNode<T> start) {
		if (start == null) {
			return 0;
		} else {
			int leftHeight = getHeight(start.getLeft());
			int rightHeight = getHeight(start.getRight());

			if (leftHeight > rightHeight) {
				return leftHeight + 1;
			} else {
				return rightHeight + 1;
			}
		}
	}

	/**
	 * Breadth first traversal.
	 *
	 * @param consumer what you want it to with each node
	 *                 during traversal.
	 */
	private void consumeLevelOrder(Consumer<BTNode<T>> consumer) {
		int height = this.getHeight(this.root);

		for (int i = 1; i <= height; i++) {
			consumeAtLevel(this.root, i, consumer);
		}
	}

	/**
	 * Also yoinked from G4G
	 *
	 * @param start
	 * @param level
	 * @param consumer
	 */
	private void consumeAtLevel(BTNode<T> start, int level, Consumer<BTNode<T>> consumer) {
		if (start == null) {
			return;
		}

		if (level == 1) {
			consumer.accept(start);
		} else if (level > 1) {
			this.consumeAtLevel(start.getLeft(), level - 1, consumer);
			this.consumeAtLevel(start.getRight(), level - 1, consumer);
		}
	}

	public T getMin() {
		this.toRoot();
		while (this.next(LEFT)) ;
		return this.currentNode.getData();
	}

	public T getMax() {
		this.toRoot();
		while (this.next(RIGHT)) ;
		return this.currentNode.getData();
	}

	public BTNode<T> getRoot() {
		return root;
	}

	public void setRoot(BTNode<T> newRoot) {
		this.root = newRoot;
	}

	/**
	 * @param newCurr the new current node
	 */
	void updateCurrNode(BTNode<T> newCurr) {
		this.currentNode = newCurr;
	}

	public enum Direction {
		LEFT,
		RIGHT
	}

	public enum Order {
		LEVEL_ORDER,
		BREADTH_FIRST,
		DF_IN_ORDER,
		DF_PRE_ORDER,
		DF_POST_ORDER
	}
}

