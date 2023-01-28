import base.Game
import game.MazeGame
import utils.ANSIUtils

class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            println("Do you want to start a game?(Y/N)")
            val startGame = readLine()?.let { it.contains("Y") || it.contains("y") } == true

            if (startGame) {
                val game: Game<*> = MazeGame(System.out)

                game.prepareBoard()

                val gameResult = game.awaitGameResult()

                when (gameResult) {
                    is Game.Result.Lose -> "${ANSIUtils.COLOR_RED}You've lost due to [${gameResult.reason}]${ANSIUtils.COLOR_RESET}"
                    is Game.Result.Win -> "${ANSIUtils.COLOR_GREEN}Congratulations, you've won!${gameResult.additionalMessage ?: ""}${ANSIUtils.COLOR_RESET}"
                    Game.Result.NotFinished -> "Game not finished."
                    Game.Result.BoardNotReady -> "Game board initialisation problems"
                    else -> ""
                }.let { verdict ->
                    println("Your result is: $verdict")
                }
            }

            println("Ok, bye!")
        }
    }
}