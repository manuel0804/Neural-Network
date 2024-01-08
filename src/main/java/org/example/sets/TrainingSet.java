package org.example.sets;

import java.util.ArrayList;

/**
 * TrainingSet represents a collection of training data pairs, each consisting of an input and
 * its corresponding expected output. It provides methods to add data, retrieve input and output
 * by index, and get the size of the training set.
 */
public class TrainingSet {

    /** The size of the input data. */
    public final int INPUT_SIZE;

    /** The size of the output data. */
    public final int OUTPUT_SIZE;

    /** The list to store training data pairs. */
    private final ArrayList<double[][]> data = new ArrayList<double[][]>();

    /**
     * Constructs a TrainingSet with specified input and output sizes.
     *
     * @param INPUT_SIZE The size of the input data.
     * @param OUTPUT_SIZE The size of the output data.
     */
    public TrainingSet(int INPUT_SIZE, int OUTPUT_SIZE){
        this.INPUT_SIZE = INPUT_SIZE;
        this.OUTPUT_SIZE = OUTPUT_SIZE;
    }

    /**
     * Adds a new training data pair to the training set.
     *
     * @param input The input data array.
     * @param expectedOutput The expected output data array.
     */
    public void addData(double[] input, double[] expectedOutput){
        if(input.length != INPUT_SIZE || expectedOutput.length != OUTPUT_SIZE) return;
        data.add(new double[][]{input, expectedOutput});
    }

    /**
     * Returns the size of the training set.
     *
     * @return The size of the training set.
     */
    public int size(){
        return data.size();
    }

    /**
     * Retrieves the input data array at the specified index.
     *
     * @param index The index of the training data pair.
     * @return The input data array.
     */
    public double[] getInput(int index){
        return data.get(index)[0];
    }

    /**
     * Retrieves the expected output data array at the specified index.
     *
     * @param index The index of the training data pair.
     * @return The expected output data array.
     */
    public double[] getOutput(int index){
        return data.get(index)[1];
    }

    public ArrayList<double[][]> getData() {
        return data;
    }
}
