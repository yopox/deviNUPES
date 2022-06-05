package com.yopox;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yopox.data.Propositions;
import com.yopox.ui.Colors;
import com.yopox.ui.GUI;
import com.yopox.ui.Letter;
import com.yopox.ui.Text;

import java.util.Optional;
import java.util.Vector;
import java.util.function.Predicate;

public class Proposition {
    private static final char UNKNOWN = '~';

    private final Vector<Letter> before;
    private final Vector<Letter> guess;
    private final Vector<Letter> after;

    private final String answer;
    private final Float answerOffset;
    private final Vector<Integer> found = new Vector<>();

    private String lastGuess = "";
    private int counter = 0;
    private int charCounter = 0;

    public Proposition(Propositions.Data data) {
        this(data.before, data.guess, data.after);
    }

    public Proposition(String before, String answer, String after) {
        this.answer = answer;
        this.answerOffset = (Util.WIDTH - answer.length() * Util.TILE) / 2;

        this.before = Letter.listOf(before, Colors.WHITE, Colors.WHITE);
        this.after = Letter.listOf(after, Colors.WHITE, Colors.WHITE);
        this.guess = new Vector<>();
        for (int i = 0; i < answer.length(); i++) {
            final char c = answer.charAt(i);
            this.guess.add(new Letter(Letter.GIVEN_CHARS.contains(String.valueOf(c)) ? c : UNKNOWN, Colors.WHITE, Colors.WHITE));
        }
    }

    public boolean isOver() {
        for (int i = 0; i < answer.length(); i++) {
            final char c = Character.toLowerCase(answer.charAt(i));
            if (Letter.VALID_CHARS.contains(String.valueOf(c))) {
                if (!found.contains(i)) return false;
            }
        }
        return true;
    }

    public void draw(String currentGuess, float posY, SpriteBatch batch) {
        final float h1 = Letter.heightOf(before);
        final float h2 = Letter.heightOf(guess);
        final float h3 = Letter.heightOf(after);

        final float gap1 = before.isEmpty() ? 0f : Util.TILE;
        final float gap2 = after.isEmpty() ? 0f : Util.TILE;
        final float h = h1 + h2 + h3 + gap1 + gap2;
        final float midY = posY + h / 2 - h1 - gap1 - h2 / 2;

        if (!before.isEmpty()) Text.draw(before, -1f, midY + h1 / 2 + gap1 + h2 / 2, true, batch);
        Text.draw(guess, -1f, midY, true, batch);
        if (!after.isEmpty()) Text.draw(after, -1f, midY - h2 / 2 - gap2 - h3 / 2, true, batch);

        Text.draw(Letter.listOf(currentGuess, Colors.BLUE, Colors.WHITE), answerOffset, midY, true, batch);
    }

    public boolean update() {
        if (charCounter == lastGuess.length()) {
            updateGUI();
            charCounter = 0;
            counter = 0;
            return true;
        }
        if (counter == 0) {
            updateGuessChar(charCounter);
            final Color fg = guess.get(charCounter).getFg();
            if (Colors.GREEN.equals(fg)) {
                counter = 6;
            } else if (Colors.ORANGE.equals(fg)) {
                counter = 8;
            } else {
                counter = 4;
            }
            charCounter += 1;
        } else {
            counter -= 1;
        }
        return false;
    }

    private void updateGuessChar(int i) {
        if (i >= answer.length()) return;
        final char character = sanitize(lastGuess.charAt(i));
        if (!Letter.VALID_CHARS.contains(String.valueOf(character))) return;

        guess.get(i).setCharacter(lastGuess.charAt(i));
        if (character == sanitize(answer.charAt(i))) {
            guess.get(i).setFg(Colors.GREEN);
            SFX.GREEN.play(SFX.VOLUME);
            if (!found.contains(i)) found.add(i);
        } else if (!answer.contains(String.valueOf(character))) {
            SFX.RED.play(SFX.VOLUME);
            guess.get(i).setFg(Colors.RED);
        } else {
            int rightChars = 0;
            int alreadyFound = 0;
            int wrongChars = 0;
            int nChar = 0;

            for (int j = 0; j < answer.length(); j++) {
                final char aChar = sanitize(answer.charAt(j));
                if (aChar == character) nChar += 1;

                if (lastGuess.length() <= j) continue;
                if (sanitize(lastGuess.charAt(j)) == aChar && aChar == character) rightChars += 1;
                else if (j < i && sanitize(lastGuess.charAt(j)) == character) wrongChars += 1;
                if (found.contains(j) && sanitize(lastGuess.charAt(j)) == character) alreadyFound += 1;
            }

            final Color color;
            if (nChar > Math.max(rightChars, alreadyFound) + wrongChars) {
                color = Colors.ORANGE;
                SFX.ORANGE.play(SFX.VOLUME);
                GUI.update(character, Colors.ORANGE);
            }
            else color = Colors.BLUE;

            guess.get(i).setFg(color);
        }

        if (found.contains(i)) {
            guess.get(i).setCharacter(Character.toLowerCase(answer.charAt(i)));
            guess.get(i).setFg(Colors.GREEN);
        }
    }

