package com.yopox.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yopox.Util;

import java.util.Date;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Stream;

public class GUI {
    private static Vector<Letter> alphabet = Letter.listOf("abcdefghijklmnopqrstuvwxyz", Colors.WHITE, Colors.BLUE);
    private static Vector<Letter> numbers = Letter.listOf("0123456789", Colors.WHITE, Colors.BLUE);
//    private static SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");

    public static void draw(int guesses, Date time, SpriteBatch batch) {
        Text.draw(alphabet, Util.TILE * 2, Util.HEIGHT - Util.TILE * 3, false, batch);
        Text.draw(numbers, Util.TILE * 2, Util.HEIGHT - Util.TILE * 4, false, batch);

//        final String timer = dateFormat.format(new Date(System.currentTimeMillis() - time.getTime()));
//        Text.draw(Letter.listOf(timer, Colors.WHITE, Colors.BLUE), Util.TILE * 33, Util.HEIGHT - Util.TILE * 5, false, batch);
        Text.draw(Letter.listOf(String.valueOf(guesses), Colors.WHITE, Colors.BLUE), Util.TILE * 34, Util.HEIGHT - Util.TILE * 3, false, batch);
    }

    public static void update(char c, Color color) {
        Stream<Letter> stream = null;
        if (c >= 'a' && c <= 'z') stream = alphabet.stream();
        else if (c >= '0' && c <= '9') stream = numbers.stream();

        if (stream != null) {
            final Optional<Letter> optional = stream.filter(l -> l.getCharacter() == c).findFirst();
            if (optional.isPresent()) {
                final Letter letter = optional.get();
                if (color != Colors.ORANGE || letter.getFg() != Colors.GREEN) letter.setFg(color);
            }
        }
    }

    public static void reset() {
        alphabet.forEach(l -> l.setFg(Colors.WHITE));
        numbers.forEach(l -> l.setFg(Colors.WHITE));
    }
}
