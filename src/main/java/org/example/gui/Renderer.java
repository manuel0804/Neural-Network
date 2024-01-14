package org.example.gui;

import org.example.NeuralNetwork.Network;
import org.example.utils.CustomOutputStream;

import javax.swing.*;
import java.io.PrintStream;

/**
 * The main renderer for visualizing the neural network and console output.
 */
public class Renderer extends JFrame {

    private final JTabbedPane tabbedPane;
    private NeuralNetworkPanel neuralNetworkPanel;
    private final ConsoleOutputPanel consoleOutputPanel;

    /**
     * Constructs a new Renderer.
     */
    public Renderer() {
        setTitle("Neural Network Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        consoleOutputPanel = new ConsoleOutputPanel();
    }

    /**
     * Updates the neural network visualization based on the provided weights.
     *
     * @param weights The updated weights.
     */
    public void update(double[][][] weights) {
        neuralNetworkPanel.updateWeightColor(weights);
    }

    /**
     * Redirects the standard output and error streams to the console output panel.
     */
    public void redirectSystemOut() {
        PrintStream printStream = new PrintStream(new CustomOutputStream(consoleOutputPanel.getTextArea()));
        System.setOut(printStream);
        System.setErr(printStream);
    }

    /**
     * Renders the neural network and console output panels.
     *
     * @param network The neural network to visualize.
     */
    public void render(Network network) {
        neuralNetworkPanel = new NeuralNetworkPanel(network);

        tabbedPane.addTab("Neural Network", neuralNetworkPanel);
        tabbedPane.addTab("Console Output", consoleOutputPanel);

        add(tabbedPane);

        setVisible(true);
    }
}
