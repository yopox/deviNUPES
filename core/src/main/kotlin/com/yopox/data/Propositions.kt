package com.yopox.data

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.yopox.TILE
import com.yopox.WIDTH
import com.yopox.ui.*
import ktx.math.vec2
import kotlin.math.max

private const val UNKNOWN = '~'

class Proposition(before: String, answer: String, after: String) {
    private val before = before.map { Letter(it.lowercaseChar(), Colors.BLUE, Colors.WHITE) }
    private val guess = answer.map {
        when (it.lowercaseChar()) {
            in GIVEN_CHARS -> Letter(it, Colors.GREEN, Colors.WHITE)
            else -> Letter(UNKNOWN, Colors.BLUE, Colors.WHITE)
        }
    }
    private val after = after.map { Letter(it.lowercaseChar(), Colors.BLUE, Colors.WHITE) }

    private val answer = answer.lowercase()
    private val found = mutableListOf<Int>()
    private val answerOffset: Float = (WIDTH - answer.length * TILE) / 2

    fun draw(currentGuess: String, posY: Float, batch: SpriteBatch) {
        val h1 = before.height
        val h2 = guess.height
        val h3 = after.height

        // Draw proposition
        if (before.any()) before.draw(posY + h1 / 2 + TILE + h2 / 2, batch)
        guess.draw(posY, batch)
        if (after.any()) after.draw(posY - h2 / 2 - TILE - h3 / 2, batch)

        // Draw current guess
        currentGuess.map { Letter(it, Colors.BLUE, Colors.WHITE) }.draw(vec2(answerOffset, posY - TILE / 2), batch)
    }

    fun completeGuess(currentGuess: String, char: Char): String {
        if (char.lowercaseChar() !in VALID_CHARS) return currentGuess
        if (currentGuess.length == answer.length) return currentGuess
        val nextChar = answer[currentGuess.length]
        var newGuess = currentGuess + char
        if (nextChar in GIVEN_CHARS) newGuess = "$currentGuess$nextChar$char"
        return newGuess.substring(0, minOf(newGuess.length, answer.length))
    }

    fun guess(text: String) {
        for (i in text.indices) {
            if (i >= answer.length) return
            val char = sanitize(text[i])
            if (char !in VALID_CHARS) continue
            when (char) {
                sanitize(answer[i]) -> found.add(i)
                !in answer -> {
                    GUI.update(char, Colors.RED)
                    guess[i].apply { this.char = UNKNOWN; color = Colors.BLUE }
                }
                else -> {
                    val sanitizedAnswer = answer.map { sanitize(it) }
                    val nChar = sanitizedAnswer.count { it == char }
                    val rightChars = text.filterIndexed { index, c -> c == char && sanitizedAnswer[index] == c }
                    val alreadyFound = answer.filterIndexed { index, c -> index in found && c == char }
                    val wrongChars = text.filterIndexed { index, c -> index < i && c == char && sanitizedAnswer[index] != char }

                    val color = when {
                        nChar > max(rightChars.length, alreadyFound.length) + wrongChars.length -> Colors.ORANGE
                        else -> Colors.BLUE
                    }
                    guess[i].apply { this.char = text[i]; this.color = color }
                }
            }
            if (i in found) guess[i].apply {
                this.char = answer[i]
                color = Colors.GREEN
            }
        }
    }

    private fun sanitize(c: Char): Char = when (c) {
        in "àâ" -> 'a'
        in "ç" -> 'c'
        in "èéêë" -> 'e'
        in "ô" -> 'o'
        in "ùû" -> 'u'
        else -> c
    }
}

val propositions = listOf(
    Proposition("Inscrire dans la Constitution\nla règle verte, qui impose de", "ne pas prendre plus à la nature", "que ce qu'elle peut reconstituer")
)