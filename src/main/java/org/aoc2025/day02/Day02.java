package org.aoc2025.day02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day02 {

    private static final String INPUT_FILE_NAME = "day02/input.txt";

    private Day02() {
        throw new IllegalStateException("Utility class");
    }

    public static void solve() throws IOException {
        solvePartOne();
        solvePartTwo();
    }

    private static List<Range> getRanges() throws IOException {
        InputStream inputStream = Day02.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
        List<Range> ranges = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String range : line.split(",")) {
                    ranges.add(new Range(range));
                }
            }
        }
        return ranges;
    }

    private static void solvePartOne() throws IOException {
        long solution = 0;
        List<Range> ranges = getRanges();
        for (Range range : ranges)  {
            solution += range.sumInvalidIds();
        }
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static void solvePartTwo() throws IOException {
        long solution = 0;
        List<Range> ranges = getRanges();
        for (Range range : ranges)  {
            solution += range.sumInvalidIdsPartTwo();
        }
        System.out.printf("The solution to part two is %s.%n", solution);
    }
}
