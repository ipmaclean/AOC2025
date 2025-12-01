package org.aoc2025.day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.aoc2025.utils.Utils.modulo;

public class Day01 {

    private static final String INPUT_FILE_NAME = "day01/input.txt";

    private Day01() {
        throw new IllegalStateException("Utility class");
    }

    public static void solve() throws IOException {
        solvePartOne();
        solvePartTwo();
    }

    private static List<String> getInstructions() throws IOException {
        InputStream inputStream = Day01.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
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
        long lockValue = 50;
        List<String> instructions = getInstructions();
        for (String instruction : instructions) {
            long rotation = Long.parseLong(instruction.substring(1));

            if (instruction.startsWith("R")) {
                lockValue = modulo(lockValue + rotation, 100);
            }
            else {
                lockValue = modulo(lockValue - rotation, 100);
            }
            if (lockValue  == 0) {
                solution++;
            }
        }
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static void solvePartTwo() throws IOException {
        long solution = 0;
        long lockValue = 50;
        List<String> instructions = getInstructions();
        for (String instruction : instructions) {
            long rotation = Long.parseLong(instruction.substring(1));

            if (rotation > 100) {
                solution += rotation / 100;
                rotation = rotation % 100;
            }

            if (instruction.startsWith("R")) {
                if (rotation >= (100 - lockValue) && lockValue != 0) {
                    solution++;
                }
                lockValue = modulo(lockValue + rotation, 100);
            }
            else {
                if (rotation >= lockValue && lockValue != 0) {
                    solution++;
                }
                lockValue = modulo(lockValue - rotation, 100);
            }
        }
        System.out.printf("The solution to part two is %s.%n", solution);
    }
}
