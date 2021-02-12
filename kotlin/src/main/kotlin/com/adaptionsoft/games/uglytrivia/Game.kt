package com.adaptionsoft.games.uglytrivia

import java.util.*

class Game {
    private val players = ArrayList<Player>()

    private val popQuestions = LinkedList<Any>()
    private val scienceQuestions = LinkedList<Any>()
    private val sportsQuestions = LinkedList<Any>()
    private val rockQuestions = LinkedList<Any>()

    private var currentPlayer = 0
    private var isGettingOutOfPenaltyBox: Boolean = false

    init {
        for (i in 0..49) {
            popQuestions.addLast("Pop Question " + i)
            scienceQuestions.addLast("Science Question " + i)
            sportsQuestions.addLast("Sports Question " + i)
            rockQuestions.addLast(createRockQuestion(i))
        }
    }

    fun createRockQuestion(index: Int) = "Rock Question " + index

    fun isPlayable() = howManyPlayers() >= 2

    fun add(playerName: String): Boolean {
        players.add(Player(playerName))
        println("They are player number " + players.size)
        return true
    }

    fun howManyPlayers() = players.size

    fun roll(roll: Int) {
        println(players[currentPlayer].name + " is the current player")
        println("They have rolled a " + roll)

        if (players[currentPlayer].inPenaltyBox) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true

                println(players[currentPlayer].name + " is getting out of the penalty box")
                players[currentPlayer].move(roll)

                println(players[currentPlayer].name
                        + "'s new location is "
                        + players[currentPlayer].place)
                println("The category is " + currentCategory(players[currentPlayer].place))
                askQuestion()
            } else {
                println(players[currentPlayer].name + " is not getting out of the penalty box")
                isGettingOutOfPenaltyBox = false
            }
        } else {
            players[currentPlayer].move(roll)

            println(players[currentPlayer].name
                    + "'s new location is "
                    + players[currentPlayer].place)
            println("The category is " + currentCategory(players[currentPlayer].place))
            askQuestion()
        }

    }

    private fun askQuestion() {
        if (currentCategory(players[currentPlayer].place) === "Pop")
            println(popQuestions.removeFirst())
        if (currentCategory(players[currentPlayer].place) === "Science")
            println(scienceQuestions.removeFirst())
        if (currentCategory(players[currentPlayer].place) === "Sports")
            println(sportsQuestions.removeFirst())
        if (currentCategory(players[currentPlayer].place) === "Rock")
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
        if (players[currentPlayer].inPenaltyBox) {
            if (isGettingOutOfPenaltyBox) {
                println("Answer was correct!!!!")
                players[currentPlayer].addCoin()
                println(
                    players[currentPlayer].name
                            + " now has "
                            + players[currentPlayer].purse
                            + " Gold Coins."
                )

                val winner = !players[currentPlayer].playerWon()
                currentPlayer++
                if (currentPlayer == players.size) currentPlayer = 0
                return winner
            } else {
                currentPlayer++
                if (currentPlayer == players.size) currentPlayer = 0
                return true
            }
        } else {
            println("Answer was corrent!!!!")
            players[currentPlayer].addCoin()
            println(
                players[currentPlayer].name
                        + " now has "
                        + players[currentPlayer].purse
                        + " Gold Coins."
            )

            val winner = !players[currentPlayer].playerWon()
            currentPlayer++
            if (currentPlayer == players.size) currentPlayer = 0
            return winner
        }
    }

    fun wrongAnswer(): Boolean {
        println("Question was incorrectly answered")
        println(players[currentPlayer].name + " was sent to the penalty box")
        players[currentPlayer].inPenaltyBox = true

        currentPlayer++
        if (currentPlayer == players.size) currentPlayer = 0
        return true
    }
}

private class Player(val name: String) {
    init {
        println(name + " was added")
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
    }

    fun playerWon() = purse == 6
}