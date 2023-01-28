package base

import java.io.PrintStream

interface Game<T : Board> {
    var board: T?
    var result: Result

    fun prepareBoard()
    fun awaitGameResult() : Result
    fun showInstruction()

    sealed class Result {
        class Win(val additionalMessage: String? = null) : Result()
        class Lose(val reason: String) : Result()
        object Ongoing : Result()
        object NotFinished : Result()
        object BoardNotReady : Result()
    }
}

sealed class GameException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    object GameStartedWithUninitialisedBoard : GameException()
}