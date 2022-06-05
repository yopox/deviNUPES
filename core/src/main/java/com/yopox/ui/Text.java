package com.yopox.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.yopox.Util;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class Text {
    public static final Texture mrmoTexture = new Texture("MRMOTEXT.png");
    public static TextureRegion[][] mrmoRegions = TextureRegion.split(mrmoTexture, Util.TILE.intValue(), Util.TILE.intValue());

    public static void draw(Vector<Letter> text, Float x, Float y, boolean yShift, SpriteBatch batch) {
        final List<Integer> lengths = Arrays.stream(Letter.stringOf(text).split("\n")).map(s -> s.length()).collect(Collectors.toList());
        if (lengths.isEmpty()) return;
        final Float maxWidth = lengths.stream().max((i1, i2) -> i2 - i1).get() * Util.TILE;
        final Vector<Float> xOffsets = lengths.stream()
                .map(i -> (maxWidth - i * Util.TILE) / 2)
                .collect(Collectors.toCollection(Vector::new));
        final Float xOffset = x == -1f ? (Util.WIDTH - maxWidth) / 2 : x;
        final Float yOffset = yShift ? Letter.heightOf(text) / 2 : 0f;

        drawStep(text, xOffsets, xOffset, y + yOffset, batch);
    }

    private static void drawStep(
            Vector<Letter> text,
            Vector<Float> xOffsets,
            Float x,
            Float y,
            SpriteBatch batch) {
        final Vector2 offset = new Vector2();
        for (final Letter l : text) {
            if (l.getCharacter() == '\n') {
                offset.x = 0f;
                offset.y -= 2 * Util.TILE;
                xOffsets.remove(0);
            } else {
                batch.draw(
                        l.getTexture(),
                        x + offset.x + xOffsets.get(0),
                        y + offset.y
                );
                offset.x += Util.TILE;
            }
        }
    }
}
