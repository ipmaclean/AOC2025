package org.aoc2025.day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public List<Integer> getJoltageRequirements() {
        return joltageRequirements;
    }

    public boolean anyGreaterThanJoltageRequirements(List<Integer> joltages) {
        if (joltages.size() != joltageRequirements.size()) {
            throw new IllegalArgumentException("Wrong size list for joltages");
        }
        for (int i = 0; i < joltageRequirements.size(); i++) {
            if (joltages.get(i) > joltageRequirements.get(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesJoltageRequirements(List<Integer> joltages) {
        if (joltages.size() != joltageRequirements.size()) {
            throw new IllegalArgumentException("Wrong size list for joltages");
        }
        for (int i = 0; i < joltageRequirements.size(); i++) {
            if (!Objects.equals(joltages.get(i), joltageRequirements.get(i))) {
                return false;
            }
        }
        return true;
    }
}
