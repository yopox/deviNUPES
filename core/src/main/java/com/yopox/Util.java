package com.yopox;

import java.util.HashMap;

public class Util {
    public static final Float TILE = 8f;
    public static final Float WIDTH = TILE * 40;
    public static final Float HEIGHT = TILE * 22;

    public enum Mode {
        DAILY,
        TIME_TRIAL,
        SEED_FIVE,
        SEED_TIME_TRIAL,
    }

    public static boolean isTimeTrial(Mode mode) {
        return mode == Mode.TIME_TRIAL || mode == Mode.SEED_TIME_TRIAL;
    }

    public static HashMap<Character, IntPair> indexes = new HashMap<Character, IntPair>() {{
        put('>', new IntPair(0, 15));
        put('~', new IntPair(16, 24));
        put('=', new IntPair(23, 15));
        put('!', new IntPair(27, 1));
        put('?', new IntPair(27, 31));
        put('"', new IntPair(27, 2));
        put('%', new IntPair(27, 5));
        put('&', new IntPair(27, 6));
        put('\'', new IntPair(27, 7));
        put('(', new IntPair(27, 8));
        put(')', new IntPair(27, 9));
        put(',', new IntPair(27, 12));
        put('-', new IntPair(27, 13));
        put('.', new IntPair(27, 14));
        put('/', new IntPair(27, 15));
        put(':', new IntPair(27, 26));
        put(';', new IntPair(27, 27));
        put('à', new IntPair(29, 20));
        put('â', new IntPair(29, 22));
        put('ç', new IntPair(29, 27));
        put('è', new IntPair(29, 28));
        put('é', new IntPair(29, 29));
        put('ê', new IntPair(29, 30));
        put('ë', new IntPair(29, 31));
        put('î', new IntPair(30, 2));
        put('ô', new IntPair(30, 8));
        put('ù', new IntPair(30, 12));
        put('û', new IntPair(30, 14));
    }};

    static {
        for (int i = 0; i <= 25; i++) {
            indexes.put((char) (97 + i), new IntPair(28, i + 1));
        }
        for (int i = 0; i <= 9; i++) {
            indexes.put((char) (48 + i), new IntPair(27, i + 16));
        }
    }
}
