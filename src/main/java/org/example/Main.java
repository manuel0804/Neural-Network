package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // Beispielaufruf der train-Methode mit zufälligen Daten.
        // timer start
        var start = System.currentTimeMillis();

        CSVReader.loadCSV();
        var trainingInputs = CSVReader.trainingInputs;
        var trainingOutputs = CSVReader.trainingOutputs;

        var neuralNetwork = new DiabetesNeuralNetwork(trainingInputs, trainingOutputs);
        neuralNetwork.train();

/*        double[][] training_inputs = {{0.0f,0.0f},
            {1.0f,0.0f},
            {0.0f,1.0f},
            {1.0f,1.0f}};
        double[][] training_outputs= {{0.0f},
            {1.0f},
            {1.0f},
            {0.0f}};
        var neuralNetwork2 = new DiabetesNeuralNetwork(training_inputs, training_outputs);
        neuralNetwork2.train();*/

        // timer end
        var end = System.currentTimeMillis();
        var timeElapsed = end - start;
        System.out.printf("\n Time it took to compile: %d ms", timeElapsed);
    }
}
