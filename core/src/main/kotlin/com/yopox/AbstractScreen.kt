package com.yopox

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.yopox.ui.Colors
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely

abstract class AbstractScreen : KtxScreen, InputProcessor {
    internal val batch = SpriteBatch()
    internal val camera = OrthographicCamera()
    internal val viewport = FitViewport(WIDTH, HEIGHT, camera)

    override fun show() {
        Gdx.input.inputProcessor = this
    }

    override fun render(delta: Float) {
        batch.projectionMatrix = camera.combined
        clearScreen(Colors.BLUE.r, Colors.BLUE.g, Colors.BLUE.b)
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