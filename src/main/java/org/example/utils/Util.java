package org.example.utils;

/**
 * Util is a utility class providing common functionalities such as sigmoid activation,
 * its derivative, data normalization, and random array creation.
 */
public class Util {

    /** The learning rate constant. */
    public static final double LEARNING_RATE = 0.05;

    /**
     * Computes the sigmoid activation function for a given input.
     *
     * @param x The input value.
     * @return The sigmoid activation of the input.
     */
    public static double sigmoid(double x) {
        return 1d / (1 + Math.exp(-x));
    }

    /**
     * Computes the derivative of the sigmoid activation function for a given input.
     *
     * @param x The input value.
     * @return The derivative of the sigmoid activation.
     */
    public static double sigmoidDerivative(double x) {
        return sigmoid(x) * (1 - sigmoid(x));
    }

    /**
     * Normalizes each column of a 2D array to the range [0, 1].
     *
     * @param data The input data array.
     * @return The normalized data array.
     */
    public static double[][] normalize(double[][] data) {
        int numRows = data.length;
        int numCols = data[0].length;

        for (int col = 0; col < numCols; col++) {
            // Find min and max values for the current column
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;

            for (double[] datum : data) {
                min = Math.min(min, datum[col]);
                max = Math.max(max, datum[col]);
            }

            // Normalize the current column to the range [0, 1]
            for (int row = 0; row < numRows; row++) {
                data[row][col] = (data[row][col] - min) / (max - min);
            }
        }
        return data;
    }

    /**
     * Creates a random array of specified size with values between a given lower and upper bound.
     *
     * @param size The size of the array.
     * @param lowerBound The lower bound for random values.
     * @param upperBound The upper bound for random values.
     * @return The random array.
     */
    public static double[] createRandomArray(int size, double lowerBound, double upperBound){
        if(size < 1) return null;

        double[] array = new double[size];
        for(int i = 0; i < size; i++){
            array[i] = randomValue(lowerBound, upperBound);
        }
        return array;
    }

    /**
     * Creates a random 2D array of specified size with values between given lower and upper bounds.
     *
     * @param sizeX The number of rows.
     * @param sizeY The number of columns.
     * @param lowerBound The lower bound for random values.
     * @param upperBound The upper bound for random values.
     * @return The random 2D array.
     */
    public static double[][] createRandomArray(int sizeX, int sizeY, double lowerBound, double upperBound){
        if(sizeX < 1 || sizeY < 1) return null;

        double[][] array = new double[sizeX][sizeY];
        for(int i = 0; i < sizeX; i++){
            array[i] = createRandomArray(sizeY, lowerBound, upperBound);
        }
        return array;
    }

    /**
     * Generates a random value between a given lower and upper bound.
     *
     * @param lowerBound The lower bound for the random value.
     * @param upperBound The upper bound for the random value.
     * @return The random value.
     */
    public static double randomValue(double lowerBound, double upperBound){
        return Math.random() * (upperBound - lowerBound) + lowerBound;
    }

    /**
     * Calculates the error between the predicted output and the expected output.
     *
     * @param output The predicted output.
     * @param expectedOutput The expected output.
     * @return The error.
     */
    public static double calculateError(double[] output, double[] expectedOutput) {
        double error = 0;
        for (int i = 0; i < output.length; i++) {
            error += Math.abs(output[i] - expectedOutput[i]);
        }
        return error / output.length;
    }

    /**
     * Calculates the number of false positives.
     *
     * @param output The predicted output.
     * @param expectedOutput The expected output.
     * @return The number of false positives.
     */
    public static int calculateFalsePositives(double[] output, double[] expectedOutput) {
        int falsePositives = 0;
        for (int i = 0; i < output.length; i++) {
            if (output[i] > 0.5 && expectedOutput[i] == 0) {
                falsePositives++;
            }
            if(output[i] < 0.5 && expectedOutput[i] == 1){
                falsePositives++;
            }
        }
        return falsePositives;
    }

    /**
     * Calculates the loss using a specific loss function (e.g., mean squared error).
     *
     * @param output The predicted output.
     * @param expectedOutput The expected output.
     * @return The loss.
     */
    public static double calculateLoss(double[] output, double[] expectedOutput) {
        // Mean Squared Error
        double loss = 0;
        for (int i = 0; i < output.length; i++) {
            loss += Math.pow(output[i] - expectedOutput[i], 2);
        }
        return loss / output.length;
    }
}
