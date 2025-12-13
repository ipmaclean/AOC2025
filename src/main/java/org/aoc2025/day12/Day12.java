package org.aoc2025.day12;

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

    private static List<PresentInstructions> getInput() throws IOException {
        InputStream inputStream = Day12.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
        List<PresentInstructions> presentInstructions = new ArrayList<>();
        Pattern pattern = Pattern.compile("^\\d+x\\d");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (!matcher.find()) {
                    continue;
                }
                presentInstructions.add(new PresentInstructions(line));
            }
        }
        return presentInstructions;
    }

    private static void solvePartOne() throws IOException {
        long solution = 0;
        List<PresentInstructions> presentInstructions = getInput();
        for (PresentInstructions presentInstruction : presentInstructions) {
            long gridArea = presentInstruction.getRegionDimensions().x() * presentInstruction.getRegionDimensions().y();
            long shapeCount = Arrays.stream(presentInstruction.getPresentCounts()).sum();
            if (shapeCount * 9 <= gridArea) {
                solution++;
            }
        }
        System.out.printf("The solution to part one is %s.%n", solution);
    }
}
