package org.example.OOP;

import java.util.Random;

public class Util {

    public static double learningRate = 0.05;
    public static int numberOfEpochs = 100;

    public static double randomDouble() {
        return (Math.random() * 2) -1;
    }

    public static double sigmoid(double x) {
        return (1/(1+Math.pow(Math.E, -x)));
    }

    public static double sigmoidDerivative(double x) {
        return sigmoid(x)*(1-sigmoid(x));
    }

    public static void shuffle(int[] array){
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--){
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
