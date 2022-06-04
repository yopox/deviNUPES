package com.yopox.ui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.yopox.TILE
import com.yopox.WIDTH
import ktx.math.plus
import ktx.math.vec2

val mrmoTexture = Texture("MRMOTEXT.png")
val mrmoRegions = TextureRegion.split(mrmoTexture, TILE.toInt(), TILE.toInt())

object Text {

    fun draw(text: List<Letter>, posY: Float, batch: SpriteBatch) {
        val offset = Vector2()
        val len = text.string().split("\n").map { it.length }
        val xOffsets = len.map { (len.maxOf { i -> i } - it) / 2 * TILE }.toMutableList()
        val centerOffset = (WIDTH - (len.maxOf { it } * TILE)) / 2
        val ySize = (2 * len.size - 1) * TILE

        drawStep(text, offset, xOffsets, vec2(centerOffset, posY + ySize / 2 - TILE), batch)
    }

    fun draw(text: List<Letter>, pos: Vector2, batch: SpriteBatch) {
        val offset = Vector2()
        val len = text.string().split("\n").map { it.length }
        val xOffsets = len.map { (len.maxOf { i -> i } - it) / 2 * TILE }.toMutableList()

        drawStep(text, offset, xOffsets, pos, batch)
    }

    private fun drawStep(
        text: List<Letter>,
        offset: Vector2,
        xOffsets: MutableList<Float>,
        pos: Vector2,
        batch: SpriteBatch
    ) {
        for (c in text) {
            if (c.char == '\n') {
                offset.x = 0f
                offset.y -= 2 * TILE
                xOffsets.removeAt(0)
            } else {
                draw(c, pos + offset + vec2(xOffsets.first(), 0f), batch)
                offset.x += TILE
            }
        }
    }

    fun draw(letter: Letter, pos: Vector2, batch: SpriteBatch) {
        val indexes = letter.indexes()
        batch.draw(mrmoRegions[indexes.first][indexes.second], pos.x, pos.y)
    }

}