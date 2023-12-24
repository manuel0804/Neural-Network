package org.example.OOP;

public class NeuralNetwork {

    static Layer[] layers;

    double[] trainingData;
    double[] expectedOutput;

    public void initNN(double[] trainingData, double[] expectedOutput) {
        this.trainingData = trainingData;
        this.expectedOutput = expectedOutput;
        layers = new Layer[3];
        layers[0] = new Layer(trainingData);
        layers[1] = new Layer(trainingData.length-2, trainingData.length);
        layers[2] = new Layer(1, trainingData.length-2);

        train();
    }

    public void train(){

        forward();

        System.out.printf("Actual output: %f\tExpected output: %f\n", layers[2].neurons[0].out, expectedOutput[0]);

        backwardPropagation();
    }

    public void forward(){

        for(int i = 0; i < layers[1].neurons.length; i++){
            double scalarProduct = 0;
            for(int j = 0; j < layers[0].neurons.length; j++){
                scalarProduct += layers[0].neurons[j].in * layers[1].neurons[i].weights[j];
            }
            layers[1].neurons[i].in = scalarProduct;
            layers[1].neurons[i].out = Util.sigmoid(scalarProduct);
        }

        for(int i = 0; i < layers[2].neurons.length; i++){
            double scalarProduct = 0;
            for(int j = 0; j < layers[1].neurons.length; j++){
                scalarProduct += layers[1].neurons[j].in * layers[2].neurons[i].weights[j];
            }
            layers[2].neurons[i].in = scalarProduct;
            layers[2].neurons[i].out = Util.sigmoid(scalarProduct);
        }
    }

    public void backwardPropagation(){

        for(int i = 0; i < layers[2].neurons.length; i++){
            layers[2].neurons[i].delta = (expectedOutput[i] - layers[2].neurons[i].out) * Util.sigmoidDerivative(layers[2].neurons[i].in);
        }

        // Update weights for the output layer
        for(int i = 0; i < layers[2].neurons.length; i++){
            for(int j = 0; j < layers[1].neurons.length; j++){
                layers[2].neurons[i].weights[j] += layers[2].neurons[i].delta * layers[1].neurons[j].out * Util.learningRate;
            }
        }

        // Update weights for the hidden layer
        for(int i = 0; i < layers[1].neurons.length; i++) {
            for(int j = 0; j < layers[0].neurons.length; j++){
                layers[1].neurons[i].weights[j] += layers[1].neurons[i].delta * layers[0].neurons[j].out * Util.learningRate;
            }
        }
    }

}
