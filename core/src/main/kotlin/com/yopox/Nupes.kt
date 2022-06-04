package com.yopox

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.yopox.data.propositions
import com.yopox.ui.Colors
import com.yopox.ui.GUI
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use
import java.util.*

class Nupes : KtxGame<KtxScreen>() {
    override fun create() {
        addScreen(FirstScreen())
        setScreen<FirstScreen>()
    }
}

val TILE = 8f
val WIDTH = TILE * 40
val HEIGHT = TILE * 22

class FirstScreen : KtxScreen, InputProcessor {
    private val batch = SpriteBatch()
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(WIDTH, HEIGHT, camera)

    private val background = Texture("background.png")

    private var currentGuess = ""
    private var proposition = propositions.random()
    private var time = Date()
    private var guesses = 0

    private enum class State {
        GUESS,          // The player can write the guess
        ANIMATE_GUESS,  // The player validated a guess
        REVEAL,         // The proposition appears
        HIDE,           // The proposition disappears
    }
    private var state = State.REVEAL

    override fun show() {
        Gdx.app.input.inputProcessor = this
    }

    override fun render(delta: Float) {
        batch.projectionMatrix = camera.combined
        clearScreen(Colors.BLUE.r, Colors.BLUE.g, Colors.BLUE.b)

        when (state) {
            State.REVEAL -> if (proposition.reveal()) state = State.GUESS
            State.ANIMATE_GUESS -> {
                if (proposition.update()) {
                    state = if (proposition.over) State.HIDE else State.GUESS
                }
            }
            State.HIDE -> {
                if (proposition.hide()) {

                }
            }
            else -> Unit
        }

        batch.use {
            it.draw(background, 0f, 0f)
            GUI.draw(guesses, time, batch)
            proposition.draw(currentGuess, TILE * 9, batch)
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

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        camera.update()
    }

    override fun dispose() {
        batch.disposeSafely()
    }

    override fun keyDown(keycode: Int): Boolean = false

    override fun keyUp(keycode: Int): Boolean = false

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false

    override fun scrolled(amountX: Float, amountY: Float): Boolean = false
}