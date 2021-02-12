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
        if (howManyPlayers() == 5) throw IndexOutOfBoundsException()
        val newPlayer = Player(playerName)
        players.add(newPlayer)

        println(newPlayer.name + " was added")
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
                println("The category is " + currentCategory())
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
            println("The category is " + currentCategory())
            askQuestion()
        }

    }

    private fun askQuestion() {
        if (currentCategory() === "Pop")
            println(popQuestions.removeFirst())
        if (currentCategory() === "Science")
            println(scienceQuestions.removeFirst())
        if (currentCategory() === "Sports")
            println(sportsQuestions.removeFirst())
        if (currentCategory() === "Rock")
            println(rockQuestions.removeFirst())
    }

    private fun currentCategory(): String {
        if (players[currentPlayer].place == 0) return "Pop"
        if (players[currentPlayer].place == 4) return "Pop"
        if (players[currentPlayer].place == 8) return "Pop"
        if (players[currentPlayer].place == 1) return "Science"
        if (players[currentPlayer].place == 5) return "Science"
        if (players[currentPlayer].place == 9) return "Science"
        if (players[currentPlayer].place == 2) return "Sports"
        if (players[currentPlayer].place == 6) return "Sports"
        if (players[currentPlayer].place == 10) return "Sports"
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