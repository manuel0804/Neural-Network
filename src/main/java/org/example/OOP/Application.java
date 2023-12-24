package org.example.OOP;

import org.example.CSVReader;

import java.util.stream.IntStream;

public class Application {
    public static void main(String[] args) {
        CSVReader.loadCSV();
        var trainingData = CSVReader.trainingInputs;
        var expectedOutput = CSVReader.trainingOutputs;

        var trainingData2 = new double[][]{{0.0f,0.0f},
                {1.0f,0.0f},
                {0.0f,1.0f},
                {1.0f,1.0f}};
        var expectedOutput2 = new double[][]{{0.0f},
                {1.0f},
                {1.0f},
                {0.0f}};

        var nn = new NeuralNetwork();

        int[] trainingSetOrder = IntStream.range(0, trainingData.length).toArray();

//        nn.initNN(trainingData[0], expectedOutput[0]);
        for(int i= 0; i < Util.numberOfEpochs; i++) {
            Util.shuffle(trainingSetOrder);

            for(int j = 0; j < trainingSetOrder.length; j++) {
                nn.initNN(trainingData[trainingSetOrder[j]], expectedOutput[trainingSetOrder[j]]);
//                nn.initNN(trainingData2[trainingSetOrder2[j]], expectedOutput2[trainingSetOrder2[j]]);
            }
        }

    }
}
