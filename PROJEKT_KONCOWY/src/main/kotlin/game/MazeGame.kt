package game

import base.BaseGame
import base.Game
import maze.Maze
import utils.ANSIUtils
import utils.MazeGenerator
import utils.MazeGeneratorException
import java.io.PrintStream
import java.lang.NumberFormatException

class MazeGame(): BaseGame<Maze>() {

    constructor(preferredOutput: PrintStream): this() {
        this.output = preferredOutput
    }

    var output: PrintStream? = null
    private fun printLnToOutput(text: String) {
        output?.println(text)
    }

    private var isGameStarted = false

    override fun prepareBoard() {
        while (board == null) {
            printLnToOutput("Provide dimension of maze you want to resolve, integer from ${MazeGenerator.MIN_SIZE} upto ${MazeGenerator.MAXIMUM_SIZE}")

            val input = readLine()

            try {
                val providedDimension = Integer.parseInt(input)

                val createdMaze = MazeGenerator.generateMaze(providedDimension)

                board = createdMaze

                printLnToOutput("Maze of size [$providedDimension] initialised.")
            } catch (ex: Exception) {
                val errorMessage = when (ex) {
                    is NumberFormatException -> "Wrong number format"
                    is MazeGeneratorException.MaximumSizeExceeded -> "Size exceeds maximum maze size [${MazeGenerator.MAXIMUM_SIZE}]"
                    is MazeGeneratorException.SizeParameterTooSmall -> "Size smaller than minimum [${MazeGenerator.MIN_SIZE}]"
                    else -> "Unknown error"
                }

                printLnToOutput("Incorrect input ($errorMessage), try again.")
            }
        }
    }

    override fun showInstruction() {
        printLnToOutput("Rules:")
        printLnToOutput("You are starting at randomly chosen point[S] at the bottom of the board")
        printLnToOutput("To win a game you have to reach maze exit[E] at the top of the board")
        printLnToOutput("Use letters to move through the maze:")
        printLnToOutput("To move right, write [R]")
        printLnToOutput("To move left, write [L]")
        printLnToOutput("To move up, write [U]")
        printLnToOutput("To move down, write [D]")
        printLnToOutput("Write 'Q' to end the game at any point")
    }

    override fun awaitGameResult(): Game.Result {
        super.awaitGameResult()
        return board?.let { currentBoard ->
            showInstruction()

            isGameStarted = true

            while (isGameStarted) {
                currentBoard.showCurrentBoardState()
                printLnToOutput("Do your move: up[U], down[D], left[L], right[R]:")
                when(readLine()) {
                    "U", "u" -> {
                        printLnToOutput("Moved up")
                        currentBoard.moveUp()
                    }
                    "D", "d" -> {
                        printLnToOutput("Moved down")
                        currentBoard.moveDown()
                    }
                    "L", "l" -> {
                        printLnToOutput("Moved left")
                        currentBoard.moveLeft()
                    }
                    "R", "r" -> {
                        printLnToOutput("Moved right")
                        currentBoard.moveRight()
                    }
                    "Q", "q" -> {
                        printLnToOutput("${ANSIUtils.COLOR_PURPLE}(ノಠ益ಠ)ノ彡┻━┻${ANSIUtils.COLOR_RESET}")
                        isGameStarted = false
                    }
                    else -> printLnToOutput("Command not supported")
                }

                val stateAfterMove = currentBoard.checkState()
                if (stateAfterMove != Game.Result.Ongoing) {
                    currentBoard.showCurrentBoardState()
                    result = stateAfterMove
                    isGameStarted = false
                }
            }

            return result
        } ?: Game.Result.NotFinished
    }
}