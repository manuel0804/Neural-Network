package org.example.gui;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that displays console output in a JTextArea.
 */
class ConsoleOutputPanel extends JPanel {

    private final JTextArea textArea;

    /**
     * Constructs a ConsoleOutputPanel with a JTextArea for displaying console output.
     */
    public ConsoleOutputPanel() {
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Gets the JTextArea used for displaying console output.
     *
     * @return The JTextArea.
     */
    public JTextArea getTextArea() {
        return textArea;
    }
}
