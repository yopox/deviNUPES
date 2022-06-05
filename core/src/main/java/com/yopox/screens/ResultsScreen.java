package com.yopox.screens;

import com.badlogic.gdx.graphics.Texture;
import com.yopox.Chrono;
import com.yopox.DeviNUPES;
import com.yopox.Util;
import com.yopox.ui.Colors;
import com.yopox.ui.Letter;
import com.yopox.ui.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class ResultsScreen extends AbstractScreen {
    private static final Texture background = new Texture("results.png");
    private final static List<Texture> characters = new ArrayList<Texture>() {{
        add(new Texture("jlm.png"));
        add(new Texture("bayou.png"));
        add(new Texture("obono.png"));
    }};

    private final Vector<Vector<Letter>> text = new Vector<>();

    private Texture myCharacter = characters.get(0);

    public ResultsScreen(DeviNUPES game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        myBatch.begin();
        myBatch.draw(background, 0f, 0f);
        myBatch.draw(myCharacter, 5 * Util.TILE, 4 * Util.TILE);
        for (int i = 0; i < text.size(); i++) {
            if (text.get(i) == null) continue;
            Text.draw(text.get(i), Util.TILE * 17, Util.HEIGHT - Util.TILE * (7 + i), false, myBatch);
        }
        myBatch.end();
    }

    @Override
    public boolean keyUp(int keycode) {
        myGame.showTitle();
        return true;
    }

    private void reset() {
        myCharacter = characters.get((int) Math.floor(new Random().nextDouble() * characters.size()));
        text.removeAllElements();
    }

    public void reset(int guesses, Chrono chrono) {
        reset();

        text.add(Letter.listOf("Tu as trouvé les", Colors.BLUE, Colors.WHITE));
        text.add(Letter.listOf("5 propositions", Colors.BLUE, Colors.WHITE));
        text.add(Letter.listOf("en :", Colors.BLUE, Colors.WHITE));
        text.add(null);
        text.add(null);
        text.add(Letter.listOf("   >  " + chrono, Colors.BLUE, Colors.WHITE));
        text.add(Letter.listOf("   >  " + guesses + " coups", Colors.BLUE, Colors.WHITE));
        text.add(null);
        text.add(null);
        text.add(Letter.listOf("                =", Colors.BLUE, Colors.WHITE));
    }

    public void reset(int guessed, int guesses) {
        reset();

        text.add(null);
        text.add(Letter.listOf(" En 5 minutes,", Colors.BLUE, Colors.WHITE));
        text.add(Letter.listOf(" tu as trouvé :", Colors.BLUE, Colors.WHITE));
        text.add(null);
        text.add(null);
        text.add(Letter.listOf(" >  " + guessed + " mesures", Colors.BLUE, Colors.WHITE));
        text.add(Letter.listOf(" >  en " + guesses + " coups", Colors.BLUE, Colors.WHITE));
        text.add(null);
        text.add(null);
        text.add(Letter.listOf("               =", Colors.BLUE, Colors.WHITE));
    }
}
