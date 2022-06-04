package com.yopox

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.yopox.ui.mrmoRegions
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

class FirstScreen : KtxScreen {
    private val batch = SpriteBatch()
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(8f * 32, 8f * 20, camera)

    override fun render(delta: Float) {
        batch.projectionMatrix = camera.combined
        clearScreen(0f, 0f, 0f)
        batch.use {
            val offset = 30f
            it.draw(mrmoRegions[28][14], offset + 0 * 8f, 20f)
            it.draw(mrmoRegions[28][21], offset + 1 * 8f, 20f)
            it.draw(mrmoRegions[28][16], offset + 2 * 8f, 20f)
            it.draw(mrmoRegions[28][5], offset + 3 * 8f, 20f)
            it.draw(mrmoRegions[28][19], offset + 4 * 8f, 20f)
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
