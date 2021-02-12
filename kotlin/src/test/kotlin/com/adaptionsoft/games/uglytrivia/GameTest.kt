package com.adaptionsoft.games.uglytrivia

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GameTest {

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

        assertEquals("Science", categoryFrom(tapSystemOut { game.roll(1) }))
        assertEquals("Sports", categoryFrom(tapSystemOut { game.roll(1) }))
        assertEquals("Rock", categoryFrom(tapSystemOut { game.roll(1) }))
        assertEquals("Pop", categoryFrom(tapSystemOut { game.roll(1) }))
        assertEquals("Science", categoryFrom(tapSystemOut { game.roll(1) }))
        assertEquals("Sports", categoryFrom(tapSystemOut { game.roll(1) }))
        assertEquals("Rock", categoryFrom(tapSystemOut { game.roll(1) }))
        assertEquals("Pop", categoryFrom(tapSystemOut { game.roll(1) }))
        assertEquals("Science", categoryFrom(tapSystemOut { game.roll(1) }))
        assertEquals("Sports", categoryFrom(tapSystemOut { game.roll(1) }))
        assertEquals("Rock", categoryFrom(tapSystemOut { game.roll(1) }))
        assertEquals("Pop", categoryFrom(tapSystemOut { game.roll(1) }))
    }

    @Test
    fun `player gets out of penalty box if a correct answer`() {
        val game = Game()
        game.add("Anita")
        game.wrongAnswer()
        game.roll(3)
        val actual = tapSystemOut {
            game.wasCorrectlyAnswered()
        }

        val expected = """
            Answer was correct!!!!
            Anita now has 1 Gold Coins.
            
        """.trimIndent()
        assertEquals(expected, actual)
    }

    @Test
    fun `player does not get out of penalty box after even roll even if a correct answer`() {
        val game = Game()
        game.add("Anita")
        game.wrongAnswer()
        game.roll(6)
        val actual = tapSystemOut {
            game.wasCorrectlyAnswered()
        }

        val expected = ""
        assertEquals(expected, actual)
    }

    @Test
    fun `check createRockQuestion`() {
        assertEquals("Rock Question 15", Game().createRockQuestion(15))
    }

    private fun categoryFrom(output: String) = output
        .split("\n")
        .first { it.startsWith("The category is ") }
        .split(" ")
        .last()
}