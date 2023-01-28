package base

interface Board {
    val board: Array<Array<Char>>

    fun showCurrentBoardState()

    data class Location(val row: Int, val column: Int)
}