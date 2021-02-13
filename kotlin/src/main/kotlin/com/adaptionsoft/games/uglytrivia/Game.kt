package com.adaptionsoft.games.uglytrivia

import java.util.*

class Game {
    private val players = LinkedList<Player>()

    private val popQuestions = LinkedList<Any>()
    private val scienceQuestions = LinkedList<Any>()
    private val sportsQuestions = LinkedList<Any>()
    private val rockQuestions = LinkedList<Any>()

    private var currentPlayerIndex = 0
    private var isGettingOutOfPenaltyBox: Boolean = false

    init {
        for (i in 0..49) {
            popQuestions.addLast("Pop Question $i")
            scienceQuestions.addLast("Science Question $i")
            sportsQuestions.addLast("Sports Question $i")
            rockQuestions.addLast(createRockQuestion(i))
        }
    }

    fun createRockQuestion(index: Int) = "Rock Question $index"

    fun isPlayable() = howManyPlayers() >= 2

    fun add(playerName: String) {
        players.add(Player(playerName))
        println("They are player number " + players.size)
    }

    fun howManyPlayers() = players.size

    fun roll(roll: Int) {
        currentPlayer().announceCurrent()
        println("They have rolled a $roll")

        if (currentPlayer().inPenaltyBox) {
            if (roll % 2 == 0) {
                println("${currentPlayer().name} is not getting out of the penalty box")
                isGettingOutOfPenaltyBox = false
                return
            }
            isGettingOutOfPenaltyBox = true
            println("${currentPlayer().name} is getting out of the penalty box")
        }
        currentPlayer().move(roll)
        askQuestion(currentCategory(currentPlayer().place))
    }

    private fun askQuestion(category: String) {
        when(category) {
            "Pop" -> popQuestions
            "Science" -> scienceQuestions
            "Sports" -> sportsQuestions
            "Rock" -> rockQuestions
            else -> throw IllegalArgumentException("Unknown category: $category")
        }.apply { println(removeFirst()) }
    }

    private fun currentCategory(place: Int) =
        when (place) {
            0, 4, 8 -> "Pop"
            1, 5, 9 -> "Science"
            2, 6, 10 -> "Sports"
            else -> "Rock"
        }.also { println("The category is $it") }

    fun wasCorrectlyAnswered(): Boolean {
        if (currentPlayer().inPenaltyBox && !isGettingOutOfPenaltyBox) {
            nextPlayer()
            return true
        }

        println("Answer was correct!!!!")
        currentPlayer().addCoin()

        val noWinnerYet = !currentPlayer().playerWon()
        nextPlayer()
        return noWinnerYet
    }

    fun wrongAnswer(): Boolean {
        println("Question was incorrectly answered")
        println("${currentPlayer().name} was sent to the penalty box")
        currentPlayer().inPenaltyBox = true

        nextPlayer()
        return true
    }

    private fun currentPlayer() = players[currentPlayerIndex]

    private fun nextPlayer() {
        currentPlayerIndex++
        if (currentPlayerIndex == players.size) currentPlayerIndex = 0
    }
}

private class Player(val name: String) {
    init {
        println("$name was added")
    }

    fun announceCurrent() {
        println("$name is the current player")
    }

    var place: Int = 0
        private set

    var purse: Int = 0
        private set

    var inPenaltyBox = false

    fun move(roll: Int) {
        place += roll
        if (place > 11) place -= 12
        println("$name's new location is $place")
    }

    fun addCoin() {
        purse++
        println("$name now has $purse Gold Coins.")
    }

    fun playerWon() = purse == 6
}