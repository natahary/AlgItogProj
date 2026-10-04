package com.shieldbank.service;

import com.shieldbank.model.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Генератор тестовых данных с фиксированным seed для замеров.
 */
public final class DataGenerator {

    private static final long DEFAULT_SEED = 42L;
    private static final int ACCOUNT_RANGE = 1000;
    private static final int AMOUNT_RANGE = 1_000_000;

    private DataGenerator() {
    }

    /**
     * Генерирует список операций.
     *
     * @param count число операций
     * @return список операций
     */
    public static List<Operation> generateOperations(int count) {
        Random random = new Random(DEFAULT_SEED);
        List<Operation> operations = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            operations.add(new Operation(
                    Operation.Type.TRANSFER,
                    random.nextInt(ACCOUNT_RANGE),
                    random.nextInt(ACCOUNT_RANGE),
                    random.nextInt(AMOUNT_RANGE),
                    random.nextLong()
            ));
        }
        return operations;
    }

    /**
     * Генерирует массив случайных значений.
     *
     * @param count число значений
     * @return массив значений
     */
    public static long[] generateValues(int count) {
        Random random = new Random(DEFAULT_SEED);
        long[] values = new long[count];
        for (int i = 0; i < count; i++) {
            values[i] = random.nextInt(AMOUNT_RANGE);
        }
        return values;
    }
}
