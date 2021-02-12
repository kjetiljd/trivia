package com.adaptionsoft.games.uglytrivia

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GameTest {

    @Test
    fun `can instantiate game`() {
        Game()
    }

    @Test
    fun `isPlayable needs at least two players`() {
        val game = Game()
        assertFalse(game.isPlayable())

        game.add("Fred")
        assertFalse(game.isPlayable())

        game.add("Kenny")
        assertTrue(game.isPlayable())
    }

    @Test
    fun `add announces new player`() {
        val game = Game()

        val actual = tapSystemOut {
            game.add("Anita")
        }

        val expected = """
            Anita was added
            They are player number 1
            
        """.trimIndent()

        assertEquals(expected, actual)
    }

    @Test
    fun `roll announces player, roll and question`() {
        val game = Game()
        game.add("Anita")
        game.add("Ben")

        val actual = tapSystemOut {
            game.roll(1)
        }

        val expected = """
            Anita is the current player
            They have rolled a 1
            Anita's new location is 1
            The category is Science
            Science Question 0
            
        """.trimIndent()

        assertEquals(expected, actual)
    }
}