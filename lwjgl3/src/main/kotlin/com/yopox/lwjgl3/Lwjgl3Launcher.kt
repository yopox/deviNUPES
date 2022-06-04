@file:JvmName("Lwjgl3Launcher")

package com.yopox.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.yopox.Nupes

/** Launches the desktop (LWJGL3) application. */
fun main() {
    Lwjgl3Application(Nupes, Lwjgl3ApplicationConfiguration().apply {
        setTitle("nupes")
        setWindowedMode(1280 / 2, 720 / 2)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
