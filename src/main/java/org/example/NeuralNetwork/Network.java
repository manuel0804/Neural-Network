package org.example.NeuralNetwork;


import org.example.sets.TrainingSet;
import org.example.utils.Util;

/**
 * Network represents a feedforward neural network with the specified layer sizes.
 * It includes methods for training, forward propagation, backpropagation error calculation,
 * and weight updates.
 */
public class Network {

    /** The sizes of each layer in the neural network. */
    public final int[] NETWORK_LAYER_SIZES;

    /** The size of the input layer. */
    public final int INPUT_SIZE;

    /** The size of the output layer. */
    public final int OUTPUT_SIZE;

    /** The total number of layers in the network. */
    public final int NETWORK_SIZE;

    /** The output values for each neuron in each layer. */
    private final double[][] output;

    /** The weights connecting neurons in adjacent layers. */
    private final double[][][] weights;

    /** The biases for each neuron in each layer. */
    private final double[][] bias;

    /** The error signals for each neuron in each layer. */
    private final double[][] errorSignal;

    /** The derivatives of the output values for each neuron in each layer. */
    private final double[][] outputDerivative;

    /**
     * Constructs a neural network with the specified layer sizes.
     *
     * @param NETWORK_LAYER_SIZES The sizes of each layer in the neural network.
     */
    public Network(int... NETWORK_LAYER_SIZES) {
        this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
        this.INPUT_SIZE = NETWORK_LAYER_SIZES[0];
        this.NETWORK_SIZE = NETWORK_LAYER_SIZES.length;
        this.OUTPUT_SIZE = NETWORK_LAYER_SIZES[NETWORK_SIZE - 1];

        this.output = new double[NETWORK_SIZE][];
        this.weights = new double[NETWORK_SIZE][][];
        this.bias = new double[NETWORK_SIZE][];
        this.errorSignal = new double[NETWORK_SIZE][];
        this.outputDerivative = new double[NETWORK_SIZE][];

        for (int i = 0; i < NETWORK_SIZE; i++) {
            this.output[i] = new double[NETWORK_LAYER_SIZES[i]];
            this.errorSignal[i] = new double[NETWORK_LAYER_SIZES[i]];
            this.outputDerivative[i] = new double[NETWORK_LAYER_SIZES[i]];

            this.bias[i] = Util.createRandomArray(NETWORK_LAYER_SIZES[i], 0.3, 0.7);

            if (i > 0) {
                weights[i] = Util.createRandomArray(NETWORK_LAYER_SIZES[i], NETWORK_LAYER_SIZES[i - 1], -0.3, 0.5);
            }
        }
    }

    /**
     * Trains the neural network using the provided training set and specified number of loops.
     *
     * @param set The training set.
     * @param loops The number of training loops.
     */
    public void train(TrainingSet set, int loops) {
        if (set.INPUT_SIZE != INPUT_SIZE || set.OUTPUT_SIZE != OUTPUT_SIZE) return;
        for (int i = 0; i < loops; i++) {
            for (int t = 0; t < set.size(); t++) {
                this.train(set.getInput(t), set.getOutput(t), Util.LEARNING_RATE);
            }
        }
    }

    /**
     * Trains the neural network with the provided input, expected output, and learning rate.
     *
     * @param input The input data array.
     * @param expectedOutput The expected output data array.
     * @param learningRate The learning rate.
     */
    public void train(double[] input, double[] expectedOutput, double learningRate) {
        if (input.length != INPUT_SIZE || expectedOutput.length != OUTPUT_SIZE) return;

        forward(input);
        backpropagationError(expectedOutput);
        updateWeights(learningRate);
        System.out.printf("Actual Result: %.4f\tExpected Result %s\n", output[NETWORK_SIZE - 1][0], expectedOutput[0] == 1 ? "Diabetes" : "No Diabetes");
    }

    /**
     * Performs forward propagation through the neural network using the provided input.
     *
     * @param input The input data array.
     * @return The output values of the last layer.
     */
    public double[] forward(double... input) {
        if (input.length != this.INPUT_SIZE) return null;
        this.output[0] = input;
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {
                double scalarSum = bias[layer][neuron];
                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
                    scalarSum += output[layer - 1][prevNeuron] * weights[layer][neuron][prevNeuron];
                }
                output[layer][neuron] = Util.sigmoid(scalarSum);
                outputDerivative[layer][neuron] = Util.sigmoidDerivative(scalarSum);
            }
        }
        return output[NETWORK_SIZE - 1];
    }

    /**
     * Calculates the error signals for each neuron in each layer during backpropagation.
     *
     * @param expectedOutput The expected output data array.
     */
    public void backpropagationError(double[] expectedOutput) {
        for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[NETWORK_SIZE - 1]; neuron++) {
            errorSignal[NETWORK_SIZE - 1][neuron] = (output[NETWORK_SIZE - 1][neuron] - expectedOutput[neuron]) * outputDerivative[NETWORK_SIZE - 1][neuron];
        }

        for (int layer = NETWORK_SIZE - 2; layer > 0; layer--) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {
                double sum = 0;
                for (int nextNeuron = 0; nextNeuron < NETWORK_LAYER_SIZES[layer + 1]; nextNeuron++) {
                    sum += weights[layer + 1][nextNeuron][neuron] * errorSignal[layer + 1][nextNeuron];
                }
                this.errorSignal[layer][neuron] = sum * outputDerivative[layer][neuron];
            }
        }
    }

    /**
     * Updates the weights and biases of the neural network using the specified learning rate.
     *
     * @param learningRate The learning rate.
     */
    public void updateWeights(double learningRate) {
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {
                double delta = -learningRate * errorSignal[layer][neuron];
                bias[layer][neuron] += delta;
                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
                    weights[layer][neuron][prevNeuron] += delta * output[layer - 1][prevNeuron];
                }
            }
        }
    }
}

