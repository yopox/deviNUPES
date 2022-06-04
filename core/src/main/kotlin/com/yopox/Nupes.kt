package com.yopox

import ktx.app.KtxGame
import ktx.app.KtxScreen

object Nupes : KtxGame<KtxScreen>() {
    override fun create() {
        addScreen(TitleScreen())
        addScreen(GameScreen())
        setScreen<TitleScreen>()
    }
}

val TILE = 8f
val WIDTH = TILE * 40
val HEIGHT = TILE * 22