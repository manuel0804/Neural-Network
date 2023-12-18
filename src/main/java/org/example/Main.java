package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println("Des is mein Branch");
        System.out.println("Ig will nimmer");

        // Beispielaufruf der train-Methode mit zufälligen Daten.
        NeuronalNetworkTraining trainer = new NeuronalNetworkTraining();
        double[][] inputPatterns = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[] outputPatterns = {0, 1, 1, 0};

        trainer.train(inputPatterns, outputPatterns);
        //help
    }
}