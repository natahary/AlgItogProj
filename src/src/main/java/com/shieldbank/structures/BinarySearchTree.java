package com.shieldbank.structures;

import com.shieldbank.model.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Собственное бинарное дерево поиска для реестра счетов.
 */
public class BinarySearchTree {

    /**
     * Узел дерева.
     */
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

    /**
     * Добавляет счёт в дерево.
     *
     * @param account счёт
     */
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

    /**
     * Ищет счёт по номеру.
     *
     * @param number номер счёта
     * @return найденный счёт или null
     */
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

    /**
     * Возвращает счета в порядке возрастания номеров.
     *
     * @return отсортированный список счетов
     */
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
