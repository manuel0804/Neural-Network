package org.example.gui;

import org.example.NeuralNetwork.Network;
import org.example.utils.CustomOutputStream;

import javax.swing.*;
import java.io.PrintStream;

public class Renderer extends JFrame {

    private final Network network;
    private final JTabbedPane tabbedPane;
    private NeuralNetworkPanel neuralNetworkPanel;
    private final ConsoleOutputPanel consoleOutputPanel;

    public Renderer(Network network) {
        this.network = network;

        setTitle("Neural Network Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        consoleOutputPanel = new ConsoleOutputPanel();
    }

    public void update(double[][][] weights) {
        neuralNetworkPanel.updateWeightColor(weights);
    }

    public void redirectSystemOut() {
        PrintStream printStream = new PrintStream(new CustomOutputStream(consoleOutputPanel.getTextArea()));
        System.setOut(printStream);
        System.setErr(printStream);
    }

    public void render(){

        neuralNetworkPanel = new NeuralNetworkPanel(network);

        tabbedPane.addTab("Neural Network", neuralNetworkPanel);
        tabbedPane.addTab("Console Output", consoleOutputPanel);

        add(tabbedPane);

        setVisible(true);
    }
}