package com.shieldbank.algorithms;

/**
 * Аналитика потока: скользящее окно и поиск пары.
 */
public final class WindowAnalytics {

    private WindowAnalytics() {
    }

    /**
     * Максимальная сумма k подряд идущих элементов.
     *
     * @param values массив значений
     * @param k длина окна
     * @return максимальная сумма или -1 при некорректном k
     */
    public static long maxWindowSum(long[] values, int k) {
        if (k <= 0 || values.length < k) {
            return -1;
        }
        long sum = 0;
        for (int i = 0; i < k; i++) {
            sum += values[i];
        }
        long max = sum;
        for (int i = k; i < values.length; i++) {
            sum += values[i] - values[i - k];
            if (sum > max) {
                max = sum;
            }
        }
        return max;
    }

    /**
     * Ищет пару с заданной суммой в отсортированном массиве.
     *
     * @param sorted отсортированный массив
     * @param target целевая сумма
     * @return пара значений или null
     */
    public static long[] findPair(long[] sorted, long target) {
        int left = 0;
        int right = sorted.length - 1;
        while (left < right) {
            long sum = sorted[left] + sorted[right];
            if (sum == target) {
                return new long[]{sorted[left], sorted[right]};
            }
            if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return null;
    }
}
