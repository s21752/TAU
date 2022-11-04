package pl.edu.pjwstk.tau;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class WeightedAverage {

    private final File testFile;

    WeightedAverage() {
        this.testFile = null;
    }

    WeightedAverage(String filePath) {
        this.testFile = new File(".\\src\\test\\resources\\" + filePath);
    }

    public double calculate() {

        try {
            if (testFile == null) {
                throw new IllegalArgumentException("No file specified.");
            }

            if (!testFile.exists()) {
                throw new IllegalArgumentException("File does not exist.");
            }

            var fileReader = new BufferedReader(new FileReader(testFile));

            long sum = 0, weightSum = 0;

            String line = null;
            while ((line = fileReader.readLine()) != null) {
                long element, elementsWeight;
                String[] data = line.split(" ");
                if (data.length == 2) {
                    try {
                        elementsWeight = Long.parseLong(data[0]);
                        element = Long.parseLong(data[1]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Wrong data file format.");
                    }
                } else {
                    throw new IllegalArgumentException("Wrong data file format.");
                }

                sum += element * elementsWeight;
                weightSum += elementsWeight;
            }

            BigDecimal resultBD = new BigDecimal((double) sum / (double) weightSum).setScale(2, RoundingMode.HALF_UP);
            return resultBD.doubleValue();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    // to satisfy exercise specification (removing trailing zeroes), result has to be returned as a String
    public String resultAsString() {
        var result = calculate();

        String stringResult = String.format("%.2f", result);

        return removeTrailingZeroes(stringResult);
    }

    String removeTrailingZeroes(String result) {
        char lastChar = result.charAt(result.length() - 1);
        if (lastChar == '0' && result.contains(".")) {
            return removeTrailingZeroes(result.substring(0, result.lastIndexOf('0')));
        }
        if (lastChar == '.') {
            return result.replace(".", "");
        }

        return result;
    }
}
