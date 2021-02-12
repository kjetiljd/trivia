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

    @Test
    fun `howManyPlayers keeps track of players`() {
        val game = Game()

        game.add("One")
        game.add("Two")
        game.add("Three")
        game.add("Four")
        game.add("Five")

        assertEquals(5, game.howManyPlayers())
    }

    @Test
    fun `game crashes on six players`() {
        val game = Game()

        game.add("One")
        game.add("Two")
        game.add("Three")
        game.add("Four")
        game.add("Five")

        assertThrows(IndexOutOfBoundsException::class.java) {
            game.add("Six")
        }
    }

    @Test
    fun `six correct answers win`() {
        val game = Game()
        game.add("One")

        assertFalse(!game.wasCorrectlyAnswered())
        assertFalse(!game.wasCorrectlyAnswered())
        assertFalse(!game.wasCorrectlyAnswered())
        assertFalse(!game.wasCorrectlyAnswered())
        assertFalse(!game.wasCorrectlyAnswered())
        assertTrue(!game.wasCorrectlyAnswered())
    }

    @Test
    fun `wrong answer puts player in penalty box`() {
        val game = Game()
        game.add("Anita")

        val actual = tapSystemOut {
            game.wrongAnswer()
        }

        val expected = """
            Question was incorrectly answered
            Anita was sent to the penalty box
            
        """.trimIndent()
        assertEquals(expected, actual)
    }

    @Test
    fun `player in penalty box stays there with even roll`() {
        val game = Game()
        game.add("Anita")
        game.wrongAnswer()

        val actual = tapSystemOut {
            game.roll(4)
        }

        val expected = """
             Anita is the current player
             They have rolled a 4
             Anita is not getting out of the penalty box
             
        """.trimIndent()
        assertEquals(expected, actual)
    }

    @Test
    fun `player in penalty box gets out with an odd roll`() {
        val game = Game()
        game.add("Anita")
        game.wrongAnswer()

        val actual = tapSystemOut {
            game.roll(3)
        }

        val expected = """
             Anita is the current player
             They have rolled a 3
             Anita is getting out of the penalty box
             Anita's new location is 3
             The category is Rock
             Rock Question 0
             
        """.trimIndent()
        assertEquals(expected, actual)
    }

    @Test
    fun `each board position has a question type`() {
        val game = Game()
        game.add("Anita")

        val output = tapSystemOut {
            game.roll(1)
        }
        val actual = categoryFrom(output)
        
        assertEquals("Science", actual)
    }

    private fun categoryFrom(output: String) = output
        .split("\n")
        .first { it.startsWith("The category is ") }
        .split(" ")
        .last()
}