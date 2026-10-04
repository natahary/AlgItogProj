package com.shieldbank.structures;

import com.shieldbank.model.Account;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {

    public static final class Node {

        private final Account data;
        private Node left;
        private Node right;

        private Node(Account data) {
            this.data = data;
        }
    }

    private Node root;
    private int size;

    public void insert(Account account) {
        root = insertRec(root, account);
    }

    private Node insertRec(Node node, Account account) {
        
        if (node == null) {
            size++;
            return new Node(account);
        }
        
        int cmp = Long.compare(account.getNumber(), node.data.getNumber());
        
        if (cmp < 0) {
            node.left = insertRec(node.left, account);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, account);
        }
        return node;
    }

    public Account find(long number) {
        Node current = root;
       
        while (current != null) {
            int cmp = Long.compare(number, current.data.getNumber());
           
            if (cmp == 0) {
                return current.data;
            }
            current = (cmp < 0) ? current.left : current.right;
        }
        return null;
    }

    public List<Account> inOrder() {
        List<Account> result = new ArrayList<>();
        inOrderRec(root, result);
        return result;
    }

    private void inOrderRec(Node node, List<Account> result) {
       
        if (node == null) {
            return;
        }
        inOrderRec(node.left, result);
        result.add(node.data);
        inOrderRec(node.right, result);
    }

    public int size() {
        return size;
    }
}
