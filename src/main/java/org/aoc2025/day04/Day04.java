package org.aoc2025.day04;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day04 {

    private static final String INPUT_FILE_NAME = "day04/input.txt";

    private static final List<Point> directions = List.of(
            new Point(-1, -1),
            new Point(0, -1),
            new Point(1, -1),
            new Point(-1, 0),
            new Point(1, 0),
            new Point(-1, 1),
            new Point(0, 1),
            new Point(1, 1)
    );

    private Day04() {
        throw new IllegalStateException("Utility class");
    }

    public static void solve() throws IOException {
        solvePartOne();
        solvePartTwo();
    }

    private static List<String> getMap() throws IOException {
        InputStream inputStream = Day04.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
        List<String> map = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                map.add(line);
            }
        }
        return map;
    }

    private static void solvePartOne() throws IOException {
        long solution = 0;
        List<String> map = getMap();
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).length(); x++) {
                if (map.get(y).charAt(x) == '@' && isAccessible(map, x, y)) {
                    solution++;
                }
            }
        }
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static void solvePartTwo() throws IOException {
        List<String> map = getMap();
        Set<Point> removedPaper = new HashSet<>();
        Set<Point> toBeRemovedPaper;

        do {
            toBeRemovedPaper = new HashSet<>();
            for (int y = 0; y < map.size(); y++) {
                for (int x = 0; x < map.get(y).length(); x++) {
                    Point coord = new Point(x,y);
                    if (map.get(y).charAt(x) == '@' && !removedPaper.contains(coord) && isAccessiblePartTwo(map, x, y, removedPaper)) {
                        toBeRemovedPaper.add(coord);
                    }
                }
            }
            removedPaper.addAll(toBeRemovedPaper);
        } while (!toBeRemovedPaper.isEmpty());
        System.out.printf("The solution to part two is %s.%n", removedPaper.size());
    }

    private static boolean isAccessible(List<String> map, int x, int y) {
        int adjacentPaperCount = 0;
        for (Point direction : directions) {
            if (x + direction.x < 0 || x + direction.x >= map.get(y).length() || y + direction.y < 0 || y + direction.y >= map.size()) {
                continue;
            }
            if (map.get(y + direction.y).charAt(x + direction.x) == '@') {
                adjacentPaperCount++;
            }
        }
        return adjacentPaperCount < 4;
    }

    private static boolean isAccessiblePartTwo(List<String> map, int x, int y, Set<Point> removedPaper) {
        int adjacentPaperCount = 0;
        for (Point direction : directions) {
            if (x + direction.x < 0 || x + direction.x >= map.get(y).length() || y + direction.y < 0 || y + direction.y >= map.size()) {
                continue;
            }
            if (map.get(y + direction.y).charAt(x + direction.x) == '@' && !removedPaper.contains(new Point(x + direction.x, y + direction.y))) {
                adjacentPaperCount++;
            }
        }
        return adjacentPaperCount < 4;
    }
}
