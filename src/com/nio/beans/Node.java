package com.nio.beans;

/**
 * Created by ronak on 2/2/2017.
 */
public class Node {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        if (getValue() != node.getValue()) return false;
        return getNext() != null ? getNext().equals(node.getNext()) : node.getNext() == null;

    }

    @Override
    public int hashCode() {
        int result = getValue();
        result = 31 * result + (getNext() != null ? getNext().hashCode() : 0);
        return result;
    }

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }

    private int value;
    private Node next;



    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
