package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    static List<double[]> dataList = new ArrayList<>();
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ausgabe der gespeicherten Daten
        for (double[] dataRow : dataList) {
            for (double value : dataRow) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
