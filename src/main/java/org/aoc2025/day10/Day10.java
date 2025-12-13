package org.aoc2025.day10;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
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
        Instant start = Instant.now();
        long solution = 0;
        List<MachineManualLine> machineManual = getInput();
        int counter = 1;
        for (MachineManualLine machineManualLine : machineManual) {
            System.out.printf("Solving input line %s. ",  counter++);
            Instant startLine = Instant.now();
            long solutionForLine = getMinimumButtonPressesForJoltages(machineManualLine);
            solution += solutionForLine;
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(startLine, finish).toMillis();
            DecimalFormat formatter = new DecimalFormat("#,###");
            System.out.printf("Solved in %sms. Solution for line %s.%n",  formatter.format(timeElapsed), formatter.format(solutionForLine));
        }
        Instant finish = Instant.now();
        // 20617 too high
        System.out.printf("The solution to part two is %s.%n", solution);

        long timeElapsed = Duration.between(start, finish).toMillis();
        DecimalFormat formatter = new DecimalFormat("#,###");
        System.out.printf("The solution to part two took %sms.%n", formatter.format(timeElapsed));
    }

    private static long getMinimumButtonPressesForJoltages(MachineManualLine machineManualLine) {
        int[][] matrix = machineManualLine.getTransposedMatrixWiringSchematics();
        List<Integer> joltages = machineManualLine.getJoltageRequirements();

        Model model = new Model();
        IntVar[] xs = model.intVarArray("xs", machineManualLine.getWiringSchematics().size(), 0, 1000, false);

        for (int i = 0; i < matrix.length; i++) {
            List<IntVar> variablesToSum = new ArrayList<>();
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    variablesToSum.add(xs[j]);
                }
            }
            model.sum(variablesToSum.toArray(new IntVar[0]), "=", joltages.get(i)).post();
        }
        IntVar sum = model.intVar("sum", 0, 2000, false);
        model.sum(xs, "=", sum).post();

        Solver solver = model.getSolver();
        Solution solution = solver.findOptimalSolution(sum, Model.MINIMIZE);
        return solution.getIntVal(sum);
    }
}
