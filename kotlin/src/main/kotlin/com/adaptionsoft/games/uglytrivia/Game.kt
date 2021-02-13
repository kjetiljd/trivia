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
        println("${currentPlayer().name} is the current player")
        println("They have rolled a $roll")

        if (currentPlayer().inPenaltyBox) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true

                println("${currentPlayer().name} is getting out of the penalty box")
                currentPlayer().move(roll)

                println("${currentPlayer().name}'s new location is ${currentPlayer().place}")
                println("The category is ${currentCategory(currentPlayer().place)}")
                askQuestion(currentCategory(currentPlayer().place))
            } else {
                println("${currentPlayer().name} is not getting out of the penalty box")
                isGettingOutOfPenaltyBox = false
            }
        } else {
            currentPlayer().move(roll)

            println("${currentPlayer().name}'s new location is ${currentPlayer().place}")
            println("The category is ${currentCategory(currentPlayer().place)}")
            askQuestion(currentCategory(currentPlayer().place))
        }

    }

    private fun askQuestion(category: String) {
        if (category === "Pop")
            println(popQuestions.removeFirst())
        if (category === "Science")
            println(scienceQuestions.removeFirst())
        if (category === "Sports")
            println(sportsQuestions.removeFirst())
        if (category === "Rock")
            println(rockQuestions.removeFirst())
    }

    private fun currentCategory(place: Int): String {
        if (place == 0) return "Pop"
        if (place == 4) return "Pop"
        if (place == 8) return "Pop"
        if (place == 1) return "Science"
        if (place == 5) return "Science"
        if (place == 9) return "Science"
        if (place == 2) return "Sports"
        if (place == 6) return "Sports"
        if (place == 10) return "Sports"
        return "Rock"
    }

    fun wasCorrectlyAnswered(): Boolean {
        if (currentPlayer().inPenaltyBox) {
            if (isGettingOutOfPenaltyBox) {
                println("Answer was correct!!!!")
                currentPlayer().addCoin()

                val noWinnerYet = !currentPlayer().playerWon()
                nextPlayer()
                return noWinnerYet
            } else {
                nextPlayer()
                return true
            }
        } else {
            println("Answer was corrent!!!!")
            currentPlayer().addCoin()

            val noWinnerYet = !currentPlayer().playerWon()
            nextPlayer()
            return noWinnerYet
        }
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
    var place: Int = 0
        private set

    var purse: Int = 0
        private set

    var inPenaltyBox = false

    fun move(roll: Int) {
        place += roll
        if (place > 11) place -= 12
    }

    fun addCoin() {
        purse++
        println("$name now has $purse Gold Coins.")
    }

    fun playerWon() = purse == 6
}