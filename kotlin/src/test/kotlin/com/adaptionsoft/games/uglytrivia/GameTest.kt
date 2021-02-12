package com.adaptionsoft.games.uglytrivia

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
}