package org.aoc2025.utils;

public record PointLong3D(long x, long y, long z) {
    public long getSquareOfEuclideanDistance(PointLong3D other) {
        long xDiff = this.x - other.x();
        long yDiff = this.y - other.y();
        long zDiff = this.z - other.z();
        return xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
    }
}
