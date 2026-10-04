package com.shieldbank;

import com.shieldbank.algorithms.ChangeMaker;
import com.shieldbank.algorithms.ContributionCalculator;
import com.shieldbank.algorithms.GraphAlgorithms;
import com.shieldbank.algorithms.Searcher;
import com.shieldbank.algorithms.Sorter;
import com.shieldbank.algorithms.WindowAnalytics;
import com.shieldbank.model.Account;
import com.shieldbank.model.Operation;
import com.shieldbank.service.DataGenerator;
import com.shieldbank.structures.ActionStack;
import com.shieldbank.structures.BinarySearchTree;
import com.shieldbank.structures.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Точка входа приложения ShieldBank.
 */
public final class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    private Main() {
    }

    /**
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Выбор: ");
            try {
                switch (choice) {
                    case 1:
                        demoActionStack();
                        break;
                    case 2:
                        demoRegistry();
                        break;
                    case 3:
                        demoSorting();
                        break;
                    case 4:
                        demoGraph();
                        break;
                    case 5:
                        demoChangeMaker();
                        break;
                    case 6:
                        demoWindowAnalytics();
                        break;
                    case 7:
                        demoContribution();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Выход.");
                        break;
                    default:
                        System.out.println("Нет такого пункта.");
                        break;
                }
            } catch (RuntimeException exception) {
                System.out.println("Ошибка: " + exception.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("ShieldBank: итоговый проект");
        System.out.println("1. BR-1: журнал операций и откат");
        System.out.println("2. BR-2: реестр счетов");
        System.out.println("3. BR-3: сортировки и бинарный поиск");
        System.out.println("4. BR-4: граф переводов");
        System.out.println("5. BR-5: банкомат");
        System.out.println("6. BR-6: окно и пара");
        System.out.println("7. BR-7: накопительная программа");
        System.out.println("0. Выход");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException exception) {
                System.out.println("Ошибка: нужно целое число.");
            }
        }
    }

    private static void demoActionStack() {
        ActionStack stack = new ActionStack();
        System.out.println("Размер пустого журнала: " + stack.size());
        stack.push(new Operation(Operation.Type.TRANSFER, 1, 2, 100, 1));
        stack.push(new Operation(Operation.Type.BLOCK, 3, -1, 0, 2));
        stack.push(new Operation(Operation.Type.UNBLOCK, 3, -1, 0, 3));
        System.out.println("Размер после трёх операций: " + stack.size());
        System.out.println("Откат: " + stack.pop());
        System.out.println("Откат: " + stack.pop());
        System.out.println("Откат: " + stack.pop());
        try {
            stack.pop();
        } catch (IllegalStateException exception) {
            System.out.println("Ожидаемая ошибка: " + exception.getMessage());
        }
    }

    private static void demoRegistry() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(new Account(500, 100_000));
        tree.insert(new Account(100, 50_000));
        tree.insert(new Account(900, 200_000));
        tree.insert(new Account(300, 70_000));
        tree.insert(new Account(700, 80_000));
        System.out.println("Размер реестра: " + tree.size());
        System.out.println("Поиск 700: " + tree.find(700));
        System.out.println("Поиск 999: " + tree.find(999));
        System.out.println("Счета по возрастанию номера:");
        List<Account> accounts = tree.inOrder();
        for (Account account : accounts) {
            System.out.println("  " + account);
        }
    }

    private static void demoSorting() {
        int[] sizes = {1_000, 5_000, 10_000, 20_000};
        Comparator<Operation> byTimestamp =
                Comparator.comparingLong(Operation::getTimestamp);
        System.out.printf("%-10s %-18s %-18s%n",
                "Размер", "Bubble (ms)", "Merge (ms)");
        for (int size : sizes) {
            List<Operation> source = DataGenerator.generateOperations(size);

            List<Operation> bubbleData = new ArrayList<>(source);
            long bubbleStart = System.nanoTime();
            Sorter.bubbleSort(bubbleData, byTimestamp);
            long bubbleEnd = System.nanoTime();

            List<Operation> mergeData = new ArrayList<>(source);
            long mergeStart = System.nanoTime();
            Sorter.mergeSort(mergeData, byTimestamp);
            long mergeEnd = System.nanoTime();

            double bubbleMs = (bubbleEnd - bubbleStart) / 1_000_000.0;
            double mergeMs = (mergeEnd - mergeStart) / 1_000_000.0;
            System.out.printf("%-10d %-18.2f %-18.2f%n",
                    size, bubbleMs, mergeMs);
        }

        List<Operation> sorted = DataGenerator.generateOperations(50);
        Sorter.mergeSort(sorted, byTimestamp);
        long key = sorted.get(25).getTimestamp();
        int index = Searcher.lowerBound(sorted, key);
        System.out.println("lowerBound(" + key + ") = " + index);
    }

    private static void demoGraph() {
        Graph graph = new Graph();
        graph.addEdge(1, 2, 10);
        graph.addEdge(2, 3, 20);
        graph.addEdge(3, 1, 5);
        graph.addEdge(3, 4, 30);
        graph.addVertex(99);

        System.out.println("BFS от 1: " + GraphAlgorithms.bfs(graph, 1));
        System.out.println("Есть цикл: " + GraphAlgorithms.hasCycle(graph));
        System.out.println("Компонент: " + GraphAlgorithms.countComponents(graph));
        Map<Long, Integer> distance = GraphAlgorithms.dijkstra(graph, 1);
        System.out.println("Кратчайшие комиссии от 1:");
        for (Map.Entry<Long, Integer> entry : distance.entrySet()) {
            System.out.println("  до " + entry.getKey()
                    + " = " + entry.getValue());
        }
    }

    private static void demoChangeMaker() {
        int[] standard = {1, 2, 5, 10, 50, 100, 200, 500, 1000, 2000, 5000};
        int amount = 13_560;
        System.out.println("Стандартные номиналы, сумма " + amount);
        System.out.println("Жадный: " + ChangeMaker.greedy(standard, amount));
        System.out.println("ДП:     " + ChangeMaker.dp(standard, amount));

        int[] tricky = {1, 3, 4};
        int trickyAmount = 6;
        System.out.println();
        System.out.println("Номиналы 1, 3, 4, сумма " + trickyAmount);
        System.out.println("Жадный: " + ChangeMaker.greedy(tricky, trickyAmount));
        System.out.println("ДП:     " + ChangeMaker.dp(tricky, trickyAmount));
    }

    private static void demoWindowAnalytics() {
        long[] values = DataGenerator.generateValues(1000);
        int window = 10;
        long best = WindowAnalytics.maxWindowSum(values, window);
        System.out.println("Максимум по окну " + window + ": " + best);

        long[] sorted = values.clone();
        Arrays.sort(sorted);
        long target = sorted[10] + sorted[900];
        long[] pair = WindowAnalytics.findPair(sorted, target);
        if (pair == null) {
            System.out.println("Пара не найдена");
        } else {
            System.out.println("Пара с суммой " + target + ": "
                    + pair[0] + " и " + pair[1]);
        }
    }

    private static void demoContribution() {
        int smallMonth = 30;
        long memoStart = System.nanoTime();
        long memoValue = ContributionCalculator.memo(smallMonth);
        long memoEnd = System.nanoTime();
        System.out.printf("memo(%d) = %d, время %.3f мс%n",
                smallMonth, memoValue, (memoEnd - memoStart) / 1_000_000.0);

        long naiveStart = System.nanoTime();
        long naiveValue = ContributionCalculator.naive(smallMonth);
        long naiveEnd = System.nanoTime();
        System.out.printf("naive(%d) = %d, время %.3f мс%n",
                smallMonth, naiveValue, (naiveEnd - naiveStart) / 1_000_000.0);

        long total = ContributionCalculator.total(80);
        System.out.println("Накопленная сумма за 80 месяцев: " + total);
    }
}
