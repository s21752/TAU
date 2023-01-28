package maze

import base.Board
import base.Game

interface Maze : Board {
    var currentLocation: Board.Location
    val exitLocation: Board.Location
    val startLocation: Board.Location

    val obstacles: MutableList<Board.Location>
    var numberOfMoves: Int

    fun generateWalls()

    fun checkState() : Game.Result

    fun moveLeft()
    fun moveRight()
    fun moveUp()
    fun moveDown()

    sealed class Result()
}