package com.yopox.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.yopox.*;
import com.yopox.data.Propositions;
import com.yopox.ui.GUI;

import java.util.HashSet;
import java.util.Vector;

public class PropositionScreen extends AbstractScreen {
    private static final Texture background = new Texture("background.png");

    private Vector<Propositions.Data> dataSet = new Vector<>();
    private final HashSet<Propositions.Data> oldData = new HashSet<>();
    private Proposition proposition = new Proposition("", "", "");

    private String currentGuess = "";
    private Chrono chrono = new Chrono();
    private int guesses = 0;
    private int guessed = 0;
    private Util.Mode mode = Util.Mode.DAILY;

    public PropositionScreen(DeviNUPES game) {
        super(game);
    }

    enum State {
        GUESS,          // The player can write the guess
        ANIMATE_GUESS,  // The player validated a guess
        REVEAL,         // The proposition appears
        HIDE,           // The proposition disappears
    }

    private State myState = State.REVEAL;

    public void reset(Util.Mode mode) {
        this.mode = mode;
        GUI.reset();
        guesses = 0;
        guessed = 0;
        oldData.clear();
        dataSet.clear();
        currentGuess = "";
        switch (mode) {
            case DAILY:
                dataSet = Propositions.getDailyPropositions();
                chrono = new Chrono();
                break;
            case SEED_FIVE:
                dataSet = Propositions.getPropositions(0);
                chrono = new Chrono();
                break;
            case TIME_TRIAL:
            case SEED_TIME_TRIAL:
                chrono = new Chrono(60 * 5L);
                break;
        }
        nextProposition();
        myState = State.REVEAL;
    }

    private void nextProposition() {
        switch (mode) {
            case DAILY:
            case SEED_FIVE:
                final Propositions.Data data = dataSet.remove(0);
                proposition = new Proposition(data.before, data.guess, data.after);
                break;
            case TIME_TRIAL:
            case SEED_TIME_TRIAL:
                proposition = new Proposition(Propositions.getNewProposition(oldData));
                break;
        }
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
                    if (proposition.isOver()) {
                        myState = State.HIDE;
                        guessed += 1;
                    } else {
                        myState = State.GUESS;
                        chrono.start();
                    }
                }
                break;
            case HIDE:
                if (proposition.hide()) {
                    if (dataSet.isEmpty() && !Util.isTimeTrial(mode)) {
                        SFX.WIN.play(SFX.VOLUME);
                        myGame.showResults(guesses, chrono);
                    } else {
                        nextProposition();
                        GUI.reset();
                        myState = State.REVEAL;
                    }
                }
                break;
            case GUESS:
                if (Util.isTimeTrial(mode) && chrono.isTimeOver()) {
                    SFX.TIME_OUT.play(SFX.VOLUME);
                    myGame.showResults(guessed, guesses);
                }
        }

        myBatch.begin();
        myBatch.draw(background, 0f, 0f);
        proposition.draw(currentGuess, Util.TILE * 8, myBatch);
        GUI.draw(guesses, chrono, myBatch);
        myBatch.end();
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.ENTER:
                if (myState == State.GUESS) guess();
                break;
            case Input.Keys.BACKSPACE:
                backspace();
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
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
