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

public class TitleScreen extends AbstractScreen {
    private static final Texture background = new Texture("title.png");

    private final Vector<Letter> option1 = Letter.listOf("Partie du jour", Colors.DEFAULT_FG, Colors.DEFAULT_BG);
    private final Vector<Letter> option2 = Letter.listOf("5 minutes chrono", Colors.DEFAULT_FG, Colors.DEFAULT_BG);
    private final Vector<Letter> option3 = Letter.listOf("Mode duel", Colors.DEFAULT_FG, Colors.DEFAULT_BG);

    private final Vector<Letter> arrow = Letter.listOf(">", Colors.DEFAULT_FG, Colors.DEFAULT_BG);
    private int selected = 0;

    public TitleScreen(DeviNUPES game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        myBatch.begin();
        myBatch.draw(background, 0f, 0f);
        Text.draw(option1, Util.TILE * 13, Util.TILE * 8, false, myBatch);
        Text.draw(option2, Util.TILE * 13, Util.TILE * 6, false, myBatch);
        Text.draw(option3, Util.TILE * 13, Util.TILE * 4, false, myBatch);
        Text.draw(arrow, Util.TILE * 10, Util.TILE * 8 - selected * 2 * Util.TILE, false, myBatch);
        myBatch.end();
    }

    @Override
    public boolean keyUp(int keycode) {
        int n_OPTIONS = 3;
        switch (keycode) {
            case Input.Keys.UP:
                SFX.playKeySound();
                selected = (selected - 1 + n_OPTIONS) % n_OPTIONS;
                break;
            case Input.Keys.DOWN:
                SFX.playKeySound();
                selected = (selected + 1 + n_OPTIONS) % n_OPTIONS;
                break;
            default:
                if (selected == 0) {
                    SFX.OK.play(SFX.VOLUME);
                    myGame.startGame(Util.Mode.DAILY);
                } else if (selected == 1) {
                    SFX.OK.play(SFX.VOLUME);
                    myGame.startGame(Util.Mode.TIME_TRIAL);
                } else {
                    SFX.OK.play(SFX.VOLUME);
                    myGame.chooseSeed();
                }
                break;
        }
        return true;
    }
}
