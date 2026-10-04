package com.shieldbank.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Собственный ориентированный взвешенный граф переводов.
 */
public class Graph {

    /**
     * Ребро графа.
     */
    public static final class Edge {

        private final long to;
        private final int weight;

        /**
         * @param to конечная вершина
         * @param weight вес ребра
         */
        public Edge(long to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        public long getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }
    }

    private final Map<Long, List<Edge>> adjacency = new HashMap<>();

    /**
     * Добавляет вершину, если её ещё нет.
     *
     * @param id идентификатор вершины
     */
    public void addVertex(long id) {
        adjacency.putIfAbsent(id, new ArrayList<>());
    }

    /**
     * Добавляет направленное ребро.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     * @param weight вес ребра
     */
    public void addEdge(long from, long to, int weight) {
        addVertex(from);
        addVertex(to);
        adjacency.get(from).add(new Edge(to, weight));
    }

    /**
     * Возвращает исходящие рёбра вершины.
     *
     * @param vertex вершина
     * @return список рёбер, возможно пустой
     */
    public List<Edge> neighbors(long vertex) {
        return adjacency.getOrDefault(vertex, Collections.emptyList());
    }

    public Set<Long> vertices() {
        return adjacency.keySet();
    }
}
