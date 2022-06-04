package com.yopox.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.yopox.HEIGHT
import com.yopox.TILE
import ktx.math.vec2
import java.text.SimpleDateFormat
import java.util.*

object GUI {
    private val alphabet = "abcdefghijklmnopqrsuvwxyz".map { Letter(it) }
    private val numbers = "0123456789".map { Letter(it) }
    private val dateFormatter = SimpleDateFormat("mm:ss")

    fun draw(guesses: Int, time: Date, batch: SpriteBatch) {
        alphabet.draw(vec2(TILE * 2, HEIGHT - TILE * 3), batch)
        numbers.draw(vec2(TILE * 2, HEIGHT - TILE * 4), batch)

        val timer = dateFormatter.format(Date(Date().time - time.time))
        timer.map { Letter(it) }.draw(vec2(TILE * 33, HEIGHT - TILE * 5), batch)
        guesses.toString().map { Letter(it) }.draw(vec2(TILE * 33, HEIGHT - TILE * 3), batch)
    }

    fun update(char: Char, color: Color) {
        when (char) {
            in 'a'..'z' -> alphabet.firstOrNull { it.char == char }?.color = color
            in '0'..'9' -> numbers.firstOrNull { it.char == char }?.color = color
        }
    }
}