package com.yopox.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.yopox.DeviNUPES;
import com.yopox.SFX;
import com.yopox.Util;
import com.yopox.ui.Colors;
import com.yopox.ui.Letter;
import com.yopox.ui.Text;

import java.util.Vector;

public class SeedScreen extends AbstractScreen {
    private static String seed = "";
    private static final Texture background = new Texture("seed.png");
    private Vector<Letter> digits = Letter.listOf("~~~~~~~~~~", Colors.BLUE, Colors.WHITE);
    private Vector<Letter> line1 = Letter.listOf("Entrez un seed :", Colors.BLUE, Colors.WHITE);
    private Vector<Letter> line2 = Letter.listOf("Mode de jeu :", Colors.BLUE, Colors.WHITE);
    private Vector<Letter> line3 = Letter.listOf("5 mesures", Colors.BLUE, Colors.WHITE);

    private Vector<Letter> line4 = Letter.listOf("5 minutes", Colors.BLUE, Colors.WHITE);
    private Vector<Letter> arrow = Letter.listOf(">", Colors.BLUE, Colors.WHITE);
    private int selected = 0;

    public SeedScreen(DeviNUPES game) {
        super(game);
    }

    public static String getSeed() {
        return seed;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        myBatch.begin();
        myBatch.draw(background, 0f, 0f);
        Text.draw(line1, Util.TILE * 12, Util.HEIGHT / 2 + Util.TILE * 3, false, myBatch);
        Text.draw(digits, Util.TILE * 15, Util.HEIGHT / 2, false, myBatch);
        Text.draw(Letter.listOf(seed, Colors.BLUE, Colors.WHITE), Util.TILE * 15, Util.HEIGHT / 2, false, myBatch);

        Text.draw(line2, Util.TILE * 12, Util.HEIGHT / 2 - Util.TILE * 3, false, myBatch);
        Text.draw(line3, Util.TILE * 19, Util.HEIGHT / 2 - Util.TILE * 6, false, myBatch);
        Text.draw(line4, Util.TILE * 19, Util.HEIGHT / 2 - Util.TILE * 9, false, myBatch);

        Text.draw(arrow, Util.TILE * 17, Util.HEIGHT / 2 - Util.TILE * 6 - selected * Util.TILE * 3, false, myBatch);
        myBatch.end();
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
            case Input.Keys.DOWN:
                selected = (selected + 1) % 2;
                SFX.playKeySound();
                break;
            case Input.Keys.ENTER:
                if (selected == 0) {
                    myGame.startGame(Util.Mode.SEED_FIVE);
                } else {
                    myGame.startGame(Util.Mode.SEED_TIME_TRIAL);
                }
                break;
            case Input.Keys.BACKSPACE:
                backspace();
                break;
            case Input.Keys.ESCAPE:
                myGame.showTitle();
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        type(character);
        return true;
    }

    private void type(char character) {
        if (seed.length() == 10) return;
        final char c = Character.toLowerCase(character);
        if (Letter.VALID_CHARS.contains(String.valueOf(c))) {
            SFX.playKeySound();
            seed += c;
        }
    }

    private void backspace() {
        if (seed.length() > 0) {
            seed = seed.substring(0, seed.length() - 1);
        }
    }

    public void reset() {
        seed = "";
    }
}
