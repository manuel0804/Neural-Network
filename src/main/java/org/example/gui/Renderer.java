package org.example.gui;



import org.example.NeuralNetwork.Network;

import javax.swing.*;

public class Renderer extends JFrame {

    private final Network network;
    private final NeuralNetworkPanel neuralNetworkPanel;

    public Renderer(Network network) {
        this.network = network;

        setTitle("Neural Network Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        neuralNetworkPanel = new NeuralNetworkPanel(network);
        add(neuralNetworkPanel);

        setVisible(true);
    }

    public void update(double[][][] weights){
        neuralNetworkPanel.updateWeightColor(weights);
    }
}
