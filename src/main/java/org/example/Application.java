package org.example;

import org.example.NeuralNetwork.Network;
import org.example.gui.Renderer;
import org.example.sets.TrainingSet;
import org.example.utils.CSVReader;
import org.example.utils.Util;

import java.util.Arrays;


public class Application {

    public static void main(String[] args) {
/*        Network net = new Network(4,1,3,4);

        double[] input = new double[]{0.1,0.5,0.2,0.9};
        double[] expectedOutput = new double[]{1,1,0,0};

        int epochs = 1000;
        for(int epoch = 0; epoch < epochs; epoch++){
            net.train(input, expectedOutput, Util.LEARNING_RATE);
        }

        double[] result = net.forward(input);
        System.out.printf("Result: %.4f, %.4f, %.4f, %.4f\n\n", result[0], result[1], result[2], result[3]);

        Network net2 = new Network(4,3,3,2);

        TrainingSet ts = new TrainingSet(4,2);
        ts.addData(new double[]{0.1,0.2,0.3,0.4}, new double[]{0.9,0.1});
        ts.addData(new double[]{0.9,0.8,0.7,0.6}, new double[]{0.1,0.9});
        ts.addData(new double[]{0.3,0.8,0.1,0.4}, new double[]{0.3,0.7});
        ts.addData(new double[]{0.9,0.8,0.1,0.2}, new double[]{0.7,0.3});

        net2.train(ts, 10000);

        for(int i = 0; i < ts.size(); i++){
            System.out.println(Arrays.toString(net2.forward(ts.getInput(i))));
        }*/
        //stat Timer
        long startTime = System.currentTimeMillis();

        CSVReader.loadCSV("diabetes.csv");
        var inputData = CSVReader.trainingInputs;
        var outputData = CSVReader.trainingOutputs;

        var normalizedInput = Util.normalize(inputData);

        Network net3 = new Network(8,6,4,1);
        TrainingSet ts = new TrainingSet(8,1);

        Renderer r = new Renderer(net3);
        r.redirectSystemOut();

        for(int i = 0; i < inputData.length; i++){
            ts.addData(normalizedInput[i], outputData[i]);
        }

        net3.train(ts, 10000);

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.printf("\nExecution time in milliseconds: %d\n", timeElapsed);

        r.render();


        
    }
}
