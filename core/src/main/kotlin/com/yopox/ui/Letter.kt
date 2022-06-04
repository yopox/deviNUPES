package com.yopox.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.yopox.TILE

private val textures = HashMap<Triple<Pair<Int, Int>, Color, Color>, Texture>()

const val VALID_CHARS= "abcdefghijklmnopqrstuvwxyzàâçèéêëôùû0123456789"
const val GIVEN_CHARS = ":;,-/\"()& "

data class Letter(var char: Char, var color: Color = Colors.WHITE, var background: Color = Colors.BLUE) {
    private fun indexes(): Pair<Int, Int> = when (char) {
        in 'a'..'z' -> 28 to char.code - 'a'.code + 1
        in '0'..'9' -> 27 to 16 + char.code - '0'.code
        '!' -> 27 to 1
        '?' -> 27 to 31
        '"' -> 27 to 2
        '&' -> 27 to 6
        '\'' -> 27 to 7
        '(' -> 27 to 8
        ')' -> 27 to 9
        ',' -> 27 to 12
        '-' -> 27 to 13
        '.' -> 27 to 14
        '/' -> 27 to 15
        ':' -> 27 to 26
        ';' -> 27 to 27
        '~' -> 16 to 24
        'à' -> 29 to 20
        'â' -> 29 to 22
        'ç' -> 29 to 27
        'è' -> 29 to 28
        'é' -> 29 to 29
        'ê' -> 29 to 30
        'ë' -> 29 to 31
        'ô' -> 30 to 8
        'ù' -> 30 to 12
        'û' -> 30 to 14
        else -> 0 to 0
    }

    fun texture(): Texture {
        val i = indexes()
        val triple = Triple(i, color, background)
        textures[triple]?.let { return it }

        val texture = mrmoRegions[i.first][i.second]
        texture.texture.textureData.prepare()
        val pixmap = texture.texture.textureData.consumePixmap()
        val newPixmap = Pixmap(8, 8, Pixmap.Format.RGBA8888)

        for (y in 0 until pixmap.height)
            for (x in 0 until pixmap.width) {
                val color = when (pixmap.getPixel(x + i.second * TILE.toInt(), y + i.first * TILE.toInt())) {
                    -1 -> color
                    else -> background
                }
                newPixmap.setColor(color)
                newPixmap.drawRectangle(x, y, 1, 1)
            }

        Texture(newPixmap).let {
            textures[triple] = it
            return it
        }
    }
}

fun List<Letter>.string(): String {
    return map { it.char.toString() }.reduceOrNull { s1, s2 -> s1 + s2 } ?: ""
}

val List<Letter>.height: Float
    get() = (string().split("\n").size * 2 - 1) * TILE
