package org.aoc2025.day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.aoc2025.utils.Utils.findCombinationsOfSizeR;

public class Day10 {

    private static final String INPUT_FILE_NAME = "day10/input.txt";

    private Day10() {
        throw new IllegalStateException("Utility class");
    }

    public static void solve() throws IOException {
        solvePartOne();
        solvePartTwo();
    }

    private static List<MachineManualLine> getInput() throws IOException {
        InputStream inputStream = Day10.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
        List<MachineManualLine> machineManual = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                machineManual.add(new MachineManualLine(line));
            }
        }
        return machineManual;
    }

    private static void solvePartOne() throws IOException {
        long solution = 0;
        List<MachineManualLine> machineManual = getInput();
        for (MachineManualLine machineManualLine : machineManual) {
            solution += getMinimumButtonPresses(machineManualLine);
        }
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static long getMinimumButtonPresses(MachineManualLine machineManualLine) {
        for (int i = 1; i <= machineManualLine.getWiringSchematics().size(); i++) {
            List<List<Integer>> combinations = findCombinationsOfSizeR(machineManualLine.getWiringSchematics(), i);
            for (List<Integer> combination : combinations) {
                int indicatorLightValue = 0;
                for (Integer button : combination) {
                    indicatorLightValue ^= button;
                }
                if (indicatorLightValue == machineManualLine.getIndicatorLightDiagram()) {
                    return i;
                }
            }
        }
        throw new IllegalStateException("Didn't match lights with button presses.");
    }

    private static void solvePartTwo() throws IOException {
        long solution = 0;
        System.out.printf("The solution to part two is %s.%n", solution);
    }
}
