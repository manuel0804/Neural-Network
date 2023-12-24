package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class DiabetesNeuralNetwork {
    private final double[][] trainingInputs;
    private final double[][] trainingOutputs;
    private final double learningRate = 0.05;
    private final int numEpochs = 100;

    private int numInputs, numHiddenNodes, numOutputs, numTrainingSets;

    public DiabetesNeuralNetwork(double[][] trainingInputs, double[][] trainingOutputs){
        this.trainingInputs = normalize(trainingInputs);
        this.trainingOutputs = trainingOutputs;
        this.numInputs = trainingInputs[0].length;
        this.numHiddenNodes = 5; // Adjust the number as needed
        this.numOutputs = trainingOutputs[0].length;
        this.numTrainingSets = trainingInputs.length;
    }

    private double[][] normalize(double[][] data) {
        int numSamples = data.length;
        int numFeatures = data[0].length;

        for (int j = 0; j < numFeatures; j++) {
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;

            // Find min and max values for the current feature
            for (int i = 0; i < numSamples; i++) {
                min = Math.min(min, data[i][j]);
                max = Math.max(max, data[i][j]);
            }

            // Scale the feature to the range [0, 1]
            for (int i = 0; i < numSamples; i++) {
                data[i][j] = (data[i][j] - min) / (max - min);
            }
        }

        return data;
    }

    public double initWeights(){
        Random random = new Random();
        return -1 + (1 - (-1)) * random.nextDouble();
    }

    private double sigmoid(double x){
        return 1 / (1 + Math.exp(-x));
    }

    private double sigmoidDerivative(double x){
        return sigmoid(x) * (1 - sigmoid(x));
    }

    private void shuffle(int[] array){
        Random rand = new Random();
        int n = array.length;

        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            // Swap array[i] and array[j]
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public void train(){

        double[] hiddenLayer = new double[numHiddenNodes];
        double[] outputLayer = new double[numOutputs];

        double[] hiddenLayerBias = new double[numHiddenNodes];
        double[] outputLayerBias = new double[numOutputs];

        double[][] hiddenWeights = new double[numInputs][numHiddenNodes];
        double[][] outputWeights = new double[numHiddenNodes][numOutputs];
        //both of them are build like a matrix first col is first vector

        // Initialize weights
        for(int i = 0; i < numInputs; i++){
            for(int j = 0; j < numHiddenNodes; j++){
                hiddenWeights[i][j] = initWeights();
            }
        }

        for(int i = 0; i < numHiddenNodes; i++){
            for(int j = 0; j < numOutputs; j++){
                outputWeights[i][j] = initWeights();
            }
        }

        // Initialize biases
        for(int i = 0; i < numHiddenNodes; i++){
            hiddenLayerBias[i] = initWeights();
        }
        //Initialize output bias
        for(int i = 0; i < numOutputs; i++){
            outputLayerBias[i] = initWeights();
        }


        int[] trainingSetOrder = IntStream.range(0, numTrainingSets).toArray();



        // Training
        for(int epoch = 0; epoch < numEpochs; epoch++){
            int correctClassified = 0;

            shuffle(trainingSetOrder);

            for(int x = 0; x < numTrainingSets; x++){

                int currentSet = trainingSetOrder[x];

                // Forward pass
                //input -> hidden
                for(int i = 0; i < numHiddenNodes; i++){
                    double scalarSum = 0;
                    for(int j = 0; j < numInputs; j++){
                        scalarSum += trainingInputs[currentSet][j] * hiddenWeights[j][i];
                    }
                    hiddenLayer[i] = scalarSum + hiddenLayerBias[i] ;
                }

                //hidden -> output
                for(int i = 0; i < numOutputs; i++){
                    double scalarSum = 0;
                    for(int j = 0; j < numHiddenNodes; j++){
                        scalarSum += sigmoid(hiddenLayer[j]) * outputWeights[j][i];
                    }
                    outputLayer[i] = scalarSum + outputLayerBias[i];
                }

                //prints the input
                System.out.printf("Input: %s\n", Arrays.toString(trainingInputs[currentSet]));
                System.out.printf("Actual Output: %.6f,  Expected Output: %d\n\n", sigmoid(outputLayer[0]), (int)trainingOutputs[currentSet][0]);
                if(sigmoid(outputLayer[0]) >= 0.5 && trainingOutputs[currentSet][0] == 1) correctClassified++;
                if(sigmoid(outputLayer[0]) < 0.5 && trainingOutputs[currentSet][0] == 0) correctClassified++;




                // Delta output calculation
                double[] out = new double[numOutputs];
                for(int j = 0; j < numOutputs; j++){
                    out[j] = sigmoid(outputLayer[j]);
                }

                double[] deltaOutput = new double[numOutputs];
                for(int j = 0; j < numOutputs; j++){
                    deltaOutput[j] = sigmoidDerivative(outputLayer[j]) * (trainingOutputs[currentSet][j] - out[j]);
                }

                double[] deltaHidden = new double[numHiddenNodes];
                for (int j=0; j<numHiddenNodes; j++) {
                    double errorHidden = 0;
                    for (int k=0; k<numOutputs; k++) {
                        errorHidden += deltaOutput[k] * outputWeights[j][k];
                    }
                    deltaHidden[j] = errorHidden * sigmoidDerivative(hiddenLayer[j]);
                }

                // Update weights
                for (int j=0; j<numOutputs; j++){
                    outputLayerBias[j] += deltaOutput[j] * learningRate;
                    for (int k=0; k<numHiddenNodes; k++) {
                        outputWeights[k][j] += hiddenLayer[k] * deltaOutput[j] * learningRate;
                    }
                }

                for (int j=0; j<numHiddenNodes; j++) {
                    hiddenLayerBias[j] += deltaHidden[j] * learningRate;
                    for(int k=0; k<numInputs; k++) {
                        hiddenWeights[k][j] += trainingInputs[currentSet][k] * deltaHidden[j] * learningRate;
                    }
                }
            }
            System.out.println("Epoch: " + epoch + " Accuracy: " + (double)correctClassified/numTrainingSets * 100 + "%");
        }
    }
}
