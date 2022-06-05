package com.yopox

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.yopox.ui.draw
import com.yopox.ui.toLetterList
import ktx.graphics.use
import ktx.math.vec2

private const val OPTIONS = 3

class TitleScreen : AbstractScreen() {
    private val background = Texture("title.png")
    private val classique = "Partie classique".toLetterList()
    private val seed = "Partie avec seed".toLetterList()
    private val option = "Troisième option".toLetterList()
    private val arrow = ">".toLetterList()

    private var selected = 0

    override fun render(delta: Float) {
        super.render(delta)

        batch.use {
            it.draw(background, 0f, 0f)
            classique.draw(vec2(TILE * 15, TILE * 12), batch)
            seed.draw(vec2(TILE * 15, TILE * 10), batch)
            option.draw(vec2(TILE * 15, TILE * 8), batch)
            arrow.draw(vec2(TILE * 13, TILE * 12 - 2 * TILE * selected), batch)
        }
    }

    override fun keyUp(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.UP -> selected = (selected - 1 + OPTIONS) % OPTIONS
            Input.Keys.DOWN -> selected = (selected + 1 + OPTIONS) % OPTIONS
            else -> Nupes.setScreen<GameScreen>()
        }
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }
}