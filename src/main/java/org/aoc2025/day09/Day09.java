package org.aoc2025.day09;

import org.aoc2025.utils.PointLong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day09 {

    private static final String INPUT_FILE_NAME = "day09/input.txt";

    private Day09() {
        throw new IllegalStateException("Utility class");
    }

    public static void solve() throws IOException {
        solvePartOne();
        solvePartTwo();
    }

    private static List<PointLong> getInput() throws IOException {
        InputStream inputStream = Day09.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
        List<PointLong> tiles = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                tiles.add(new PointLong(Long.parseLong(split[0]), Long.parseLong(split[1])));
            }
        }
        return tiles;
    }

    private static void solvePartOne() throws IOException {
        long solution = 0;
        List<PointLong> input = getInput();
        for (int i = 0; i < input.size() - 1; i++) {
            for (int j = i + 1; j < input.size(); j++) {
                solution = Math.max(solution,
                        (Math.abs((input.get(i).x() - input.get(j).x())) + 1) * (Math.abs((input.get(i).y() - input.get(j).y())) + 1)
                );
            }
        }
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static void solvePartTwo() throws IOException {
        long solution = 0;
        System.out.printf("The solution to part two is %s.%n", solution);
    }
}
