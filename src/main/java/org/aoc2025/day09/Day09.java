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
        List<PointLong> input = getInput();
        for (int i = 0; i < input.size() - 1; i++) {
            for (int j = i + 1; j < input.size(); j++) {
                PointLong corner1 = input.get(i);
                PointLong corner2 = input.get(j);

                PointLong tile = input.getLast();
                boolean intersectsBoundary = false;
                // Start at index 0 then wrap around back to 0
                for (int k = 0; k <= input.size(); k++) {
                    PointLong nextTile = input.get(k % input.size());
                    if (rectangleIntersectsWithEdge(corner1, corner2, tile, nextTile)) {
                        intersectsBoundary = true;
                        break;
                    }
                    tile = nextTile;
                }
                if (!intersectsBoundary) {
                    solution = Math.max(solution,
                            (Math.abs((input.get(i).x() - input.get(j).x())) + 1) * (Math.abs((input.get(i).y() - input.get(j).y())) + 1)
                    );
                }
            }
        }
        System.out.printf("The solution to part two is %s.%n", solution);
    }

    private static boolean rectangleIntersectsWithEdge(PointLong corner1, PointLong corner2, PointLong tile, PointLong nextTile) {
        long maxEdgeX = Math.max(tile.x(), nextTile.x());
        long maxEdgeY = Math.max(tile.y(), nextTile.y());
        long minEdgeX = Math.min(tile.x(), nextTile.x());
        long minEdgeY = Math.min(tile.y(), nextTile.y());

        long maxCornerX = Math.max(corner1.x(), corner2.x());
        long maxCornerY = Math.max(corner1.y(), corner2.y());
        long minCornerX = Math.min(corner1.x(), corner2.x());
        long minCornerY = Math.min(corner1.y(), corner2.y());
        // If the edge line segment lies outside or on the perimeter of the rectangle
        if ((tile.x() == nextTile.x() && (tile.x() <= minCornerX || tile.x() >= maxCornerX || minEdgeY >= maxCornerY || maxEdgeY <= minCornerY)) ||
                (tile.y() == nextTile.y() && (tile.y() <= minCornerY || tile.y() >= maxCornerY || minEdgeX >= maxCornerX || maxEdgeX <= minCornerX))) {
            return false;
        }
        return true;
    }
}
