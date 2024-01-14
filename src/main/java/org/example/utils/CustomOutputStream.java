package org.example.utils;

import javax.swing.*;
import java.io.OutputStream;

/**
 * A custom output stream that redirects data to a JTextArea for display.
 */
public class CustomOutputStream extends OutputStream {

    private final JTextArea textArea;

    /**
     * Constructs a new CustomOutputStream.
     *
     * @param textArea The JTextArea to which the output is redirected.
     */
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    /**
     * Writes an int to the output stream.
     *
     * @param b The int to be written.
     */
    @Override
    public void write(int b) {
        textArea.append(String.valueOf((char) b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
