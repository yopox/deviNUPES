package com.yopox.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.yopox.Devinupes;
import com.yopox.Util;
import com.yopox.ui.Colors;
import com.yopox.ui.Letter;
import com.yopox.ui.Text;

import java.util.Vector;

public class TitleScreen extends AbstractScreen {
    private static final Texture background = new Texture("title.png");

    private Vector<Letter> option1 = Letter.listOf("Partie classique", Colors.DEFAULT_FG, Colors.DEFAULT_BG);
    private Vector<Letter> option2 = Letter.listOf("Contre la montre (5 min)", Colors.DEFAULT_FG, Colors.DEFAULT_BG);
    private Vector<Letter> option3 = Letter.listOf("Contre la montre (1 min)", Colors.DEFAULT_FG, Colors.DEFAULT_BG);

    private Vector<Letter> arrow = Letter.listOf(">", Colors.DEFAULT_FG, Colors.DEFAULT_BG);
    private final int N_OPTIONS = 3;
    private int selected = 0;

    public TitleScreen(Devinupes game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        myBatch.begin();
        myBatch.draw(background, 0f, 0f);
        Text.draw(option1, Util.TILE * 14, Util.TILE * 12, false, myBatch);
        Text.draw(option2, Util.TILE * 14, Util.TILE * 10, false, myBatch);
        Text.draw(option3, Util.TILE * 14, Util.TILE * 8, false, myBatch);
        Text.draw(arrow, Util.TILE * 12, Util.TILE * 12 - selected * 2 * Util.TILE, false, myBatch);
        myBatch.end();
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                selected = (selected - 1 + N_OPTIONS) % N_OPTIONS;
                break;
            case Input.Keys.DOWN:
                selected = (selected + 1 + N_OPTIONS) % N_OPTIONS;
                break;
            default:
                myGame.startGame();
                break;
        }
        return true;
    }
}
