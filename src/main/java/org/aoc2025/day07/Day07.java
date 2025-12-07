package org.aoc2025.day07;

import org.aoc2025.utils.PointLong;
import org.aoc2025.utils.tuple.Tuple2;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day07 {

    private static final String INPUT_FILE_NAME = "day07/input.txt";

    private Day07() {
        throw new IllegalStateException("Utility class");
    }

    public static void solve() throws IOException {
        solvePartOne();
        solvePartTwo();
    }

    private static Tuple2<PointLong, Set<PointLong>> getInput() throws IOException {
        InputStream inputStream = Day07.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
        PointLong startPoint = new PointLong(-1, -1);
        Set<PointLong> splitters = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            long y = 0;
            while ((line = reader.readLine()) != null) {
                for (long x = 0; x < line.length(); x++) {
                    if (line.charAt((int) x) == 'S') {
                        startPoint = new PointLong(x, y);
                    } else if (line.charAt((int) x) == '^') {
                        splitters.add(new PointLong(x, y));
                    }
                }
                y++;
            }
        }
        return new Tuple2<>(startPoint, splitters);
    }

    private static void solvePartOne() throws IOException {
        Tuple2<PointLong, Set<PointLong>> input = getInput();
        PointLong startPoint = input.x();
        HashMap<PointLong, Boolean> splitters = new HashMap<>();
        for (PointLong splitter : input.y()) {
            splitters.put(splitter, false);
        }
        pewPewShootLaser(startPoint, splitters);
        long solution = splitters.values().stream().filter(x -> x).count();
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static void pewPewShootLaser(PointLong startPoint, HashMap<PointLong, @NotNull Boolean> splitters) {
        Optional<PointLong> unusedSplitterBelow = splitters.keySet().stream()
                .filter(s -> s.x() == startPoint.x() && s.y() > startPoint.y())
                .min(Comparator.comparingLong(PointLong::y));
        if (unusedSplitterBelow.isPresent() && !splitters.get(unusedSplitterBelow.get())) {
            splitters.put(unusedSplitterBelow.get(), true);
            pewPewShootLaser(new PointLong(unusedSplitterBelow.get().x() - 1, unusedSplitterBelow.get().y()), splitters);
            pewPewShootLaser(new PointLong(unusedSplitterBelow.get().x() + 1, unusedSplitterBelow.get().y()), splitters);
        }
    }

    private static void solvePartTwo() throws IOException {
        Tuple2<PointLong, Set<PointLong>> input = getInput();
        PointLong startPoint = input.x();
        Set<PointLong> splitters = input.y();
        HashMap<PointLong, Long> splittersToTimelinesMap = new HashMap<>();
        System.out.printf("The solution to part two is %s.%n", pewPewShootLaserPartTwo(startPoint, splitters, splittersToTimelinesMap));
    }

    private static long pewPewShootLaserPartTwo(PointLong startPoint, Set<PointLong> splitters, HashMap<PointLong, Long> splittersToTimelinesMap) {
        Optional<PointLong> unusedSplitterBelow = splitters.stream()
                .filter(s -> s.x() == startPoint.x() && s.y() > startPoint.y())
                .min(Comparator.comparingLong(PointLong::y));
        if (unusedSplitterBelow.isPresent()) {
            if (splittersToTimelinesMap.containsKey(unusedSplitterBelow.get())) {
                return splittersToTimelinesMap.get(unusedSplitterBelow.get());
            }
            long timelines =  pewPewShootLaserPartTwo(new PointLong(unusedSplitterBelow.get().x() - 1, unusedSplitterBelow.get().y()), splitters, splittersToTimelinesMap) +
                    pewPewShootLaserPartTwo(new PointLong(unusedSplitterBelow.get().x() + 1, unusedSplitterBelow.get().y()), splitters, splittersToTimelinesMap);
            splittersToTimelinesMap.put(unusedSplitterBelow.get(), timelines);
            return timelines;
        }
        return 1;
    }
}
