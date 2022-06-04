package com.yopox

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.yopox.ui.Color
import com.yopox.ui.GUI
import com.yopox.ui.Letter
import com.yopox.ui.Text
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use

class Nupes : KtxGame<KtxScreen>() {
    override fun create() {
        addScreen(FirstScreen())
        setScreen<FirstScreen>()
    }
}

val TILE = 8f
val WIDTH = TILE * 40
val HEIGHT = TILE * 28

class FirstScreen : KtxScreen {
    private val batch = SpriteBatch()
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(WIDTH, HEIGHT, camera)

    override fun render(delta: Float) {
        batch.projectionMatrix = camera.combined
        clearScreen(Color.BLUE.r, Color.BLUE.g, Color.BLUE.b)
        batch.use {
            GUI.draw(batch)
            Text.draw("Créer un ministère de la\n---------- -----------".map { Letter(it) }, HEIGHT / 2, batch)
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        camera.update()
    }

    override fun dispose() {
        batch.disposeSafely()
    }
}
