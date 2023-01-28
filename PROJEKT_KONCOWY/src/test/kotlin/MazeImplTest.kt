import base.Game
import maze.Maze
import maze.MazeImpl
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.InputStream

class MazeImplTest {

    companion object {
        const val TEST_BOARD_SIZE = 13
    }

    lateinit var mazeToTest: Maze

    @Before
    fun prepare() {
        // refresh maze before each test
        mazeToTest = MazeImpl(TEST_BOARD_SIZE)
    }

    @Test
    fun test_initial_game_board_state_equals_ongoing() {
        Assert.assertEquals(Game.Result.Ongoing, mazeToTest.checkState())
    }

    @Test
    fun test_making_move_increments_step_step_counter() {
        val movesBefore = mazeToTest.numberOfMoves
        mazeToTest.moveUp()
        Assert.assertTrue(movesBefore < mazeToTest.numberOfMoves)
    }

    @Test
    fun test_going_outside_board_results_in_lose() {
        // user starts at the bottom of the board, with walls on his left and right, so going down is the only direction he can go outside the bounds
        mazeToTest.moveDown()

        val newState = mazeToTest.checkState()
        Assert.assertTrue(newState is Game.Result.Lose && newState.reason.contains("stepped outside of map"))
    }

    @Test
    fun test_going_into_obstacle_results_in_lose() {
        // user starts at the bottom of the board, with walls on his left and right, so going either side ends in wall
        mazeToTest.moveLeft()

        val newState = mazeToTest.checkState()
        Assert.assertTrue(newState is Game.Result.Lose && newState.reason.contains("You've hit obstacle"))
    }

    @Test
    fun test_game_starts_at_start_position() {
        Assert.assertEquals(mazeToTest.startLocation, mazeToTest.currentLocation)
    }

    @Test
    fun test_moving_out_of_start_position() {
        mazeToTest.moveUp()
        Assert.assertNotEquals(mazeToTest.startLocation, mazeToTest.currentLocation)
    }

    // while moving left, player is moving to the column with index 1 lower than before
    @Test
    fun test_moving_left() {
        val initialLocation = mazeToTest.currentLocation.copy()
        mazeToTest.moveLeft()
        Assert.assertTrue(initialLocation.column - 1 == mazeToTest.currentLocation.column && initialLocation.row == mazeToTest.currentLocation.row )
    }

    // while moving right, player is moving to the column with index 1 greater than before
    @Test
    fun test_moving_right() {
        val initialLocation = mazeToTest.currentLocation.copy()
        mazeToTest.moveRight()
        Assert.assertTrue(initialLocation.column + 1 == mazeToTest.currentLocation.column && initialLocation.row == mazeToTest.currentLocation.row )
    }

    // while moving up, player is moving to the row with index 1 lower than before
    @Test
    fun test_moving_up() {
        val initialLocation = mazeToTest.currentLocation.copy()
        mazeToTest.moveUp()
        Assert.assertTrue(initialLocation.row - 1 == mazeToTest.currentLocation.row && initialLocation.column == mazeToTest.currentLocation.column )
    }

    // while moving down, player is moving to the row with index 1 greater than before
    @Test
    fun test_moving_down() {
        val initialLocation = mazeToTest.currentLocation.copy()
        mazeToTest.moveDown()
        Assert.assertTrue(initialLocation.row + 1 == mazeToTest.currentLocation.row && initialLocation.column == mazeToTest.currentLocation.column )
    }

    @Test
    fun test_moving_up_and_down_ends_in_same_location() {
        val initialLocation = mazeToTest.currentLocation.copy()

        mazeToTest.moveUp()
        mazeToTest.moveDown()

        Assert.assertEquals(initialLocation, mazeToTest.currentLocation)
    }

    @Test
    fun test_start_and_end_location_are_not_in_same_place() {
        Assert.assertNotEquals(mazeToTest.startLocation, mazeToTest.exitLocation)
    }

    @Test
    fun test_player_not_starting_on_end_location() {
        Assert.assertNotEquals(mazeToTest.currentLocation, mazeToTest.exitLocation)
    }

    // actual size of board equals board size + 2 (for walls on every end of the board)
    @Test
    fun test_board_size_is_correct() {
        Assert.assertEquals(TEST_BOARD_SIZE + 2, mazeToTest.board.size)
    }

    @Test
    fun test_obstacles_list_generated_not_empty() {
        Assert.assertTrue(mazeToTest.obstacles.isNotEmpty())
    }
}