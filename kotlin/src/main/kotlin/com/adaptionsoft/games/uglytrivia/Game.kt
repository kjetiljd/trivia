package com.adaptionsoft.games.uglytrivia

import java.util.*

class Game {
    private val players = Players()
    private val questions = Questions()

    private var isGettingOutOfPenaltyBox: Boolean = false

    fun createRockQuestion(index: Int) = questions.createRockQuestion(index)

    fun isPlayable() = howManyPlayers() >= 2

    fun add(playerName: String) {
        players.add(Player(playerName))
    }

    fun howManyPlayers() = players.size

    fun roll(roll: Int) {
        players.announceCurrent()
        println("They have rolled a $roll")

        if (players.current().inPenaltyBox) {
            isGettingOutOfPenaltyBox = roll % 2 != 0
            if (!isGettingOutOfPenaltyBox) {
                println("${players.current().name} is not getting out of the penalty box")
                return
            }
            println("${players.current().name} is getting out of the penalty box")
        }
        players.current().move(roll)
        questions.askQuestion(Board.category(players.current().place))
    }

    fun wasCorrectlyAnswered(): Boolean {
        if (players.current().inPenaltyBox && !isGettingOutOfPenaltyBox) {
            players.next()
            return true
        }

        println("Answer was correct!!!!")
        players.current().addCoin()

        val noWinnerYet = !players.current().playerWon()
        players.next()
        return noWinnerYet
    }

    fun wrongAnswer(): Boolean {
        println("Question was incorrectly answered")
        players.current().sendToPenaltyBox()
        players.next()
        return true
    }
}

private object Board {
    fun category(place: Int) =
        when (place) {
            0, 4, 8 -> "Pop"
            1, 5, 9 -> "Science"
            2, 6, 10 -> "Sports"
            else -> "Rock"
        }.also { println("The category is $it") }
}

private class Players {
    private val players = LinkedList<Player>()
    private var currentPlayerIndex = 0

    val size get() = players.size

    fun add(player: Player) {
        players.add(player)
        println("They are player number " + players.size)
    }

    fun announceCurrent() {
        current().announceCurrent()
    }

    fun current() = players[currentPlayerIndex]

    fun next() {
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

    private var purse: Int = 0

    var inPenaltyBox = false
        private set

    fun sendToPenaltyBox() {
        println("$name was sent to the penalty box")
        inPenaltyBox = true
    }

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


private class Questions {
    private val popQuestions = LinkedList<Any>()
    private val scienceQuestions = LinkedList<Any>()
    private val sportsQuestions = LinkedList<Any>()
    private val rockQuestions = LinkedList<Any>()

    init {
        for (i in 0..49) {
            popQuestions.addLast("Pop Question $i")
            scienceQuestions.addLast("Science Question $i")
            sportsQuestions.addLast("Sports Question $i")
            rockQuestions.addLast(createRockQuestion(i))
        }
    }

    fun createRockQuestion(index: Int) = "Rock Question $index"

    fun askQuestion(category: String) {
        when(category) {
            "Pop" -> popQuestions
            "Science" -> scienceQuestions
            "Sports" -> sportsQuestions
            "Rock" -> rockQuestions
            else -> throw IllegalArgumentException("Unknown category: $category")
        }.apply { println(removeFirst()) }
    }
}
