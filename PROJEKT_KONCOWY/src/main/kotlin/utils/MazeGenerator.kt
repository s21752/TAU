package utils

import maze.Maze
import maze.MazeImpl

class MazeGenerator {

    companion object {
        const val MAXIMUM_SIZE = 40
        const val MIN_SIZE = 5
        const val DEFAULT_SIZE = 13

        fun generateMaze(size: Int): Maze {
            if (size < MIN_SIZE)
                throw MazeGeneratorException.SizeParameterTooSmall
            if (size > MAXIMUM_SIZE)
                throw MazeGeneratorException.MaximumSizeExceeded

            return MazeImpl(size)
        }
    }
}

sealed class MazeGeneratorException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    object MaximumSizeExceeded : MazeGeneratorException()
    object SizeParameterTooSmall : MazeGeneratorException()
}