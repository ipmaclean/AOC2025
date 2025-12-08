package org.aoc2025.day08;

import org.aoc2025.utils.PointLong3D;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Day08 {

    private static final String INPUT_FILE_NAME = "day08/input.txt";

    private Day08() {
        throw new IllegalStateException("Utility class");
    }

    public static void solve() throws IOException {
        solvePartOne();
        solvePartTwo();
    }

    private static List<PointLong3D> getInput() throws IOException {
        InputStream inputStream = Day08.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
        List<PointLong3D> input = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                long[] coords = Arrays.stream(line.split(",")).mapToLong(Long::parseLong).toArray();
                input.add(new PointLong3D(coords[0], coords[1], coords[2]));
            }
        }
        return input;
    }

    private static void solvePartOne() throws IOException {
        final int junctionBoxesToConnect = 1000;
        List<PointLong3D> input = getInput();
        Map<Point, Long> coordsIndicesToDistanceSquaredMap = new HashMap<>();
        for (int i = 0; i < input.size() - 1; i++) {
            for (int j = i + 1; j < input.size(); j++) {
                long distanceSquared = input.get(i).getSquareOfEuclideanDistance(input.get(j));
                coordsIndicesToDistanceSquaredMap.put(new Point(i, j), distanceSquared);
            }
        }
        List<Point> distanceSquareOrderedByValues = returnListOfEntriesSortedByValue(coordsIndicesToDistanceSquaredMap);
        HashMap<PointLong3D, Integer> junctionBoxToNetworkMap = new HashMap<>();
        for (int i = 0; i < junctionBoxesToConnect; i++) {
            PointLong3D junctionBox1 = input.get(distanceSquareOrderedByValues.get(i).x);
            PointLong3D junctionBox2 = input.get(distanceSquareOrderedByValues.get(i).y);
            connectJunctionBoxes(junctionBox1, junctionBox2, junctionBoxToNetworkMap);
        }
        int[] groupedByCircuit = junctionBoxToNetworkMap
                .entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getValue))
                .values().stream().map(List::size).sorted(Collections.reverseOrder()).mapToInt(Integer::valueOf).toArray();
        int solution = groupedByCircuit[0] * groupedByCircuit[1] * groupedByCircuit[2];
        System.out.printf("The solution to part one is %s.%n", solution);
    }

    private static void solvePartTwo() throws IOException {
        List<PointLong3D> input = getInput();
        Map<Point, Long> coordsIndicesToDistanceSquaredMap = new HashMap<>();
        for (int i = 0; i < input.size() - 1; i++) {
            for (int j = i + 1; j < input.size(); j++) {
                long distanceSquared = input.get(i).getSquareOfEuclideanDistance(input.get(j));
                coordsIndicesToDistanceSquaredMap.put(new Point(i, j), distanceSquared);
            }
        }
        List<Point> distanceSquareOrderedByValues = returnListOfEntriesSortedByValue(coordsIndicesToDistanceSquaredMap);
        HashMap<PointLong3D, Integer> junctionBoxToNetworkMap = new HashMap<>();
        int circuitCount;
        int counter = 0;
        long solution = -1;
        do {
            PointLong3D junctionBox1 = input.get(distanceSquareOrderedByValues.get(counter).x);
            PointLong3D junctionBox2 = input.get(distanceSquareOrderedByValues.get(counter).y);
            connectJunctionBoxes(junctionBox1, junctionBox2, junctionBoxToNetworkMap);
            circuitCount = junctionBoxToNetworkMap
                    .entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getValue)).size();
            counter++;
            if (circuitCount == 1 && junctionBoxToNetworkMap.size() == input.size()) {
                solution = junctionBox1.x() * junctionBox2.x();
            }

        } while (circuitCount > 1 || junctionBoxToNetworkMap.size() < input.size());
        System.out.printf("The solution to part two is %s.%n", solution);
    }

    private static void connectJunctionBoxes(PointLong3D junctionBox1, PointLong3D junctionBox2, HashMap<PointLong3D, Integer> junctionBoxToNetworkMap) {
        Optional<Integer> maxCircuitNumber = junctionBoxToNetworkMap.values().stream().max(Integer::compare);
        if (!junctionBoxToNetworkMap.containsKey(junctionBox1) && !junctionBoxToNetworkMap.containsKey(junctionBox2)) {
            int nextCircuitNumber = maxCircuitNumber.map(integer -> integer + 1).orElse(1);
            junctionBoxToNetworkMap.put(junctionBox1, nextCircuitNumber);
            junctionBoxToNetworkMap.put(junctionBox2, nextCircuitNumber);
        } else if ((junctionBoxToNetworkMap.containsKey(junctionBox1) && !junctionBoxToNetworkMap.containsKey(junctionBox2)) ||
                (!junctionBoxToNetworkMap.containsKey(junctionBox1) && junctionBoxToNetworkMap.containsKey(junctionBox2))) {
            int circuitNumber = junctionBoxToNetworkMap.getOrDefault(junctionBox1, junctionBoxToNetworkMap.get(junctionBox2));
            junctionBoxToNetworkMap.put(junctionBox1, circuitNumber);
            junctionBoxToNetworkMap.put(junctionBox2, circuitNumber);
        } else if (junctionBoxToNetworkMap.containsKey(junctionBox1) && junctionBoxToNetworkMap.containsKey(junctionBox2)) {
            int circuitNumber1 = junctionBoxToNetworkMap.get(junctionBox1);
            int circuitNumber2 = junctionBoxToNetworkMap.get(junctionBox2);
            if (circuitNumber1 == circuitNumber2) {
                return;
            }
            List<PointLong3D> junctionBoxesInEitherNetwork = junctionBoxToNetworkMap.entrySet().stream()
                    .filter(x -> x.getValue() == circuitNumber1 || x.getValue() == circuitNumber2)
                    .map(Map.Entry::getKey)
                    .toList();
            for (PointLong3D junctionBox : junctionBoxesInEitherNetwork) {
                junctionBoxToNetworkMap.put(junctionBox, circuitNumber1);
            }
        } else {
            throw new IllegalStateException("State that I haven't thought of!");
        }
    }

    private static List<Point> returnListOfEntriesSortedByValue(Map<Point, Long> unsortedMap) {
        return unsortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toList();
    }
}
