package com.yopox;

public class IntPair {
    public static final IntPair DEFAULT = new IntPair(0, 0);

    public int first;
    public int second;

    public IntPair(int first, int second) {
        this.first = first;
        this.second = second;
    }
}
