package org.aoc2025.day10;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
        Loader.loadNativeLibraries();
        long solution = 0;
        List<MachineManualLine> machineManual = getInput();
        for (MachineManualLine machineManualLine : machineManual) {
            solution += getMinimumButtonPressesForJoltages(machineManualLine);
        }
        System.out.printf("The solution to part two is %s.%n", solution);
    }

    private static long getMinimumButtonPressesForJoltages(MachineManualLine machineManualLine) {
        MPSolver solver = MPSolver.createSolver("SCIP");
        if (solver == null) {
            throw new RuntimeException("SCIP solver unavailable.");
        }

        MPVariable[] xs = IntStream.range(0, machineManualLine.getWiringSchematics().size())
                .mapToObj(i -> solver.makeIntVar(0, MPSolver.infinity(), "x_" + i))
                .toArray(MPVariable[]::new);

        int[][] matrix = machineManualLine.getTransposedMatrixWiringSchematics();
        List<Integer> joltages = machineManualLine.getJoltageRequirements();
        for (int i = 0; i < joltages.size(); i++) {
            MPConstraint constraint = solver.makeConstraint(joltages.get(i), joltages.get(i), "c_" + i);
            for (int j = 0; j < machineManualLine.getWiringSchematics().size(); j++) {
                if (matrix[i][j] == 1) {
                    constraint.setCoefficient(xs[j], 1);
                }
            }
        }

        MPObjective objective = solver.objective();
        for (MPVariable x : xs) {
            objective.setCoefficient(x, 1);
        }
        objective.setMinimization();

        final MPSolver.ResultStatus resultStatus = solver.solve();
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            return (long)objective.value();
        }
        throw new IllegalStateException("No optimal solution found");
    }
}
