package org.aoc2025.day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day11 {

    private static final String INPUT_FILE_NAME = "day11/input.txt";

    private Day11() {
        throw new IllegalStateException("Utility class");
    }

    public static void solve() throws IOException {
        solvePartOne();
        solvePartTwo();
    }

    private static HashMap<String, List<String>> getInput() throws IOException {
        InputStream inputStream = Day11.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
        HashMap<String, List<String>> input = new HashMap<>();
        Pattern pattern = Pattern.compile("[a-z]+");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                int counter = 0;
                String firstMatch = "";
                List<String> otherMatches = new ArrayList<>();
                while (matcher.find()) {
                    String match = matcher.group();
                    if (counter++ == 0) {
                        firstMatch = match;
                    }
                    else {
                        otherMatches.add(match);
                    }
                }
                input.put(firstMatch, otherMatches);
            }
        }
        return input;
    }

    private static void solvePartOne() throws IOException {
        HashMap<String, List<String>> input = getInput();
        System.out.printf("The solution to part one is %s.%n", countPathsToOut("you", input));
    }

    private static long countPathsToOut(String currentServer, HashMap<String, List<String>> input) {
        if (currentServer.equals("out")) {
            return 1;
        }
        long currentCount = 0;
        for (String server: input.get(currentServer)) {
            currentCount += countPathsToOut(server, input);
        }
        return currentCount;
    }

    private static void solvePartTwo() throws IOException {
        long solution = 0;
        System.out.printf("The solution to part two is %s.%n", solution);
    }
}
