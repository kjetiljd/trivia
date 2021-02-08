package com.adaptionsoft.games.trivia

import com.adaptionsoft.games.trivia.runner.main
import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import org.approvaltests.Approvals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.stream.LongStream.range


class GameRunnerTest {

    @Test
    fun `Game runner output is captured`() {
        val actual = tapSystemOut {
            main(arrayOf())
        }
        assertTrue(actual.isNotBlank())
    }

    @Test
    fun `Game runner output is as approved`() {
        val actual = tapSystemOut {
            val seed = 0L.toString()
            main(arrayOf(seed))
        }
        Approvals.verify(actual)
    }

    @Test
    fun `Ugly and new implementation report same result`() {
        val seed = 0L.toString()
        val uglyTrivia = tapSystemOut {
            com.adaptionsoft.games.uglytrivia.runner.main(arrayOf(seed))
        }
        val trivia = tapSystemOut {
            main(arrayOf(seed))
        }
        assertEquals(uglyTrivia, trivia)
    }

    @Test
    fun `Ugly and new implementation report same result for many different seeds`() {
        for (seed in 1L..1000L) {
            val uglyTrivia = tapSystemOut {
                com.adaptionsoft.games.uglytrivia.runner.main(arrayOf(seed.toString()))
            }
            val trivia = tapSystemOut {
                main(arrayOf(seed.toString()))
            }
            assertEquals(uglyTrivia, trivia, "Results differ when seed is $seed")
        }
    }
}
