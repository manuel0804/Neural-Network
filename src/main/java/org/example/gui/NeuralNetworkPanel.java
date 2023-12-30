package org.example.gui;



import org.example.NeuralNetwork.Network;

import javax.swing.*;
import java.awt.*;

public class NeuralNetworkPanel extends JPanel {

    private final Network network;
    private double[][][] weights;  // Store weights

    public NeuralNetworkPanel(Network network) {
        this.network = network;
        this.weights = network.weights;  // Initialize weights
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawNeuralNetwork(g);
    }

    public void updateWeightColor(double[][][] weights) {
        this.weights = weights;  // Update weights
        repaint();  // This will trigger the paintComponent method
    }

    private void drawNeuralNetwork(Graphics g) {
        int[] layerSizes = network.NETWORK_LAYER_SIZES;

        int startX = 50;
        int startY = getHeight() / 2;

        for (int layer = 0; layer < layerSizes.length; layer++) {
            int neuronsInLayer = layerSizes[layer];

            for (int neuron = 0; neuron < neuronsInLayer; neuron++) {
                int x = startX + layer * 200;
                int y = startY - neuronsInLayer * 30 + neuron * 60;

                // Draw the neuron
                g.setColor(Color.BLUE);
                g.fillOval(x, y, 30, 30);

                // Draw connections to the next layer
                if (layer < layerSizes.length - 1) {
                    int nextLayerNeurons = layerSizes[layer + 1];

                    for (int nextNeuron = 0; nextNeuron < nextLayerNeurons; nextNeuron++) {
                        int nextX = startX + (layer + 1) * 200;
                        int nextY = startY - nextLayerNeurons * 30 + nextNeuron * 60;

                        // Determine the color based on the weight
                        double weight = network.weights[layer + 1][nextNeuron][neuron];
                        Color lineColor = weight >= 0 ? Color.GREEN : Color.RED;
                        g.setColor(lineColor);

                        // Draw the connection line
                        g.drawLine(x + 30, y + 15, nextX, nextY + 15);
                    }
                }
            }
        }
    }
}