package com.example.citiesgame.util;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityFileReader {
    public List<String> readCitiesFromFile(String filename) {
        List<String> cities = new ArrayList<>();
        /** List<String> cities оголошує змінну cities типу List<String>, що означає,
         * що вона буде містити об'єкти типу String.
         * new ArrayList<>(); створює новий об'єкт ArrayList<String> та присвоює його змінній cities. Оператор new створює
         * новий екземпляр класу ArrayList<String>, а <> вказує компілятору використовувати тип String для списку. Таким чином,
         * отримуємо порожній список міст, до якого потім будуть додаватись рядки з файлу.
         */
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line; // Створюється змінна line, яка буде використовуватися для зберігання кожного рядка, прочитаного з файлу.
            while ((line = reader.readLine()) != null) {
                cities.add(line.trim().toLowerCase());
            }
            /** Додаємо кожне місто видаляючи зайві пробіли та приводимо у нижній регістр
             * В циклі while зчитується кожен рядок з файлу за допомогою методу readLine() об'єкта BufferedReader.
             * Цей цикл продовжується до тих пір, поки метод readLine() не поверне значення null, що означає кінець файлу.
             * Таким чином, ці рядки зчитують рядки з текстового файлу, який має бути переданий у параметрі filename,
             * та обробляють їх у циклі.
             */
        } catch (IOException e) {
            e.printStackTrace(); // Ексепшн на випадок помилки зчитування файлу
        }
        return cities;
    }
}

