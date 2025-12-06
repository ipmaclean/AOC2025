package org.aoc2025.day06;

import org.aoc2025.utils.tuple.Tuple3;

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

    private static Tuple3<List<long[]>, char[], List<String>> getInput() throws IOException {
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
        return new Tuple3<>(problems, operators, input);
    }

    private static void solvePartOne() throws IOException {
        long solution = 0;
        Tuple3<List<long[]>, char[], List<String>> input = getInput();
        List<long[]> problems = input.x();
        char[] operators = input.y();
        for (int i = 0; i < problems.getFirst().length; i++) {
            long problemSolution = problems.getFirst()[i];
            for (int j = 1; j < problems.size(); j++) {
                problemSolution = applyOperator(operators[i], problemSolution, problems.get(j)[i]);
            }
            solution += problemSolution;
        }
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static void solvePartTwo() throws IOException {
        long solution = 0;
        Tuple3<List<long[]>, char[], List<String>> inputTuple = getInput();
        List<String> input = inputTuple.z();
        char[] operators = inputTuple.y();

        int operatorCounter = 0;
        char operator = operators[operatorCounter];
        long problemSolution = operator == '*' ? 1L : 0L;
        // The input strings are not all the same length
        if (input.isEmpty()) {
            throw new IllegalStateException("Puzzle input is empty.");
        }
        int maxInputLineLength = input.stream().mapToInt(String::length).max().getAsInt();
        for (int i = 0; i <= maxInputLineLength; i++) {
            Long nextValue = getVerticalLong(input, i);
            if (nextValue == null) {
                solution += problemSolution;
                operatorCounter++;
                operator = operatorCounter < operators.length ? operators[operatorCounter] : '#'; // # means we've reached the end - it won't be applied
                problemSolution = operator == '*' ? 1L : 0L;
            } else {
                problemSolution = applyOperator(operator, problemSolution, nextValue);
            }
        }
        System.out.printf("The solution to part two is %s.%n", solution);
    }

    private static Long getVerticalLong(List<String> input, int index) {
        // Return the vertical value or null if it's
        // an empty column or the end of the input
        StringBuilder valueSb = new StringBuilder();
        for (int i = 0; i < input.size() - 1; i++) {
            if (index < input.get(i).length()) {
                valueSb.append(input.get(i).charAt(index));
            }
        }
        String valueAsString = valueSb.toString().replaceAll("\\s+", "");
        return valueAsString.isEmpty() ? null : Long.parseLong(valueAsString);
    }

    private static long applyOperator(char operator, long currentProblemSolution, long nextValue) {
        if (operator == '*') {
            return currentProblemSolution * nextValue;
        } else if (operator == '+') {
            return currentProblemSolution + nextValue;
        } else {
            throw new IllegalStateException("Operator must be * or +");
        }
    }
}
