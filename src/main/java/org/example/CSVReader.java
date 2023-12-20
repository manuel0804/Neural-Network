package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private static List<double[]> dataList = new ArrayList<>();
    public static double[][] trainingInputs;
    public static double[][] trainingOutputs;
    public static void loadCSV() {
        // Pfad zur CSV-Datei
        String csvFilePath = "src/main/resources/diabetes.csv";

        // Liste zum Speichern der Daten


        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Header überspringen
            String headerLine = reader.readLine();

            // Iteriere über die Zeilen der CSV-Datei
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                // Extrahiere die relevanten Werte (angepasst an deine CSV-Datei)
                double pregnancies = Double.parseDouble(values[0]);
                double glucose = Double.parseDouble(values[1]);
                double bloodPressure = Double.parseDouble(values[2]);
                double skinThickness = Double.parseDouble(values[3]);
                double insulin = Double.parseDouble(values[4]);
                double bmi = Double.parseDouble(values[5]);
                double diabetesPedigreeFunction = Double.parseDouble(values[6]);
                double age = Double.parseDouble(values[7]);
                double outcome = Double.parseDouble(values[8]);

                // Füge die Werte zu einem Array hinzu
                double[] dataRow = {pregnancies, glucose, bloodPressure, skinThickness, insulin, bmi, diabetesPedigreeFunction, age, outcome};
                dataList.add(dataRow);

            }
            convertDataListToArrays(dataList);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ausgabe der gespeicherten Daten
/*        for (double[] dataRow : dataList) {
            for (double value : dataRow) {
                System.out.print(value + " ");
            }
            System.out.println();
        }*/
    }

    private static void convertDataListToArrays(List<double[]> dataList) {
        int numFeatures = dataList.get(0).length - 1; // Number of features excluding the last column
        trainingInputs = new double[dataList.size()][numFeatures];
        trainingOutputs = new double[dataList.size()][1];

        for (int i = 0; i < dataList.size(); i++) {
            double[] dataRow = dataList.get(i);
            for (int j = 0; j < numFeatures; j++) {
                trainingInputs[i][j] = dataRow[j];
            }
            // Assuming the outcome is in the last column
            trainingOutputs[i][0] = dataRow[numFeatures];
        }
    }
}
