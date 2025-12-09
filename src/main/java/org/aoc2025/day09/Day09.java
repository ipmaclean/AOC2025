package org.aoc2025.day09;

import org.aoc2025.utils.PointLong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Day09 {

    private static final String INPUT_FILE_NAME = "day09/example.txt";

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
        Instant start = Instant.now();
        long solution = 0;
        List<PointLong> input = getInput();
        Set<PointLong> paintedTiles = new HashSet<>();
        PointLong tile = input.getFirst();
        // Start at index 1 then wrap around
        for (int i = 1; i <= input.size(); i++) {
            PointLong nextTile = input.get(i % input.size());
            long lowX = Math.min(tile.x(), nextTile.x());
            long highX = Math.max(tile.x(), nextTile.x());
            long lowY = Math.min(tile.y(), nextTile.y());
            long highY = Math.max(tile.y(), nextTile.y());
            for (long x = lowX; x <= highX; x++) {
                for (long y = lowY; y <= highY; y++) {
                    // add outline of the shape to the hashset
                    paintedTiles.add(new PointLong(x, y));
                }
            }
            tile = nextTile;
        }
        // Then floodfill - should automate finding start point 'inside' the shape.
        // Can count left/right turns and see which happens more, then orient from
        // start point based on that
        floodFill(paintedTiles, new PointLong(input.getFirst().x() + 1, input.getFirst().y() + 1));
        // Then redo part 1 but checking outline of rectangle is within the fill
        for (int i = 0; i < input.size() - 1; i++) {
            for (int j = i + 1; j < input.size(); j++) {
                PointLong tileI = input.get(i);
                PointLong tileJ = input.get(j);
                long lowX = Math.min(tileI.x(), tileJ.x());
                long highX = Math.max(tileI.x(), tileJ.x());
                long lowY = Math.min(tileI.y(), tileJ.y());
                long highY = Math.max(tileI.y(), tileJ.y());
                boolean rectangleOutOfBounds = false;
                for (long x = lowX; x <= highX; x++) {
                    for (long y = lowY; y <= highY; y++) {
                        // Check whole outline of rectangle are painted tiles
                        if (!paintedTiles.contains(new PointLong(x, y))) {
                            rectangleOutOfBounds = true;
                            break;
                        }
                    }
                    if (rectangleOutOfBounds) {
                        break;
                    }
                }
                if (!rectangleOutOfBounds) {
                    solution = Math.max(solution,
                            (Math.abs((tileI.x() - tileJ.x())) + 1) * (Math.abs((tileI.y() - tileJ.y())) + 1)
                    );
                }
            }
        }
        Instant finish = Instant.now();
        System.out.printf("The solution to part two is %s.%n", solution);
        long timeElapsed = Duration.between(start, finish).toMillis();
        DecimalFormat formatter = new DecimalFormat("#,###");
        System.out.printf("The solution to part two took %sms.%n", formatter.format(timeElapsed));
    }

    private static void floodFill(Set<PointLong> paintedTiles, PointLong startTile) {
        var directions = List.of(
                new PointLong(-1, 0),
                new PointLong(0, -1),
                new PointLong(1, 0),
                new PointLong(0, 1)
        );
        Queue<PointLong> tilesToAdd = new ArrayDeque<>();
        tilesToAdd.add(startTile);
        paintedTiles.add(startTile);
        while (!tilesToAdd.isEmpty()) {
            PointLong currentTile = tilesToAdd.remove();
//            if (paintedTiles.contains(currentTile)) {
//                continue;
//            }
//            paintedTiles.add(currentTile);
            for (PointLong direction : directions) {
                PointLong nextTile = new PointLong(currentTile.x() + direction.x(), currentTile.y() + direction.y());
                if (!paintedTiles.contains(nextTile) && !tilesToAdd.contains(nextTile)) {
                    tilesToAdd.add(nextTile);
                    paintedTiles.add(nextTile);
                }
            }
        }
    }
}
