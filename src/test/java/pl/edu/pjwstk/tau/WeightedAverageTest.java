package pl.edu.pjwstk.tau;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class WeightedAverageTest {

    @Test
    public void should_create_class() {
        var average = new WeightedAverage();

        assertThat(average).isNotNull();
    }

    @Test
    public void should_calculate_simple_average() {
        try {
            // assume ??
            var average = new WeightedAverage("average\\test_avg_1.txt");

            // act
            double avg = average.calculate();

            // assert
            assertThat(avg).isEqualTo(4.52);
        } catch (Exception e) {
            // not checking throwing exceptions here so we can catch it, just to test one thing
        }
    }

    @Test
    public void should_throw_when_file_does_not_exists() {
        assertThatThrownBy(() -> {
            new WeightedAverage("imaginary_file_name.txt").calculate();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("File does not exist.");
    }

    @Test
    public void should_throw_when_file_not_specified() {
        try {
            new WeightedAverage().calculate();
        } catch (Exception e) {
            assertThatThrownBy(() -> {
                throw e;
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("No file specified.");
        }
    }

    @Test
    public void should_throw_when_wrong_file_content_format() {
        // assertion in try catch block to run the test only if creation of the WeightedAverage object throws any exception
        try {
            new WeightedAverage("average\\test_avg_wrong_content_format.txt").calculate();
        } catch (Exception e) {
            assertThatThrownBy(() -> {
                throw e;
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Wrong data file format.");
        }

        try {
            new WeightedAverage("average\\test_avg_wrong_content_format_2.txt").calculate();
        } catch (Exception e) {
            assertThatThrownBy(() -> {
                throw e;
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Wrong data file format.");
        }
    }

    @Test
    public void should_calculate_average_example_1() {
        var average = new WeightedAverage("average\\test_ex_1.txt");

        double avg = average.calculate();

        assertThat(avg).isEqualTo(240.0);
    }

    @Test
    public void should_calculate_average_example_2() {
        var average = new WeightedAverage("average\\test_ex_2.txt");

        double avg = average.calculate();

        assertThat(avg).isEqualTo(7.6);
    }

    @Test
    public void should_calculate_average_example_3() {
        var average = new WeightedAverage("average\\test_ex_3.txt");

        double avg = average.calculate();

        assertThat(avg).isEqualTo(5.0);
    }

    @Test
    public void should_remove_trailing_zeroes_1() {
        var average = new WeightedAverage("average\\trailing_zeroes.txt");

        String avg = average.resultAsString();

        assertThat(avg).isEqualTo("0.99");
    }

    @Test
    public void should_remove_trailing_zeroes_2() {
        var average = new WeightedAverage("average\\trailing_zeroes_2.txt");

        String avg = average.resultAsString();

        assertThat(avg).isEqualTo("0.9");
    }

    @Test
    public void should_remove_trailing_zeroes_3() {
        var average = new WeightedAverage("average\\trailing_zeroes_3.txt");

        String avg = average.resultAsString();

        assertThat(avg).isEqualTo("1");
    }

}
