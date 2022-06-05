package com.yopox.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.yopox.Devinupes;
import com.yopox.Proposition;
import com.yopox.Util;
import com.yopox.data.Propositions;
import com.yopox.ui.GUI;

import java.util.Date;
import java.util.Vector;

public class PropositionScreen extends AbstractScreen {
    private static Texture background = new Texture("background.png");

    private Vector<Propositions.Data> dataSet = new Vector<>();
    private Proposition proposition = new Proposition("", "", "");

    private String currentGuess = "";
    private Date time = new Date();
    private int guesses = 0;

    public PropositionScreen(Devinupes game) {
        super(game);
    }

    enum State {
        GUESS,          // The player can write the guess
        ANIMATE_GUESS,  // The player validated a guess
        REVEAL,         // The proposition appears
        HIDE,           // The proposition disappears
    }

    private State myState = State.REVEAL;

    public void reset() {
        dataSet = Propositions.getRandomPropositions();
        nextProposition();
    }

    private void nextProposition() {
        final Propositions.Data data = dataSet.remove(0);
        proposition = new Proposition(data.before, data.guess, data.after);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        switch (myState) {
            case REVEAL:
                if (proposition.reveal()) {
                    myState = State.GUESS;
                }
                break;
            case ANIMATE_GUESS:
                if (proposition.update()) {
                    if (proposition.isOver()) myState = State.HIDE;
                    else myState = State.GUESS;
                }
                break;
            case HIDE:
                if (proposition.hide()) {
                    if (dataSet.isEmpty()) {
                        // TODO: Fini
                    } else {
                        nextProposition();
                        GUI.reset();
                        myState = State.REVEAL;
                    }
                }
                break;
        }

        myBatch.begin();
        myBatch.draw(background, 0f, 0f);
        proposition.draw(currentGuess, Util.TILE * 8, myBatch);
        GUI.draw(guesses, time, myBatch);
        myBatch.end();
    }

    @Override
    public boolean keyUp(int keycode) {
        if (myState != State.GUESS) return false;
        if (keycode >= Input.Keys.A && keycode <= Input.Keys.Z) {
            type((char) (keycode - Input.Keys.A + 97));
        } else if (keycode >= Input.Keys.NUM_0 && keycode <= Input.Keys.NUM_9) {
            type((char) (keycode - Input.Keys.NUM_0 + 48));
        } else {
            switch (keycode) {
                case Input.Keys.ENTER:
                    guess();
                    break;
                case Input.Keys.BACKSPACE:
                    backspace();
                    break;
            }
        }
        return true;
    }

    private void type(char c) {
        currentGuess = proposition.completeGuess(currentGuess, c);
    }

    private void guess() {
        proposition.guess(currentGuess);
        currentGuess = "";
        guesses += 1;
        myState = State.ANIMATE_GUESS;
    }

    private void backspace() {
        if (!currentGuess.isEmpty()) currentGuess = currentGuess.substring(0, currentGuess.length() - 1);
    }
}
