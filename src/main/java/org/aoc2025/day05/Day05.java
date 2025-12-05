package org.aoc2025.day05;

import org.aoc2025.utils.PointLong;
import org.aoc2025.utils.Tuple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day05 {

    private static final String INPUT_FILE_NAME = "day05/input.txt";

    private Day05() {
        throw new IllegalStateException("Utility class");
    }

    public static void solve() throws IOException {
        solvePartOne();
        solvePartTwo();
    }

    private static Tuple<List<PointLong>, List<Long>> getInput() throws IOException {
        InputStream inputStream = Day05.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
        List<PointLong> freshRanges = new ArrayList<>();
        List<Long> ingredients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while (!(line = reader.readLine()).isEmpty()) {
                String[] split = line.split("-");
                long lower = Long.parseLong(split[0]);
                long upper = Long.parseLong(split[1]);
                freshRanges.add(new PointLong(lower, upper));
            }
            while ((line = reader.readLine()) != null) {
                ingredients.add(Long.parseLong(line));
            }
        }
        return new Tuple<>(freshRanges, ingredients);
    }

    private static void solvePartOne() throws IOException {
        long solution = 0;
        Tuple<List<PointLong>, List<Long>> input = getInput();
        List<PointLong> freshRanges = input.x();
        List<Long> ingredients = input.y();
        for (long ingredient : ingredients) {
            if (freshRanges.stream().anyMatch(fr -> ingredient >= fr.x() && ingredient <= fr.y())) {
                solution++;
            }
        }
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static void solvePartTwo() throws IOException {
        long solution = 0;
        System.out.printf("The solution to part two is %s.%n", solution);
    }
}
