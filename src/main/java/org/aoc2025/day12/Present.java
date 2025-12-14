package org.aoc2025.day12;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Present {
    private final List<String> shape;

    public Present(List<String> shape) {
        this.shape = shape;
    }

    public long getArea() {
        return shape.stream().mapToLong(x -> StringUtils.countMatches(x, "#")).sum();
    }
}
