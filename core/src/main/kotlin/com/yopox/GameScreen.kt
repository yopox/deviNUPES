package com.yopox

import com.badlogic.gdx.graphics.Texture
import com.yopox.data.randomPropositions
import com.yopox.ui.GUI
import ktx.graphics.use
import java.util.*

class GameScreen : AbstractScreen() {
    private val background = Texture("background.png")
    private var currentGuess = ""
    private val propositionsSet = randomPropositions()
    private var proposition = propositionsSet.removeAt(0)
    private var time = Date()
    private var guesses = 0

    private enum class State {
        GUESS,          // The player can write the guess
        ANIMATE_GUESS,  // The player validated a guess
        REVEAL,         // The proposition appears
        HIDE,           // The proposition disappears
    }
    private var state = State.REVEAL

    override fun render(delta: Float) {
        super.render(delta)

        when (state) {
            State.REVEAL -> if (proposition.reveal()) state = State.GUESS
            State.ANIMATE_GUESS -> {
                if (proposition.update()) {
                    state = if (proposition.over) State.HIDE else State.GUESS
                }
            }
            State.HIDE -> {
                if (proposition.hide()) {
                    if (propositionsSet.isEmpty()) {
                        // TODO: Fini
                    } else {
                        proposition = propositionsSet.removeAt(0)
                        state = State.REVEAL
                    }
                }
            }
            else -> Unit
        }

        batch.use {
            it.draw(background, 0f, 0f)
            GUI.draw(guesses, time, batch)
            proposition.draw(currentGuess, TILE * 8, batch)
        }
    }

    override fun keyTyped(character: Char): Boolean {
        if (state != State.GUESS) return false
        when (character) {
            '\n' -> guess()
            '\b' -> backspace()
            else -> type(character)
        }
        return true
    }

    private fun type(character: Char) {
        currentGuess = proposition.completeGuess(currentGuess, character)
    }

    private fun backspace() {
        currentGuess = currentGuess.dropLast(1)
    }

    private fun guess() {
        proposition.guess(currentGuess)
        currentGuess = ""
        guesses += 1
        state = State.ANIMATE_GUESS
    }
}