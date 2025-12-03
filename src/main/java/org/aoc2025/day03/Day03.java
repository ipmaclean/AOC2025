package org.aoc2025.day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day03 {

    private static final String INPUT_FILE_NAME = "day03/input.txt";

    private Day03() {
        throw new IllegalStateException("Utility class");
    }

    public static void solve() throws IOException {
        solvePartOne();
        solvePartTwo();
    }

    private static List<String> getBatteries() throws IOException {
        InputStream inputStream = Day03.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
        List<String> batteries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                batteries.add(line);
            }
        }
        return batteries;
    }

    private static void solvePartOne() throws IOException {
        long solution = 0;
        List<String> batteries = getBatteries();
        for (String battery : batteries) {
            solution += Long.parseLong(getMaxFromRight(2, 0, battery, ""));
        }
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static void solvePartTwo() throws IOException {
        long solution = 0;
        List<String> batteries = getBatteries();
        for (String battery : batteries) {
            solution += Long.parseLong(getMaxFromRight(12, 0, battery, ""));
        }
        System.out.printf("The solution to part two is %s.%n", solution);
    }

    private static String getMaxFromRight(int depth, int endIndex, String battery, String currentSolution) {
        int maxJoltage = Integer.MIN_VALUE;
        int maxIndex = Integer.MIN_VALUE;
        for (int i = battery.length() - depth; i >= endIndex; i--) {
            int joltage = Character.getNumericValue(battery.charAt(i));
            if (joltage >= maxJoltage) {
                maxJoltage = joltage;
                maxIndex = i;
            }
        }
        currentSolution += Integer.toString(maxJoltage);
        if (depth <= 1) {
            return currentSolution;
        }
        return getMaxFromRight(depth - 1, maxIndex + 1, battery, currentSolution);
    }
}
