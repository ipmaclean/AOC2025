package org.aoc2025.day11;

import org.aoc2025.utils.tuple.Tuple4;

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
                    } else {
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
        System.out.printf("The solution to part one is %s.%n", countPathsToOut("you", input, new HashMap<>()).x0());
    }

    private static void solvePartTwo() throws IOException {
        HashMap<String, List<String>> input = getInput();
        System.out.printf("The solution to part two is %s.%n", countPathsToOut("svr", input, new HashMap<>()).x3());
    }

    // Tuple4<Long total paths, Long pathsHittingDac, Long pathsHittingFFt, Long pathsHittingBoth>
    private static Tuple4<Long, Long, Long, Long> countPathsToOut(
            String currentServer,
            HashMap<String, List<String>> input,
            HashMap<String, Tuple4<Long, Long, Long, Long>> pathsToOutCache) {
        if (currentServer.equals("out")) {
            return new Tuple4<>(1L, 0L, 0L, 0L);
        }
        long currentTotal = 0;
        long currentPathsHittingDac = 0;
        long currentPathsHittingFFt = 0;
        long currentPathsHittingBoth = 0;
        for (String server : input.get(currentServer)) {
            if (pathsToOutCache.containsKey(server)) {
                Tuple4<Long, Long, Long, Long> cacheValue = pathsToOutCache.get(server);
                currentTotal += cacheValue.x0();
                currentPathsHittingDac += cacheValue.x1();
                currentPathsHittingFFt += cacheValue.x2();
                currentPathsHittingBoth += cacheValue.x3();
            } else {
                Tuple4<Long, Long, Long, Long> pathsToOut = countPathsToOut(server, input, pathsToOutCache);

                long pathsToOutHittingDac = server.equals("dac") ? pathsToOut.x0() : pathsToOut.x1();
                long pathsToOutHittingFft = server.equals("fft") ? pathsToOut.x0() : pathsToOut.x2();
                long pathsToOutHittingBoth;
                if (server.equals("dac")) {
                    pathsToOutHittingBoth = pathsToOut.x2();
                } else if (server.equals("fft")) {
                    pathsToOutHittingBoth = pathsToOut.x1();
                } else {
                    pathsToOutHittingBoth = pathsToOut.x3();
                }

                currentTotal += pathsToOut.x0();
                currentPathsHittingDac += pathsToOutHittingDac;
                currentPathsHittingFFt += pathsToOutHittingFft;
                currentPathsHittingBoth += pathsToOutHittingBoth;
                pathsToOutCache.put(server, new Tuple4<>(pathsToOut.x0(), pathsToOutHittingDac, pathsToOutHittingFft, pathsToOutHittingBoth));
            }
        }
        return new Tuple4<>(currentTotal, currentPathsHittingDac, currentPathsHittingFFt, currentPathsHittingBoth);
    }
}
