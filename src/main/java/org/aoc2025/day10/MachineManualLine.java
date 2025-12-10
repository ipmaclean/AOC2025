package org.aoc2025.day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MachineManualLine {

    private final int indicatorLightDiagram;
    private final List<Integer> wiringSchematics;
    private final String joltageRequirements;

    public MachineManualLine(String inputLine) {
        String[] split = inputLine.split("\\s");
        String lightDiagram = split[0];

        this.indicatorLightDiagram = Integer.parseInt(lightDiagram.replaceAll("[\\[\\]]", "").replace('.', '0').replace('#', '1'), 2);

        List<Integer> wiringSchema = new ArrayList<>();
        for (int i = 1; i < split.length - 1; i++) {
            int[] buttonNumbers = Arrays.stream(split[i].replaceAll("[()]", "").split(",")).mapToInt(Integer::parseInt).toArray();
            int lightValue = 0;
            for (int buttonNumber : buttonNumbers) {
                lightValue += (int) Math.pow(2, lightDiagram.length() - buttonNumber - 3d);
            }
            wiringSchema.add(lightValue);
        }

        this.wiringSchematics = wiringSchema;
        this.joltageRequirements = split[split.length - 1];
    }

    public int getIndicatorLightDiagram() {
        return indicatorLightDiagram;
    }

    public List<Integer> getWiringSchematics() {
        return wiringSchematics;
    }

    public String getJoltageRequirements() {
        return joltageRequirements;
    }
}
