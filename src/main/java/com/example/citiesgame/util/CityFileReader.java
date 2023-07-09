package com.example.citiesgame.util;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityFileReader {
    public List<String> readCitiesFromFile(String filename) {
        List<String> cities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                cities.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
