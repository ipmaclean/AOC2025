package org.aoc2025.day09;

import org.aoc2025.utils.Direction;
import org.aoc2025.utils.PointLong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static org.aoc2025.utils.Utils.modulo;

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
        Instant start = Instant.now();
        long solution = 0;
        List<PointLong> input = getInput();
        Set<PointLong> rightTurnCorners = new HashSet<>();
        PointLong tile = input.getLast();
        Direction direction = null;
        // Start at index 0 then wrap around back to 0
        for (int i = 0; i <= input.size(); i++) {
            PointLong nextTile = input.get(i % input.size());
            long xDiff = nextTile.x() - tile.x();
            long yDiff = nextTile.y() - tile.y();
            Direction nextDirection = Direction.valueOfCoord(new PointLong((xDiff) / Math.abs(xDiff == 0 ? 1 : xDiff), (yDiff) / Math.abs(yDiff == 0 ? 1 : yDiff)));
            Direction turnDirection = null;
            if (direction != null) {
                turnDirection = Direction.valueOfIndex(
                        modulo(nextDirection.index - direction.index + 1, 4)
                );
                if (turnDirection == Direction.RIGHT) {
                    rightTurnCorners.add(tile);
                } else if (turnDirection != Direction.LEFT) {
                    throw new IllegalStateException("Non left/right turn");
                }
            }
            direction = nextDirection;
            tile = nextTile;
        }
        // For each pair of opposite corners/rectangle
        for (int i = 0; i < input.size() - 1; i++) {
            for (int j = i + 1; j < input.size(); j++) {
                List<PointLong> corners = List.of(
                        new PointLong(input.get(i).x(), input.get(i).y()),
                        new PointLong(input.get(i).x(), input.get(j).y()),
                        new PointLong(input.get(j).x(), input.get(j).y()),
                        new PointLong(input.get(j).x(), input.get(i).y())
                );
                boolean outsideBoundary = false;
                PointLong corner = corners.getLast();
                for (int k = 0; k < corners.size(); k++) {
                    PointLong nextCorner = corners.get(k);
                    long x = corner.x();
                    long xDiff = nextCorner.x() - corner.x();
                    long y = corner.y();
                    long yDiff = nextCorner.y() - corner.y();
                    PointLong directionInner = new PointLong(xDiff / (xDiff == 0 ? 1 : Math.abs(xDiff)), (yDiff) / (yDiff == 0 ? 1 : Math.abs(yDiff)));
                    x += directionInner.x();
                    y += directionInner.y();
                    Direction currentDirection = Direction.valueOfCoord(directionInner);
                    if (directionInner.equals(new PointLong(0, 0))) {
                        continue;
                    }
                    do {
                        do {
                            PointLong currentCoord = new PointLong(x, y);
                            if (!currentCoord.equals(nextCorner) &&
                                    rightTurnCorners.contains(currentCoord)
                            ) {
                                outsideBoundary = true;
                            }
                            y += directionInner.y();
                        } while (y != nextCorner.y() && !outsideBoundary);
                        x += directionInner.x();
                    } while (x != nextCorner.x() && !outsideBoundary);
                    if (outsideBoundary) {
                        break;
                    }
                    corner = nextCorner;
                }

                if (!outsideBoundary) {
                    solution = Math.max(solution,
                            (Math.abs((input.get(i).x() - input.get(j).x())) + 1) * (Math.abs((input.get(i).y() - input.get(j).y())) + 1)
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
}
