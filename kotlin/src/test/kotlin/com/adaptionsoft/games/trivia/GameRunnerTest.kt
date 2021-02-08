package com.adaptionsoft.games.trivia

import com.adaptionsoft.games.trivia.runner.main
import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import org.approvaltests.Approvals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


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
            val seed = "0"
            main(arrayOf(seed))
        }
        Approvals.verify(actual)
    }
}
