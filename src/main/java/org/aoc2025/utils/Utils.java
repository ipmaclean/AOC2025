package org.aoc2025.utils;

public class Utils {
    private Utils() {}

    public static long modulo(long x, long y) {
        long result = x % y;
        if (result < 0) {
            result += y;
        }
        return result;
    }
}
