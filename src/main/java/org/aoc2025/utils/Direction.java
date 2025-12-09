package org.aoc2025.utils;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
    LEFT(0, new PointLong(-1, 0)),
    UP(1, new PointLong(0, -1)),
    RIGHT(2, new PointLong(1, 0)),
    DOWN(3, new PointLong(0, 1));


    private static final Map<Long, Direction> BY_INDEX = new HashMap<>();
    private static final Map<PointLong, Direction> BY_COORD = new HashMap<>();

    static {
        for (Direction d : values()) {
            BY_INDEX.put(d.index, d);
            BY_COORD.put(d.coord, d);
        }
    }

    public final long index;
    public final PointLong coord;

    Direction(long index, PointLong coord) {
        this.index = index;
        this.coord = coord;
    }

    public static Direction valueOfIndex(long index) {
        return BY_INDEX.get(index);
    }

    public static Direction valueOfCoord(PointLong coord) {
        return BY_COORD.get(coord);
    }
}
