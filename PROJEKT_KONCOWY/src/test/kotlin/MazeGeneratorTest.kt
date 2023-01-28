import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import utils.MazeGenerator
import utils.MazeGeneratorException

class MazeGeneratorTest {

    @Test
    fun test_providing_size_bigger_than_max_throws_size_exceeded_exception() {
        assertThrows<MazeGeneratorException.MaximumSizeExceeded> { MazeGenerator.generateMaze(50) }
    }

    @Test
    fun test_providing_size_smaller_than_min_throws_size_parameter_too_small_exception() {
        assertThrows<MazeGeneratorException.SizeParameterTooSmall> { MazeGenerator.generateMaze(3) }
    }

    @Test
    fun test_providing_negative_size_throws_size_parameter_too_small_exception() {
        assertThrows<MazeGeneratorException.SizeParameterTooSmall> { MazeGenerator.generateMaze(-13) }
    }

    @Test
    fun test_maze_generating_works_correctly() {
        val maze = MazeGenerator.generateMaze(14)
        Assert.assertNotNull(maze)
    }
}