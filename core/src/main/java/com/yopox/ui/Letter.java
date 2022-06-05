package com.yopox.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yopox.IntPair;
import com.yopox.Util;

import java.util.HashMap;
import java.util.Objects;
import java.util.Vector;

class LetterKey {
    private final int x;
    private final int y;
    private final Color fg;
    private final Color bg;

    public LetterKey(int y, int x, Color fg, Color bg) {
        this.x = x;
        this.y = y;
        this.fg = fg;
        this.bg = bg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LetterKey letterKey = (LetterKey) o;
        return x == letterKey.x && y == letterKey.y && fg.equals(letterKey.fg) && bg.equals(letterKey.bg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, fg, bg);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getFg() {
        return fg;
    }

    public Color getBg() {
        return bg;
    }
}

public class Letter {
    public static final String VALID_CHARS = "abcdefghijklmnopqrstuvwxyzàâçèéêëîôùû0123456789";
    public static final String GIVEN_CHARS = ":;,-/\"()&%' ";
    private static final HashMap<LetterKey, Texture> myTextures = new HashMap<>();

    private char myChar;
    private LetterKey myKey;

    private Color fg;
    private Color bg;

    public Letter(char character) {
        this(character, Colors.DEFAULT_FG, Colors.DEFAULT_BG);
    }

    public Letter(char character, Color fg, Color bg) {
        myChar = character;
        this.fg = fg;
        this.bg = bg;
        computeKey();
    }

    private void computeKey() {
        final IntPair indexes = Util.indexes.getOrDefault(myChar, IntPair.DEFAULT);
        myKey = new LetterKey(indexes.first, indexes.second, fg, bg);
    }

    public char getCharacter() {
        return myChar;
    }

    public void setCharacter(char character) {
        this.myChar = character;
        computeKey();
    }

    public Color getFg() {
        return fg;
    }

    public void setFg(Color fg) {
        this.fg = fg;
        computeKey();
    }

    public Color getBg() {
        return bg;
    }

    public void setBg(Color bg) {
        this.bg = bg;
        computeKey();
    }

    public Texture getTexture() {
        return myTextures.computeIfAbsent(myKey, letterKey -> {
            final TextureRegion region = Text.mrmoRegions[myKey.getY()][myKey.getX()];
            region.getTexture().getTextureData().prepare();
            final Pixmap original = region.getTexture().getTextureData().consumePixmap();

            final Pixmap pixmap = new Pixmap(Util.TILE.intValue(), Util.TILE.intValue(), Pixmap.Format.RGBA8888);
            for (int y = 0; y < pixmap.getHeight(); y++) {
                for (int x = 0; x < pixmap.getWidth(); x++) {
                    final Color color = original.getPixel(x + letterKey.getX() * Util.TILE.intValue(), y + letterKey.getY() * Util.TILE.intValue()) == -1
                            ? fg
                            : bg;
                    pixmap.setColor(color);
                    pixmap.drawPixel(x, y);
                }
            }

            return new Texture(pixmap);
        });
    }

    public static Vector<Letter> listOf(String text, Color fg, Color bg) {
        final Vector<Letter> vector = new Vector<>();
        for (int i = 0; i < text.length(); i++) {
            vector.add(new Letter(Character.toLowerCase(text.charAt(i)), fg, bg));
        }
        return vector;
    }

    public static String stringOf(Vector<Letter> vector) {
        StringBuilder result = new StringBuilder();
        for (Letter letter : vector) {
            result.append(letter.getCharacter());
        }
        return result.toString();
    }

    public static Float heightOf(Vector<Letter> vector) {
        if (vector.isEmpty()) return 0f;
        return (stringOf(vector).split("\n").length * 2 - 1) * Util.TILE;
    }
}