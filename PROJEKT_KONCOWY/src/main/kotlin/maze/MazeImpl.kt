package maze

import base.Board
import base.Game
import utils.ANSIUtils
import kotlin.random.Random

class MazeImpl(size: Int) : Maze {

    companion object {
        const val OBSTACLE = '█'
        const val PLAYER = '@'
        const val START = 'S'
        const val FINISH = 'E'
        const val CORRIDOR = '_'
    }

    // board dimension + walls on every side
    private val actualSize = size + 2

    override var numberOfMoves: Int = 0
    override val board: Array<Array<Char>> = Array(actualSize) { Array(actualSize) { CORRIDOR } }
    override val obstacles: MutableList<Board.Location> = mutableListOf()
    override val exitLocation = Board.Location(0, Random.nextInt(1, actualSize - 2))
    override var startLocation: Board.Location = Board.Location(actualSize - 1, Random.nextInt(1, actualSize - 2))
    override var currentLocation: Board.Location = startLocation

    init {
        generateWalls()
        generateOtherObstacles()
        updateBoard()
    }

    override fun generateWalls() {
        onEachTile { location ->
            if (location.row == 0 || location.column == 0 || location.row == actualSize - 1 || location.column == actualSize - 1)
                obstacles.add(location)
        }
    }

    private fun generateOtherObstacles() {
        for (i in 2 until (actualSize - 2) step 2) {
            for (j in 1 until actualSize - 1) {
                if (Random.nextInt(100) > 20)
                    obstacles.add(Board.Location(i, j))
            }
            // if whole row blocked, unblock random corridor
            if (obstacles.count { it.row == i } == actualSize) {
                val randomColumn = Random.nextInt(1, actualSize - 2)
                obstacles.removeIf { it.row == i && it.column == randomColumn }
            }
        }
    }

    private fun updateBoard() {
        onEachTile {location ->
            if (location == startLocation) {
                board[location.row][location.column] = START
            } else if (location == exitLocation) {
                board[location.row][location.column] = FINISH
            } else if (obstacles.contains(location)) {
                board[location.row][location.column] = OBSTACLE
            } else {
                board[location.row][location.column] = CORRIDOR
            }
        }
    }

    private fun Board.Location.notInBounds(): Boolean =
        this.column < 0 || this.row < 0 || this.column >= actualSize || this.row >= actualSize

    override fun checkState(): Game.Result =
        when {
            currentLocation == exitLocation -> Game.Result.Win(" And in just $numberOfMoves steps!")
            currentLocation == startLocation -> Game.Result.Ongoing
            obstacles.contains(currentLocation) -> Game.Result.Lose("You've hit obstacle")
            currentLocation.notInBounds() -> Game.Result.Lose("You've stepped outside of map")
            else -> Game.Result.Ongoing
        }

    override fun showCurrentBoardState() {
        val boardRepresentation = StringBuffer()
        var currentRow = 0

        onEachTile { location ->
            if (location.row != currentRow) {
                boardRepresentation.append("\n")
                currentRow = location.row
            }

            val tile = when {
                currentLocation == location -> {
                    if (currentLocation == exitLocation)
                        "${ANSIUtils.COLOR_GREEN}$PLAYER${ANSIUtils.COLOR_RESET}"
                    else if (obstacles.contains(currentLocation) && currentLocation != startLocation)
                        "${ANSIUtils.COLOR_RED}$PLAYER${ANSIUtils.COLOR_RESET}"
                    else
                        "${ANSIUtils.COLOR_PURPLE}$PLAYER${ANSIUtils.COLOR_RESET}"
                }
                exitLocation == location -> "${ANSIUtils.COLOR_BLUE}$FINISH${ANSIUtils.COLOR_RESET}"
                startLocation == location -> "${ANSIUtils.COLOR_BLUE}$START${ANSIUtils.COLOR_RESET}"
                obstacles.contains(location) -> {
                    "${ANSIUtils.COLOR_YELLOW}$OBSTACLE${ANSIUtils.COLOR_RESET}"
                }
                else -> "${board[location.row][location.column]}"
            }

            boardRepresentation.append("$tile  ")
        }

        println(boardRepresentation)
    }

    private fun onEachTile(doOnEach: (Board.Location) -> Unit) {
        for (i in (0 until actualSize)) {
            for (j in (0 until actualSize)) {
                doOnEach.invoke(Board.Location(i, j))
            }
        }
    }

    private fun incrementMoveCount() {
        numberOfMoves++
    }

    override fun moveLeft() {
        incrementMoveCount()
        currentLocation = currentLocation.copy(column = currentLocation.column - 1)
    }

    override fun moveRight() {
        incrementMoveCount()
        currentLocation = currentLocation.copy(column = currentLocation.column + 1)
    }

    override fun moveUp() {
        incrementMoveCount()
        currentLocation = currentLocation.copy(row = currentLocation.row - 1)
    }

    override fun moveDown() {
        incrementMoveCount()
        currentLocation = currentLocation.copy(row = currentLocation.row + 1)
    }
}