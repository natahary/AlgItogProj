package com.shieldbank.structures;

import com.shieldbank.model.Operation;

public class ActionStack {

    private static final class Node {

        private final Operation data;
        private Node next;

        private Node(Operation data) {
            this.data = data;
        }
    }

    private Node top;
    private int size;

    public void push(Operation operation) {
        Node node = new Node(operation);
        node.next = top;
        top = node;
        size++;
    }

    public Operation pop() {
        if (top == null) {
            throw new IllegalStateException("Журнал пуст, откатывать нечего");
        }
        Operation operation = top.data;
        top = top.next;
        size--;
        return operation;
    }

    public Operation peek() {
        if (top == null) {
            throw new IllegalStateException("Журнал пуст");
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}
