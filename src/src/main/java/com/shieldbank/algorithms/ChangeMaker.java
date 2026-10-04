package com.shieldbank.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Выдача суммы купюрами: жадный и точный режимы.
 */
public final class ChangeMaker {

    private ChangeMaker() {
    }

    /**
     * Жадный алгоритм.
     *
     * @param coins номиналы
     * @param amount сумма
     * @return список купюр или null, если выдать нельзя
     */
    public static List<Integer> greedy(int[] coins, int amount) {
        int[] sorted = coins.clone();
        Arrays.sort(sorted);
        List<Integer> result = new ArrayList<>();
        int rest = amount;
        for (int i = sorted.length - 1; i >= 0 && rest > 0; i--) {
            while (rest >= sorted[i]) {
                result.add(sorted[i]);
                rest -= sorted[i];
            }
        }
        if (rest != 0) {
            return null;
        }
        return result;
    }

    /**
     * Точный алгоритм через динамическое программирование.
     *
     * @param coins номиналы
     * @param amount сумма
     * @return список купюр или null, если выдать нельзя
     */
    public static List<Integer> dp(int[] coins, int amount) {
        int[] best = new int[amount + 1];
        int[] previous = new int[amount + 1];
        Arrays.fill(best, Integer.MAX_VALUE);
        best[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i >= coin && best[i - coin] != Integer.MAX_VALUE
                        && best[i - coin] + 1 < best[i]) {
                    best[i] = best[i - coin] + 1;
                    previous[i] = coin;
                }
            }
        }
        if (best[amount] == Integer.MAX_VALUE) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        int current = amount;
        while (current > 0) {
            result.add(previous[current]);
            current -= previous[current];
        }
        return result;
    }
}
