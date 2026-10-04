package com.shieldbank.algorithms;

import java.util.HashMap;
import java.util.Map;

public final class ContributionCalculator {

    private static final Map<Integer, Long> CACHE = new HashMap<>();

    private ContributionCalculator() {
    }

    public static long naive(int month) {
        if (month < 1) {
            throw new IllegalArgumentException("Номер месяца должен быть не меньше 1");
        }
        if (month <= 2) {
            return 1L;
        }
        return naive(month - 1) + naive(month - 2);
    }

    public static long memo(int month) {
        if (month < 1) {
            throw new IllegalArgumentException("Номер месяца должен быть не меньше 1");
        }
        if (month <= 2) {
            return 1L;
        }
        Long cached = CACHE.get(month);
        if (cached != null) {
            return cached;
        }
        long value = memo(month - 1) + memo(month - 2);
        CACHE.put(month, value);
        return value;
    }

    public static long total(int month) {
        long sum = 0L;
        for (int i = 1; i <= month; i++) {
            sum += memo(i);
        }
        return sum;
    }
}
