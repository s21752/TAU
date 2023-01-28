import base.Game
import base.GameException
import game.MazeGame
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream

class MazeGameTests {

    companion object {
        const val DEFAULT_BOARD_SIZE = 5
    }

    lateinit var maze: MazeGame
    lateinit var sysInBackUp: InputStream
    lateinit var sysOutBackUp: PrintStream

    private var testOut = ByteArrayOutputStream()

    @Before
    fun initializeTestClass() {
        sysInBackUp = System.`in`
        sysOutBackUp = System.out
        maze = MazeGame(System.out)
    }

    @After
    fun cleanUp() {
        System.setIn(sysInBackUp)
        System.setOut(sysOutBackUp)
    }

    @Test
    fun test_game_instruction_showing_correctly() {
        maze.output = PrintStream(testOut)
        maze.showInstruction()
        maze.output = sysOutBackUp

        Assert.assertTrue(testOut.toString().contains("Rules:"))
        Assert.assertTrue(testOut.toString().contains("You are starting at randomly chosen point[S] at the bottom of the board"))
        Assert.assertTrue(testOut.toString().contains("To win a game you have to reach maze exit[E] at the top of the board"))
        Assert.assertTrue(testOut.toString().contains("Use letters to move through the maze:"))
        Assert.assertTrue(testOut.toString().contains("To move right, write [R]"))
        Assert.assertTrue(testOut.toString().contains("To move left, write [L]"))
        Assert.assertTrue(testOut.toString().contains("To move up, write [U]"))
        Assert.assertTrue(testOut.toString().contains("To move down, write [D]"))
        Assert.assertTrue(testOut.toString().contains("Write 'Q' to end the game at any point"))
    }

    @Test
    fun test_quiting_game_return_game_not_finished_result() {
        // simulating user input for board size
        System.setIn("$DEFAULT_BOARD_SIZE".byteInputStream())
        maze.prepareBoard()

        // simulated input quitting the game immediately after start
        System.setIn("Q".byteInputStream())

        val result = maze.awaitGameResult()

        Assert.assertEquals(result, Game.Result.NotFinished)
    }

    @Test
    fun test_starting_game_with_initialising_board_first_works() {
        // simulating user input for board size
        System.setIn("$DEFAULT_BOARD_SIZE".byteInputStream())
        maze.prepareBoard()

        // simulated input quitting the game immediately after start
        System.setIn("Q".byteInputStream())

        assertDoesNotThrow {
            maze.awaitGameResult()
        }
    }

    @Test
    fun test_starting_game_without_initialising_board_throws_exception() {
        assertThrows<GameException.GameStartedWithUninitialisedBoard> {
            maze.awaitGameResult()
        }
    }

    @Test
    fun test_providing_decimal_board_size_number_format_not_crashing_game() {
        // simulating user input for board size as decimal first time, and then correct size to finish test
        System.setIn("5.6\n10".byteInputStream())
        assertDoesNotThrow {
            maze.prepareBoard()
        }
    }

    @Test
    fun test_providing_wrong_board_size_number_format_not_crashing_game() {
        // simulating user input for board size different than integer first time, and then correct size to finish test
        System.setIn("T\n10".byteInputStream())
        assertDoesNotThrow {
            maze.prepareBoard()
        }
    }

    @Test
    fun test_providing_smaller_than_minimum_board_size_not_crashing_game() {
        // simulating user input for board size smaller than minimum [$MazeGenerator.MIN_SIZE] first time, and then correct size to finish test
        System.setIn("3\n10".byteInputStream())
        assertDoesNotThrow {
            maze.prepareBoard()
        }
    }

    @Test
    fun test_providing_greater_than_maximum_board_size_not_crashing_game() {
        // simulating user input for board size smaller than minimum [$MazeGenerator.MIN_SIZE] first time, and then correct size to finish test
        System.setIn("50\n10".byteInputStream())
        assertDoesNotThrow {
            maze.prepareBoard()
        }
    }

    @Test
    fun test_providing_negative_board_size_not_crashing_game() {
        // simulating user input for board size different than integer first time, and then correct size to finish test
        System.setIn("-10\n10".byteInputStream())
        assertDoesNotThrow {
            maze.prepareBoard()
        }
    }

    @Test
    fun test_going_into_wall_ends_in_lose_result() {
        // simulating user input for board size
        System.setIn("10".byteInputStream())
        maze.prepareBoard()

        // simulating user input for going left from start point, which ends in wall
        System.setIn("L".byteInputStream())

        val result = maze.awaitGameResult()
        Assert.assertTrue(result is Game.Result.Lose)
    }
}