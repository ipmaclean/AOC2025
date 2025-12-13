package org.aoc2025.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {
    private Utils() {}

    public static long modulo(long x, long y) {
        long result = x % y;
        if (result < 0) {
            result += y;
        }
        return result;
    }

    public static Set<Long> getFactors(long n) {
        Set<Long> factors = new HashSet<>();
        int step = n % 2 == 0 ? 1 : 2;
        for (long i = 1; i <= Math.sqrt(n); i += step) {
            if (n % i == 0) {
                factors.add(i);
                factors.add(n / i);
            }
        }
        return factors;
    }

    public static <T> List<List<T>> findCombinationsOfSizeR(List<T> values, int r) {
        List<List<T>> result = new ArrayList<>();
        // To store current combination
        List<T> data = new ArrayList<>();
        combinationUtil(0, r, data, result, values);
        return result;
    }

    private static <T> void combinationUtil(int ind, int r, List<T> data, List<List<T>> result, List<T> values) {
        int n = values.size();
        // If size of current combination is r
        if (data.size() == r) {
            result.add(new ArrayList<>(data));
            return;
        }

        for (int i = ind; i < n; i++) {
            data.add(values.get(i));
            combinationUtil(i + 1, r, data, result, values);
            // Backtrack to find other combinations
            data.removeLast();
        }
    }
}
