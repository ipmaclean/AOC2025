package org.aoc2025.day10;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MachineManualLine {

    private final int indicatorLightDiagram;
    private final List<Integer> wiringSchematics;
    private final List<Integer> joltageRequirements;

    public MachineManualLine(String inputLine) {
        String[] split = inputLine.split("\\s");
        String lightDiagram = split[0];

        List<Integer> wiringSchema = new ArrayList<>();
        for (int i = 1; i < split.length - 1; i++) {
            int[] buttonNumbers = Arrays.stream(split[i].replaceAll("[()]", "").split(",")).mapToInt(Integer::parseInt).toArray();
            int lightValue = 0;
            for (int buttonNumber : buttonNumbers) {
                lightValue += (int) Math.pow(2, lightDiagram.length() - buttonNumber - 3d);
            }
            wiringSchema.add(lightValue);
        }

        this.indicatorLightDiagram = Integer.parseInt(lightDiagram.replaceAll("[\\[\\]]", "").replace('.', '0').replace('#', '1'), 2);
        this.wiringSchematics = wiringSchema;
        this.joltageRequirements = Arrays.stream(split[split.length - 1].replaceAll("[{}]", "").split(",")).mapToInt(Integer::parseInt).boxed().toList();
    }

    public int getIndicatorLightDiagram() {
        return indicatorLightDiagram;
    }

    public List<Integer> getWiringSchematics() {
        return wiringSchematics;
    }

    public int[][] getTransposedMatrixWiringSchematics() {
        List<String> wiringSchematicsStrings = new ArrayList<>();
        for (int schematic : wiringSchematics) {
            wiringSchematicsStrings.add(StringUtils.leftPad(Integer.toBinaryString(schematic), joltageRequirements.size(), '0'));
        }
        int[][] transposed = new int[joltageRequirements.size()][wiringSchematics.size()];
        for (int i = 0; i < joltageRequirements.size(); i++) {
            for (int j = 0; j < wiringSchematicsStrings.size(); j++) {
                transposed[i][j] = wiringSchematicsStrings.get(j).charAt(i) - '0';
            }
        }
        return transposed;
    }

    public List<Integer> getJoltageRequirements() {
        return joltageRequirements;
    }
}
