package org.example.NeuralNetwork;


import org.example.sets.TrainingSet;
import org.example.utils.Util;

import java.util.Collections;

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
    public final double[][][] weights;

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

            this.bias[i] = Util.createRandomArray(NETWORK_LAYER_SIZES[i], 1, 1);
            if (i > 0) {
                weights[i] = Util.createRandomArray(NETWORK_LAYER_SIZES[i], NETWORK_LAYER_SIZES[i - 1], -1, 1);
            }
        }
    }

    /**
     * Trains the neural network using the provided training set and specified number of loops.
     *
     * @param set The training set.
     * @param epochs The number of training loops.
     */
    public void train(TrainingSet set, int epochs) {
        if (set.INPUT_SIZE != INPUT_SIZE || set.OUTPUT_SIZE != OUTPUT_SIZE) return;
        Collections.shuffle(set.getData());
        for (int epoch = 0; epoch < epochs; epoch++) {
            // Shuffle the training set at the beginning of each epoch
            Collections.shuffle(set.getData());

            double totalError = 0;
            int totalFalsePositives = 0;
            double totalLoss = 0;

            for (int t = 0; t < set.size(); t++) {
                double[] input = set.getInput(t);
                double[] expectedOutput = set.getOutput(t);

                // Forward pass
                double[] tempOutput = forward(input);

                // Backpropagation
                backpropagationError(expectedOutput);

                // Update weights
                updateWeights(Util.LEARNING_RATE);

                // Calculate metrics for this training example
                double error = Util.calculateError(tempOutput, expectedOutput);
                int falsePositives = Util.calculateFalsePositives(tempOutput, expectedOutput);
                double loss = Util.calculateLoss(tempOutput, expectedOutput);

                // Accumulate metrics for the entire epoch
                totalError += error;
                totalFalsePositives += falsePositives;
                totalLoss += loss;
            }

            // Calculate average metrics for the epoch
            double averageError = totalError / set.size();
            double averageFalsePositives = (double) totalFalsePositives / set.size();
            double averageLoss = totalLoss / set.size();

            // Print or store the metrics for the epoch
            System.out.println("\nEpoch " + epoch + ":");
            System.out.println("Average Error: " + averageError);
            System.out.println("Average False Positives: " + averageFalsePositives);
            System.out.println("Average Loss: " + averageLoss);
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
    }

    /**
     * Performs forward propagation through the neural network using the provided input.
     *
     * @param input The input data array.
     * @return The output values of the last layer.
     */
    public double[] forward(double[] input) {
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

