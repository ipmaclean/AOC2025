package org.aoc2025.day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day10 {

    private static final String INPUT_FILE_NAME = "day10/example.txt";

    private Day10() {
        throw new IllegalStateException("Utility class");
    }

    public static void solve() throws IOException {
        solvePartOne();
        solvePartTwo();
    }

    private static List<String> getInput() throws IOException {
        InputStream inputStream = Day10.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
        List<String> instructions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                instructions.add(line);
            }
        }
        return instructions;
    }

    private static void solvePartOne() throws IOException {
        long solution = 0;
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static void solvePartTwo() throws IOException {
        long solution = 0;
        System.out.printf("The solution to part two is %s.%n", solution);
    }
}
