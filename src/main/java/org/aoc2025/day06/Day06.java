package org.aoc2025.day06;

import org.aoc2025.utils.Tuple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day06 {

    private static final String INPUT_FILE_NAME = "day06/input.txt";

    private Day06() {
        throw new IllegalStateException("Utility class");
    }

    public static void solve() throws IOException {
        solvePartOne();
        solvePartTwo();
    }

    private static Tuple<List<long[]>, char[]> getInput() throws IOException {
        InputStream inputStream = Day06.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
        List<String> input = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                input.add(line);
            }
        }
        List<long[]> problems = new ArrayList<>();
        for (int i = 0; i < input.size() - 1; i++) {
            problems.add(
                    Arrays.stream(input.get(i)
                                    .split("\\s+"))
                            .filter(x -> !x.isEmpty())
                            .mapToLong(Long::parseLong)
                            .toArray()
            );
        }
        char[] operators = input.getLast().replaceAll("\\s+", "").toCharArray();
        return new Tuple<>(problems, operators);
    }

    private static void solvePartOne() throws IOException {
        long solution = 0;
        Tuple<List<long[]>, char[]> input = getInput();
        List<long[]> problems = input.x();
        char[] operators = input.y();
        for (int i = 0; i < problems.getFirst().length; i++) {
            long problemSolution = problems.getFirst()[i];
            for (int j = 1; j < problems.size(); j++) {
                if (operators[i] == '*') {
                    problemSolution *= problems.get(j)[i];
                } else {
                    problemSolution += problems.get(j)[i];
                }
            }
            solution += problemSolution;
        }
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static void solvePartTwo() throws IOException {
        long solution = 0;
        System.out.printf("The solution to part two is %s.%n", solution);
    }
}
