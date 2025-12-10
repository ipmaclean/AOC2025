package org.aoc2025.day10;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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
            solution += getMinimumButtonPressesForLights(machineManualLine);
        }
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static long getMinimumButtonPressesForLights(MachineManualLine machineManualLine) {
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
        List<MachineManualLine> machineManual = getInput();
        for (MachineManualLine machineManualLine : machineManual) {
            solution += getMinimumButtonPressesForJoltages(machineManualLine);
        }
        System.out.printf("The solution to part two is %s.%n", solution);
    }

    private static long getMinimumButtonPressesForJoltages(MachineManualLine machineManualLine) {
        Queue<Day10PuzzleState> statesToCheck = new ArrayDeque<>();
        HashSet<Day10PuzzleState> visitedStates = new HashSet<>();

        statesToCheck.add(new Day10PuzzleState(
                new ArrayList<>(Collections.nCopies(machineManualLine.getWiringSchematics().size(), 0)),
                new ArrayList<>(Collections.nCopies(machineManualLine.getJoltageRequirements().size(), 0))
        ));

        while (!statesToCheck.isEmpty()) {
            Day10PuzzleState currentState = statesToCheck.remove();

            for (int i = 0; i < machineManualLine.getWiringSchematics().size(); i++) {
                List<Integer> nextButtonPress = new ArrayList<>(currentState.buttonPresses());
                List<Integer> nextJoltages = new ArrayList<>(currentState.joltages());

                nextButtonPress.set(i, nextButtonPress.get(i) + 1);
                nextJoltages = pressButton(machineManualLine.getWiringSchematics().get(i), nextJoltages);
                if (machineManualLine.anyGreaterThanJoltageRequirements(nextJoltages)) {
                    continue;
                }
                if (machineManualLine.matchesJoltageRequirements(nextJoltages)) {
                    return nextButtonPress.stream().mapToInt(Integer::valueOf).sum();
                }
                Day10PuzzleState nextPuzzleState = new Day10PuzzleState(nextButtonPress, nextJoltages);
                if (visitedStates.contains(nextPuzzleState)) {
                    continue;
                }
                statesToCheck.add(nextPuzzleState);
                visitedStates.add(nextPuzzleState);
            }
        }
        throw new IllegalStateException("Did not find combinations of buttons to configure the joltages correctly.");
    }

    private static List<Integer> pressButton(int button, List<Integer> joltages) {
        List<Integer> localJoltages = new ArrayList<>(joltages);
        String buttonAsBinaryString = StringUtils.leftPad(Integer.toBinaryString(button), joltages.size(), '0');
        for (int i = 0; i < buttonAsBinaryString.length(); i++) {
            if (buttonAsBinaryString.charAt(i) == '1') {
                localJoltages.set(i, localJoltages.get(i) + 1);
            }
        }
        return localJoltages;
    }
}
