package com.yopox.ui

import java.awt.Color

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
}

fun List<Letter>.string(): String {
    return map { it.char.toString() }.reduce { s1, s2 -> s1 + s2 }
}
