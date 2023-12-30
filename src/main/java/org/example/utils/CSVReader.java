package org.example.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CSVReader is a utility class for loading data from a CSV file, specifically tailored
 * for the diabetes dataset. It provides methods to read the CSV file, process the data,
 * and convert it into training inputs and outputs.
 */
public class CSVReader {

    /** The list to store the raw data read from the CSV file. */
    private static final List<double[]> dataList = new ArrayList<>();

    /** The 2D array to store the training inputs. */
    public static double[][] trainingInputs;

    /** The 2D array to store the corresponding training outputs. */
    public static double[][] trainingOutputs;

    /**
     * Loads data from a CSV file and processes it.
     *
     * @param filename The name of the CSV file to be loaded.
     */
    public static void loadCSV(String filename) {
        // Path to the CSV file
        String csvFilePath = "src/main/resources/" + filename;

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Skip the header
            String header = reader.readLine();

            // Iterate over the lines of the CSV file
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                // Extract relevant values (adapted to the CSV file structure)
                double pregnancies = Double.parseDouble(values[0]);
                double glucose = Double.parseDouble(values[1]);
                double bloodPressure = Double.parseDouble(values[2]);
                double skinThickness = Double.parseDouble(values[3]);
                double insulin = Double.parseDouble(values[4]);
                double bmi = Double.parseDouble(values[5]);
                double diabetesPedigreeFunction = Double.parseDouble(values[6]);
                double age = Double.parseDouble(values[7]);
                double outcome = Double.parseDouble(values[8]);

                // Add values to an array
                double[] dataRow = {pregnancies, glucose, bloodPressure, skinThickness, insulin, bmi, diabetesPedigreeFunction, age, outcome};
                dataList.add(dataRow);
            }

            // Convert dataList to arrays
            convertDataListToArrays();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts the dataList to trainingInputs and trainingOutputs arrays.
     * Assumes that the outcome is in the last column.
     */
    private static void convertDataListToArrays() {
        int numFeatures = dataList.get(0).length - 1; // Number of features excluding the last column
        trainingInputs = new double[dataList.size()][numFeatures];
        trainingOutputs = new double[dataList.size()][1];

        for (int i = 0; i < dataList.size(); i++) {
            double[] dataRow = dataList.get(i);
            System.arraycopy(dataRow, 0, trainingInputs[i], 0, numFeatures);
            // Assuming the outcome is in the last column
            trainingOutputs[i][0] = dataRow[numFeatures];
        }
    }
}
