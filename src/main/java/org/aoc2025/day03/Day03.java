package org.aoc2025.day03;

import org.aoc2025.utils.Tuple;

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
            Tuple<Integer, Integer> maxJoltageAndIndexFirstDigit = getMaxFromRight(battery.length() - 2, 0, battery);
            Tuple<Integer, Integer> maxJoltageAndIndexSecondDigit = getMaxFromRight(battery.length() - 1, maxJoltageAndIndexFirstDigit.y() + 1, battery);
            solution +=  Integer.parseInt(maxJoltageAndIndexFirstDigit.x().toString() + maxJoltageAndIndexSecondDigit.x().toString());
        }
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static void solvePartTwo() throws IOException {
        long solution = 0;
        System.out.printf("The solution to part two is %s.%n", solution);
    }

    private static Tuple<Integer, Integer> getMaxFromRight(int startIndex, int endIndex, String battery) {
        int maxJoltage = Integer.MIN_VALUE;
        int maxIndex = Integer.MIN_VALUE;

        for (int i = startIndex; i >= endIndex; i--) {
            int joltage = Character.getNumericValue(battery.charAt(i));
            if (joltage >= maxJoltage) {
                maxJoltage = joltage;
                maxIndex = i;
            }
        }
        return new Tuple<>(maxJoltage, maxIndex);
    }
}
