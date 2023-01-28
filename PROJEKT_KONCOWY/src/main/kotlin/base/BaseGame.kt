package base

abstract class BaseGame<T: Board>: Game<T> {
    override var board: T? = null
    override var result: Game.Result = Game.Result.NotFinished

    open override fun awaitGameResult(): Game.Result {
        if (board == null) {
            throw GameException.GameStartedWithUninitialisedBoard
        }

        return result
    }
}