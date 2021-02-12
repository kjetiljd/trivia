package com.adaptionsoft.games.uglytrivia

import java.util.*

class Game {
    private val players = ArrayList<Player>()
    private val purses = IntArray(6)
    private val inPenaltyBox = BooleanArray(6)

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
        val newPlayer = Player(playerName)
        players.add(newPlayer)
        purses[howManyPlayers()] = 0
        inPenaltyBox[howManyPlayers()] = false

        println(newPlayer.name + " was added")
        println("They are player number " + players.size)
        return true
    }

    fun howManyPlayers() = players.size

    fun roll(roll: Int) {
        println(players[currentPlayer].name + " is the current player")
        println("They have rolled a " + roll)

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true

                println(players[currentPlayer].name + " is getting out of the penalty box")
                players[currentPlayer].place = players[currentPlayer].place + roll
                if (players[currentPlayer].place > 11) players[currentPlayer].place = players[currentPlayer].place - 12

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
            players[currentPlayer].place = players[currentPlayer].place + roll
            if (players[currentPlayer].place > 11) players[currentPlayer].place = players[currentPlayer].place - 12

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
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                println("Answer was correct!!!!")
                purses[currentPlayer]++
                println(players[currentPlayer].name
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.")

                val winner = didPlayerWin()
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
            purses[currentPlayer]++
            println(players[currentPlayer].name
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.")

            val winner = didPlayerWin()
            currentPlayer++
            if (currentPlayer == players.size) currentPlayer = 0
            return winner
        }
    }

    fun wrongAnswer(): Boolean {
        println("Question was incorrectly answered")
        println(players[currentPlayer].name + " was sent to the penalty box")
        inPenaltyBox[currentPlayer] = true

        currentPlayer++
        if (currentPlayer == players.size) currentPlayer = 0
        return true
    }

    private fun didPlayerWin() = purses[currentPlayer] != 6
}

private class Player(val name: String) {
    var place: Int = 0
}