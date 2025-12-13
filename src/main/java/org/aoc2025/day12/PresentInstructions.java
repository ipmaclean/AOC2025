package org.aoc2025.day12;

import org.aoc2025.utils.PointLong;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PresentInstructions {
    private final PointLong regionDimensions;
    private final long[] presentCounts;

    public PresentInstructions(String inputLine) {

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(inputLine);

        matcher.find();
        long xDim = Long.parseLong(matcher.group());
        matcher.find();
        long yDim = Long.parseLong(matcher.group());

        regionDimensions = new PointLong(xDim, yDim);

        presentCounts = new long[6];
        int counter = 0;
        while (matcher.find()) {
            presentCounts[counter++] = Long.parseLong(matcher.group());
        }
    }

    public long[] getPresentCounts() {
        return presentCounts;
    }

    public PointLong getRegionDimensions() {
        return regionDimensions;
    }
}
