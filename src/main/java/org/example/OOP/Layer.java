package org.example.OOP;

public class Layer {
    public Neuron[] neurons;

    // Constructor for the input layer
    public Layer(double[] input){
        this.neurons = new Neuron[input.length];
        for(int i = 0; i < input.length; i++){
            this.neurons[i] = new Neuron(input[i]);
        }
    }

    // Constructor for the hidden and output layer
    public Layer(int numberNeurons, int previousLayerSize){
        this.neurons = new Neuron[numberNeurons];

        for(int i = 0; i < numberNeurons; i++) {
            double[] weights = new double[previousLayerSize];
            for(int j = 0; j < previousLayerSize; j++) {
                weights[j] = Util.randomDouble();
            }
            neurons[i] = new Neuron(weights);
        }
    }

    public void printLayer(){
        for(int i = 0; i < neurons.length; i++){
            System.out.printf("Neuron value: %f\n", neurons[i].in);
        }
    }
}
