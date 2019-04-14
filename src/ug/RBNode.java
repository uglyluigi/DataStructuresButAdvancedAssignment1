package ug;

public class RBNode<T extends Comparable<T>> extends BTNode<T> {

	private Color color;

	public RBNode(T data, RBNode<T> left, RBNode<T> right) {
		super(data, left, right);
		this.color = Color.RED;
	}

	public RBNode(T data) {
		this(data, null, null);
		this.color = Color.RED;
	}

	public boolean isRed() {
		return this.color == Color.RED;
	}

	public boolean isBlack() {
		return this.color == Color.BLACK;
	}

	public void setRed() {
		this.color = Color.RED;
	}

	public void setBlack() {
		this.color = Color.BLACK;
	}

	private enum Color {
		RED,
		BLACK
	}
}
