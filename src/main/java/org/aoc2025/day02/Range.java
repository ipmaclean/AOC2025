package org.aoc2025.day02;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.aoc2025.utils.Utils.getFactors;

public class Range {
    private final long lower;
    private final long upper;

    public Range(String range) {
        String[] split = range.split("-");
        lower = Long.parseLong(split[0]);
        upper = Long.parseLong(split[1]);
    }

    public long sumInvalidIds() {
        // I tried to be too clever by half here.
        // Way too much pre-optimisation that did
        // not help with part two at all! Always
        // try just brute forcing it first.
        long minRepeaters = minRepeatingDigits();
        long maxRepeaters = maxRepeatingDigits();

        if (minRepeaters > maxRepeaters) {
            return 0;
        }
        long sum = 0;
        for (long i = minRepeaters; i <= maxRepeaters; i++) {
            sum += i * (Long.parseLong(StringUtils.rightPad("1", Long.toString(i).length() + 1, "0")) + 1);
        }
        return sum;
    }

    public long sumInvalidIdsPartTwo() {
        long solution = 0;
        for (long i = lower; i <= upper; i++) {
            long digitCount = Long.toString(i).length();
            Set<Long> factors = getFactors(digitCount);
            factors.remove(1L);

            for (long factor : factors) {
                String regex = String.format("(\\d{%d})\\1{%d}", digitCount / factor, factor - 1);
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(Long.toString(i));
                if (matcher.matches()) {
                    solution += i;
                    break;
                }
            }
        }
        return solution;
    }

    private long minRepeatingDigits() {
        String lowerAsString = Long.toString(lower);
        long lowerDigitCount = lowerAsString.length();
        if (lowerDigitCount % 2 != 0) {
            return Long.parseLong(StringUtils.rightPad("1", ((int)lowerDigitCount + 1) / 2, "0"));
        }
        long firstHalf = Long.parseLong(lowerAsString.substring(0, (int)lowerDigitCount / 2));
        long secondHalf = Long.parseLong(lowerAsString.substring((int)lowerDigitCount / 2));
        if (firstHalf < secondHalf) {
            return firstHalf + 1;
        }
        else {
            return firstHalf;
        }
    }

    private long maxRepeatingDigits() {
        String upperAsString = Long.toString(upper);
        long upperDigitCount = upperAsString.length();

        if (upperDigitCount % 2 != 0) {
            return Long.parseLong(StringUtils.rightPad("", ((int)upperDigitCount - 1) / 2, "9"));
        }
        long firstHalf = Long.parseLong(upperAsString.substring(0, (int)upperDigitCount / 2));
        long secondHalf = Long.parseLong(upperAsString.substring((int)upperDigitCount / 2));
        if (firstHalf <= secondHalf) {
            return firstHalf;
        }
        else {
            return firstHalf - 1;
        }
    }
}
