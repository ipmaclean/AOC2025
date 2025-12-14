package org.aoc2025.day12;

import org.aoc2025.utils.tuple.Tuple2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {

    private static final String INPUT_FILE_NAME = "day12/input.txt";

    private Day12() {
        throw new IllegalStateException("Utility class");
    }

    public static void solve() throws IOException {
        solvePartOne();
    }

    private static Tuple2<List<Present>, List<PresentInstructions>> getInput() throws IOException {
        InputStream inputStream = Day12.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
        List<Present> presents = new ArrayList<>();
        List<PresentInstructions> presentInstructions = new ArrayList<>();
        Pattern presentPattern = Pattern.compile("^\\d:");
        Pattern presentInstructionsPattern = Pattern.compile("^\\d+x\\d");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher presentPatternMatcher = presentPattern.matcher(line);
                Matcher presentInstructionsMatcher = presentInstructionsPattern.matcher(line);


                if (presentPatternMatcher.find()) {
                    List<String> present = new ArrayList<>();
                    while (!(line = reader.readLine()).isEmpty()) {
                        present.add(line);
                    }
                    presents.add(new Present(present));
                }
                if (presentInstructionsMatcher.find()) {
                    presentInstructions.add(new PresentInstructions(line));
                }
            }
        }
        return new Tuple2<>(presents, presentInstructions);
    }

    private static void solvePartOne() throws IOException {
        long solution = 0;
        Tuple2<List<Present>, List<PresentInstructions>> input = getInput();
        List<Present> presents = input.x();
        List<PresentInstructions> presentInstructions = input.y();

        for (PresentInstructions presentInstruction : presentInstructions) {
            // If definitely does not fit - short circuit
            // else:
            // Fits trivially - all presents would fit if they were a 3x3 square OR
            // Fits with non-trivial solution
            if (!(cannotFitOnGrid(presentInstruction, presents)) &&
                    ((presentInstruction.getRegionDimensions().x() / 3) * (presentInstruction.getRegionDimensions().y() / 3) >= Arrays.stream(presentInstruction.getPresentCounts()).sum() || canBinPack(presentInstruction, presents))) {
                solution++;
            }
        }
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static boolean canBinPack(PresentInstructions presentInstruction, List<Present> presents) {
        // Really difficult!
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    private static boolean cannotFitOnGrid(PresentInstructions presentInstruction, List<Present> presents) {
        long gridArea = presentInstruction.getRegionDimensions().x() * presentInstruction.getRegionDimensions().y();
        long presentsArea = 0;
        for (int i = 0; i < presentInstruction.getPresentCounts().length; i++) {
            presentsArea += presentInstruction.getPresentCounts()[i] * presents.get(i).getArea();
        }
        return presentsArea > gridArea;
    }
}
