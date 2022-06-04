package com.yopox

import com.badlogic.gdx.graphics.Texture
import com.yopox.ui.draw
import com.yopox.ui.toLetterList
import ktx.graphics.use
import ktx.math.vec2

class TitleScreen : AbstractScreen() {
    private val background = Texture("title.png")
    private val classique = "Partie classique".toLetterList()
    private val seed = "Partie avec seed".toLetterList()

    override fun render(delta: Float) {
        super.render(delta)

        batch.use {
            it.draw(background, 0f, 0f)
            classique.draw(vec2(TILE * 15, TILE * 12), batch)
            seed.draw(vec2(TILE * 15, TILE * 10), batch)
        }
    }

    override fun keyTyped(character: Char): Boolean {
        Nupes.setScreen<GameScreen>()
        return true
    }
}