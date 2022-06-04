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
val mrmoRegions: Array<Array<TextureRegion>> = TextureRegion.split(mrmoTexture, TILE.toInt(), TILE.toInt())

fun List<Letter>.draw(posY: Float, batch: SpriteBatch) {
    val offset = Vector2()
    val len = string().split("\n").map { it.length }
    val xOffsets = len.map { (len.maxOf { i -> i } - it) / 2 * TILE }.toMutableList()
    val centerOffset = (WIDTH - (len.maxOf { it } * TILE)) / 2

    drawStep(this, offset, xOffsets, vec2(centerOffset, posY + height / 2 - TILE), batch)
}

fun List<Letter>.draw(pos: Vector2, batch: SpriteBatch) {
    val offset = Vector2()
    val len = string().split("\n").map { it.length }
    val xOffsets = len.map { (len.maxOf { i -> i } - it) / 2 * TILE }.toMutableList()

    drawStep(this, offset, xOffsets, pos, batch)
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
            val pos1 = pos + offset + vec2(xOffsets.first(), 0f)
            batch.draw(c.texture(), pos1.x, pos1.y)
            offset.x += TILE
        }
    }
}