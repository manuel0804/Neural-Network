package org.example.OOP;

public class Neuron {

    double in;
    double out;
    double delta;
    double[] weights;

    // Constructor for the input neurons
    public Neuron(double value){
        this.in = Util.sigmoid(value);
    }

    // Constructor for the hidden / output neurons
    public Neuron(double[] weights){
        this.weights = weights;
        this.in = 0;
    }
}
