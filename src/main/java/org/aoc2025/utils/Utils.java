package org.aoc2025.utils;

import java.util.HashSet;
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
}
