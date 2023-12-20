package org.example;

import au.com.bytecode.opencsv.CSVParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;

public class resourcesShow {
    public static void main(String[] args) {
        // Pfad zur CSV-Datei
        String csvFilePath = "src/main/resources/diabetes.csv";

        try (FileReader reader = new FileReader(csvFilePath);
             CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader)) {

            // Iteriere über die Zeilen der CSV-Datei
            for (CSVRecord record : csvParser) {
                // Greife auf die Werte in jeder Spalte zu
                String pregnancies = record.get("Pregnancies");
                String glucose = record.get("Glucose");
                String bloodPressure = record.get("BloodPressure");
                String skinThickness = record.get("SkinThickness");
                String insulin = record.get("Insulin");
                String bmi = record.get("BMI");
                String diabetesPedigreeFunction = record.get("DiabetesPedigreeFunction");
                String age = record.get("Age");
                String outcome = record.get("Outcome");

                // Verarbeite die Daten hier (z.B. speichere sie in einer Datenstruktur oder verwende sie für das Training deines Modells)
                System.out.println("Pregnancies: " + pregnancies + ", Glucose: " + glucose + ", Outcome: " + outcome);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
