package com.yopox.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.yopox.TILE

private val textures = HashMap<Pair<Char, Color>, Texture>()

data class Letter(val char: Char, var color: Color = Color.WHITE) {
    fun indexes(): Pair<Int, Int> = when (char) {
        in 'a'..'z' -> 28 to char.code - 'a'.code + 1
        in 'A'..'Z' -> 28 to char.code - 'A'.code + 1
        in '0'..'9' -> 27 to 16 + char.code - '0'.code
        '!' -> 27 to 1
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
        'à', 'À' -> 29 to 20
        'â', 'Â' -> 29 to 22
        'è', 'È' -> 29 to 28
        'é', 'É' -> 29 to 29
        'ê', 'Ê' -> 29 to 30
        'ë', 'Ë' -> 29 to 31
        'ô', 'Ô' -> 30 to 8
        'ù', 'Ù' -> 30 to 12
        '?' -> 31 to 27
        else -> 0 to 0
    }

    fun texture(): Texture {
        textures[char to color]?.let { return it }

        val i = indexes()
        val texture = mrmoRegions[i.first][i.second]
        texture.texture.textureData.prepare()
        val pixmap = texture.texture.textureData.consumePixmap()
        val newPixmap = Pixmap(8, 8, Pixmap.Format.RGBA8888)

        for (y in 0 until pixmap.height)
            for (x in 0 until pixmap.width)
                if (pixmap.getPixel(
                        x + i.second * TILE.toInt(),
                        y + i.first * TILE.toInt()
                    ) == -1) {
                    newPixmap.setColor(color)
                    newPixmap.drawRectangle(x, y, 1, 1)
                }

        Texture(newPixmap).let {
            textures[char to color] = it
            return it
        }
    }
}

fun List<Letter>.string(): String {
    return map { it.char.toString() }.reduce { s1, s2 -> s1 + s2 }
}
