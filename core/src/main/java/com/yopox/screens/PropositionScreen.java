package com.yopox.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.yopox.Chrono;
import com.yopox.Devinupes;
import com.yopox.Proposition;
import com.yopox.Util;
import com.yopox.data.Propositions;
import com.yopox.ui.GUI;

import java.util.Vector;

public class PropositionScreen extends AbstractScreen {
    private static final Texture background = new Texture("background.png");

    private Vector<Propositions.Data> dataSet = new Vector<>();
    private Proposition proposition = new Proposition("", "", "");

    private String currentGuess = "";
    private Chrono chrono = new Chrono();
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
        chrono = new Chrono();
        nextProposition();
    }

    private void nextProposition() {
        final Propositions.Data data = dataSet.remove(0);
        proposition = new Proposition(data.before, data.guess, data.after);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        chrono.update(delta);

        switch (myState) {
            case REVEAL:
                if (proposition.reveal()) {
                    myState = State.GUESS;
                    chrono.start();
                }
                break;
            case ANIMATE_GUESS:
                if (proposition.update()) {
                    if (proposition.isOver()) myState = State.HIDE;
                    else {
                        myState = State.GUESS;
                        chrono.start();
                    }
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
        GUI.draw(guesses, chrono, myBatch);
        myBatch.end();
    }

    @Override
    public boolean keyUp(int keycode) {
        if (myState != State.GUESS) return false;
        switch (keycode) {
            case Input.Keys.ENTER:
                guess();
                break;
            case Input.Keys.BACKSPACE:
                backspace();
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        if (myState != State.GUESS) return false;
        type(character);
        return true;
    }

    private void type(char c) {
        currentGuess = proposition.completeGuess(currentGuess, c);
    }

    private void guess() {
        proposition.guess(currentGuess);
        currentGuess = "";
        guesses += 1;
        chrono.stop();
        myState = State.ANIMATE_GUESS;
    }

    private void backspace() {
        if (!currentGuess.isEmpty()) currentGuess = currentGuess.substring(0, currentGuess.length() - 1);
    }
}
