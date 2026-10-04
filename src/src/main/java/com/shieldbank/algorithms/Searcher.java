package com.shieldbank.algorithms;

import com.shieldbank.model.Operation;

import java.util.List;

/**
 * Собственный бинарный поиск по отсортированному списку операций.
 */
public final class Searcher {

    private Searcher() {
    }

    /**
     * Возвращает индекс первого элемента с timestamp, не меньшим key.
     *
     * @param sorted список, отсортированный по timestamp
     * @param key пороговое значение
     * @return индекс первого подходящего элемента или размер списка
     */
    public static int lowerBound(List<Operation> sorted, long key) {
        int low = 0;
        int high = sorted.size();
        while (low < high) {
            int mid = (low + high) >>> 1;
            if (sorted.get(mid).getTimestamp() < key) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
