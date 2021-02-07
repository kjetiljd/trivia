package com.adaptionsoft.games.trivia

import com.adaptionsoft.games.trivia.runner.main
import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class SomeTest {

    @Test
    fun `Can call game runner`() {
        main(arrayOf())
    }

    @Test
    fun `Game runner output is captured`() {
        val actual = tapSystemOut {
            val seed = "0"
            main(arrayOf(seed))
        }
        assertTrue(actual.isNotBlank())
    }
}

val expected =
    """
        Chet was added
        They are player number 1
        Pat was added
        They are player number 2
        Sue was added
        They are player number 3
        Chet is the current player
        They have rolled a 2
        Chet's new location is 2
        The category is Sports
        Sports Question 0
        Answer was corrent!!!!
        Chet now has 1 Gold Coins.
        Pat is the current player
        They have rolled a 3
        Pat's new location is 3
        The category is Rock
        Rock Question 0
        Answer was corrent!!!!
        Pat now has 1 Gold Coins.
        Sue is the current player
        They have rolled a 4
        Sue's new location is 4
        The category is Pop
        Pop Question 0
        Question was incorrectly answered
        Sue was sent to the penalty box
        Chet is the current player
        They have rolled a 4
        Chet's new location is 6
        The category is Sports
        Sports Question 1
        Answer was corrent!!!!
        Chet now has 2 Gold Coins.
        Pat is the current player
        They have rolled a 2
        Pat's new location is 5
        The category is Science
        Science Question 0
        Answer was corrent!!!!
        Pat now has 2 Gold Coins.
        Sue is the current player
        They have rolled a 2
        Sue is not getting out of the penalty box
        Chet is the current player
        They have rolled a 4
        Chet's new location is 10
        The category is Sports
        Sports Question 2
        Answer was corrent!!!!
        Chet now has 3 Gold Coins.
        Pat is the current player
        They have rolled a 5
        Pat's new location is 10
        The category is Sports
        Sports Question 3
        Answer was corrent!!!!
        Pat now has 3 Gold Coins.
        Sue is the current player
        They have rolled a 1
        Sue is getting out of the penalty box
        Sue's new location is 5
        The category is Science
        Science Question 1
        Answer was correct!!!!
        Sue now has 1 Gold Coins.
        Chet is the current player
        They have rolled a 1
        Chet's new location is 11
        The category is Rock
        Rock Question 1
        Answer was corrent!!!!
        Chet now has 4 Gold Coins.
        Pat is the current player
        They have rolled a 4
        Pat's new location is 2
        The category is Sports
        Sports Question 4
        Answer was corrent!!!!
        Pat now has 4 Gold Coins.
        Sue is the current player
        They have rolled a 4
        Sue is not getting out of the penalty box
        Question was incorrectly answered
        Sue was sent to the penalty box
        Chet is the current player
        They have rolled a 3
        Chet's new location is 2
        The category is Sports
        Sports Question 5
        Answer was corrent!!!!
        Chet now has 5 Gold Coins.
        Pat is the current player
        They have rolled a 4
        Pat's new location is 6
        The category is Sports
        Sports Question 6
        Answer was corrent!!!!
        Pat now has 5 Gold Coins.
        Sue is the current player
        They have rolled a 3
        Sue is getting out of the penalty box
        Sue's new location is 8
        The category is Pop
        Pop Question 1
        Answer was correct!!!!
        Sue now has 2 Gold Coins.
        Chet is the current player
        They have rolled a 1
        Chet's new location is 3
        The category is Rock
        Rock Question 2
        Answer was corrent!!!!
        Chet now has 6 Gold Coins.
    """.trimIndent()