    private char sanitize(char character) {
        final char lowerCase = Character.toLowerCase(character);
        switch (lowerCase) {
            case 'à':
            case 'â':
                return 'a';
            case 'ç':
                return 'c';
            case 'è':
            case 'é':
            case 'ê':
            case 'ë':
                return 'e';
            case 'î':
                return 'i';
            case 'ô':
                return 'o';
            case 'ù':
            case 'û':
                return 'u';
            default:
                return lowerCase;
        }
    }

    public boolean reveal() {
        Predicate<Letter> predicate = (Letter l) -> l.getFg() == Colors.WHITE;
        if (counter > 0) {
            counter -= 1;
            return false;
        }

        if (before.stream().anyMatch(predicate)) {
            updateFirstAndSetCounter(before, predicate, Colors.BLUE, 1, 2);
        } else if (guess.stream().anyMatch(predicate)) {
            updateFirstAndSetCounter(guess, predicate, Colors.BLUE, 2, 4);
        } else if (after.stream().anyMatch(predicate)) {
            updateFirstAndSetCounter(after, predicate, Colors.BLUE, 1, 2);
        } else {
            counter = 0;
            return true;
        }
        return false;
    }

    public boolean hide() {
        Predicate<Letter> predicate = (Letter l) -> l.getFg() != Colors.WHITE;

        if (before.stream().anyMatch(predicate)) {
            updateFirstAndSetCounter(before, predicate, Colors.WHITE, 0, 0);
        } else if (guess.stream().anyMatch(predicate)) {
            updateFirstAndSetCounter(guess, predicate, Colors.WHITE, 0, 0);
        } else if (after.stream().anyMatch(predicate)) {
            updateFirstAndSetCounter(after, predicate, Colors.WHITE, 0, 0);
        } else {
            return true;
        }

        return false;
    }

    private void updateFirstAndSetCounter(Vector<Letter> text, Predicate<Letter> predicate, Color fg, int shortWait, int longWait) {
        final Optional<Letter> optional = text.stream().filter(predicate).findFirst();
        if (optional.isPresent()) {
            final Letter l = optional.get();
            l.setFg(fg);
            counter = Letter.VALID_CHARS.contains(String.valueOf(l.getCharacter())) ? shortWait : longWait;
            SFX.KEY.play(SFX.VOLUME / 3);
        }
    }

    public String completeGuess(String currentGuess, char c) {
        if (!Letter.VALID_CHARS.contains(String.valueOf(Character.toLowerCase(c)))) return currentGuess;
        if (currentGuess.length() == answer.length()) return currentGuess;
        StringBuilder newGuess = new StringBuilder(currentGuess);
        char nextChar = answer.charAt(newGuess.length());
        while (Letter.GIVEN_CHARS.contains(String.valueOf(nextChar))) {
            newGuess.append(nextChar);
            nextChar = answer.charAt(newGuess.length());
        }
        newGuess.append(c);
        return newGuess.substring(0, Math.min(newGuess.length(), answer.length()));
    }

    public void guess(String text) {
        lastGuess = text;
        for (int i = 0; i < text.length(); i++) {
            guess.get(i).setCharacter(text.charAt(i));
            guess.get(i).setFg(Colors.BLUE);
        }
    }

    private void updateGUI() {
        for (int i = 0; i < Letter.VALID_CHARS.length(); i++) {
            final char c = sanitize(Letter.VALID_CHARS.charAt(i));
            boolean isInAnswer = false;
            boolean all = true;
            for (int j = 0; j < answer.length(); j++) {
                if (sanitize(answer.charAt(j)) == c) {
                    isInAnswer = true;
                    if (sanitize(guess.get(j).getCharacter()) != c) all = false;
                }
            }
            if (isInAnswer && all) GUI.update(c, Colors.GREEN);
            else if (lastGuess.contains(String.valueOf(c)) && !answer.contains(String.valueOf(c))) {
                GUI.update(c, Colors.RED);
            }
        }
    }
}
