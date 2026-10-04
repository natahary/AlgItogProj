package com.shieldbank.algorithms;

import com.shieldbank.structures.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Алгоритмы на графе переводов: BFS, DFS, компоненты, Дейкстра.
 */
public final class GraphAlgorithms {

    private static final int COLOR_WHITE = 0;
    private static final int COLOR_GRAY = 1;
    private static final int COLOR_BLACK = 2;

    private GraphAlgorithms() {
    }

    /**
     * Обход в ширину.
     *
     * @param graph граф
     * @param start начальная вершина
     * @return порядок посещения вершин
     */
    public static List<Long> bfs(Graph graph, long start) {
        List<Long> order = new ArrayList<>();
        Set<Long> visited = new HashSet<>();
        Deque<Long> queue = new ArrayDeque<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            long current = queue.poll();
            order.add(current);
            for (Graph.Edge edge : graph.neighbors(current)) {
                if (visited.add(edge.getTo())) {
                    queue.add(edge.getTo());
                }
            }
        }
        return order;
    }

    /**
     * Проверяет наличие цикла в ориентированном графе.
     *
     * @param graph граф
     * @return true, если есть цикл
     */
    public static boolean hasCycle(Graph graph) {
        Map<Long, Integer> color = new HashMap<>();
        for (long vertex : graph.vertices()) {
            color.put(vertex, COLOR_WHITE);
        }
        for (long vertex : graph.vertices()) {
            if (color.get(vertex) == COLOR_WHITE
                    && dfsCycle(graph, vertex, color)) {
                return true;
            }
        }
        return false;
    }

    private static boolean dfsCycle(Graph graph, long vertex,
                                    Map<Long, Integer> color) {
        color.put(vertex, COLOR_GRAY);
        for (Graph.Edge edge : graph.neighbors(vertex)) {
            int neighborColor = color.getOrDefault(edge.getTo(), COLOR_WHITE);
            if (neighborColor == COLOR_GRAY) {
                return true;
            }
            if (neighborColor == COLOR_WHITE
                    && dfsCycle(graph, edge.getTo(), color)) {
                return true;
            }
        }
        color.put(vertex, COLOR_BLACK);
        return false;
    }

    /**
     * Считает число компонент слабой связности.
     *
     * @param graph граф
     * @return число компонент
     */
    public static int countComponents(Graph graph) {
        Map<Long, Set<Long>> undirected = new HashMap<>();
        for (long vertex : graph.vertices()) {
            undirected.putIfAbsent(vertex, new HashSet<>());
        }
        for (long vertex : graph.vertices()) {
            for (Graph.Edge edge : graph.neighbors(vertex)) {
                undirected.get(vertex).add(edge.getTo());
                undirected.computeIfAbsent(edge.getTo(), key -> new HashSet<>())
                        .add(vertex);
            }
        }
        Set<Long> visited = new HashSet<>();
        int count = 0;
        for (long vertex : undirected.keySet()) {
            if (visited.add(vertex)) {
                count++;
                Deque<Long> stack = new ArrayDeque<>();
                stack.push(vertex);
                while (!stack.isEmpty()) {
                    long current = stack.pop();
                    for (long neighbor : undirected.get(current)) {
                        if (visited.add(neighbor)) {
                            stack.push(neighbor);
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * Дейкстра: минимальная сумма комиссий от start до всех вершин.
     *
     * @param graph граф
     * @param start начальная вершина
     * @return карта вершина и минимальная комиссия
     */
    public static Map<Long, Integer> dijkstra(Graph graph, long start) {
        Map<Long, Integer> distance = new HashMap<>();
        for (long vertex : graph.vertices()) {
            distance.put(vertex, Integer.MAX_VALUE);
        }
        distance.put(start, 0);
        PriorityQueue<long[]> queue = new PriorityQueue<>(
                Comparator.comparingInt(entry -> (int) entry[1]));
        queue.add(new long[]{start, 0});
        while (!queue.isEmpty()) {
            long[] current = queue.poll();
            long vertex = current[0];
            int currentDistance = (int) current[1];
            if (currentDistance > distance.get(vertex)) {
                continue;
            }
            for (Graph.Edge edge : graph.neighbors(vertex)) {
                int candidate = currentDistance + edge.getWeight();
                if (candidate < distance.get(edge.getTo())) {
                    distance.put(edge.getTo(), candidate);
                    queue.add(new long[]{edge.getTo(), candidate});
                }
            }
        }
        return distance;
    }
}
