package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;

class ConsoleOutputPanel extends JPanel {

    private final JTextArea textArea;

    public ConsoleOutputPanel() {
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}